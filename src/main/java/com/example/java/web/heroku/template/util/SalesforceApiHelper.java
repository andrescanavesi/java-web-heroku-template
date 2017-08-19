package com.example.java.web.heroku.template.util;

import com.example.java.web.heroku.template.daos.DaoConfigs;
import com.example.java.web.heroku.template.domain.SalesforceAccessTokenResponse;
import com.example.java.web.heroku.template.domain.SalesforceErrorResponse;
import com.example.java.web.heroku.template.exceptions.SaleforceApiException;
import com.example.java.web.heroku.template.exceptions.SalesforceIpRestrictedException;
import com.example.java.web.heroku.template.exceptions.SalesforceResponseException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.GetUserInfoResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Entry point of Salesforce API. Uses SOAP and REST APIs
 *
 * @author Andres Canavesi
 */
public class SalesforceApiHelper {

    private static final Logger LOG = Logger.getLogger(SalesforceApiHelper.class.getName());

    private final String apiVersion;
    private final String vApiVersion;
    private final Gson gson;
    private PartnerConnection partnerConnection;
    private SalesforceAccessTokenResponse salesforceAccessTokenResponse;

    public SalesforceApiHelper() {
        apiVersion = DaoConfigs.getApiVersion();
        vApiVersion = "v" + apiVersion;
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     *
     * @param refreshToken
     * @param code
     * @param isSandbox
     * @throws IOException
     * @throws SalesforceIpRestrictedException if the org do not accept our IP
     * to stablish a connection
     * @throws SalesforceResponseException the request was done and response was
     * different than 200
     * @throws com.sforce.ws.ConnectionException for SOAP API configuration
     * errors
     * @throws IllegalArgumentException if refreshToken and code parameter are
     * null
     */
    public void requestAccessToken(String refreshToken, String code, boolean isSandbox) throws IOException, SalesforceIpRestrictedException, SalesforceResponseException, ConnectionException {
        LOG.info("Requesting access token by refresh token...");
        String subDomain;
        if (isSandbox) {
            subDomain = "test";
        } else {
            subDomain = "login";
        }
        String urlString = "https://" + subDomain + ".salesforce.com/services/oauth2/token";
        LOG.log(Level.INFO, "Requesting access token by refresh token. Url: {0}", urlString);

        URL url = new URL(urlString);
        URLConnection con = url.openConnection();
        HttpURLConnection http = (HttpURLConnection) con;
        http.setRequestMethod("POST");
        http.setDoOutput(true);

        Map<String, String> arguments = new HashMap<>();

        arguments.put("client_id", DaoConfigs.getSalesforceClientId());
        arguments.put("client_secret", DaoConfigs.getSalesforceClientSecret());
        if (refreshToken != null) {
            arguments.put("grant_type", "refresh_token");
            arguments.put("refresh_token", refreshToken);
        } else if (code != null) {
            arguments.put("grant_type", "authorization_code");
            arguments.put("code", code);

        } else {
            throw new IllegalArgumentException("One 'refreshToken' or 'code' parameters must be not null");
        }

        StringJoiner sj = new StringJoiner("&");
        for (Map.Entry<String, String> entry : arguments.entrySet()) {
            sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                    + URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
        int length = out.length;

        http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        http.connect();
        try (OutputStream os = http.getOutputStream()) {
            os.write(out);
        }
        if (http.getResponseCode() == 200) {
            //everything was ok
            try (InputStream is = http.getInputStream();) {
                java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
                String responseString = s.hasNext() ? s.next() : "";
                salesforceAccessTokenResponse = gson.fromJson(responseString, SalesforceAccessTokenResponse.class);

                //SOAP api configuration
                ConnectorConfig enterpriseConfig = new ConnectorConfig();
                enterpriseConfig.setSessionId(salesforceAccessTokenResponse.getAccessToken());
                enterpriseConfig.setServiceEndpoint(salesforceAccessTokenResponse.getInstanceUrl() + "/services/Soap/u/" + apiVersion);
                enterpriseConfig.setAuthEndpoint(salesforceAccessTokenResponse.getInstanceUrl() + "/services/Soap/u/" + apiVersion);

                partnerConnection = Connector.newConnection(enterpriseConfig);

                /**
                 * If you need to get refresh token from Salesforce you must
                 * enable 'Perform requests on your behalf at any time
                 * (refresh_token, offline_access)' in your connected app. This
                 * refresh token will allow you get a new session id (access
                 * token) to perform request after the current session id
                 * expires
                 */
            }
        } else {
            //error management
            try (InputStream is = http.getErrorStream();) {
                java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
                String jsonError = s.hasNext() ? s.next() : "";
                String responseError = String.valueOf(http.getResponseMessage());
                SalesforceErrorResponse error = gson.fromJson(jsonError, SalesforceErrorResponse.class);
                //IMPORTANT: We can manage manage more errors here, for now we only manage IP restrictions
                if (error.getErrorDescription() != null && error.getErrorDescription().equalsIgnoreCase("ip restricted")) {
                    throw new SalesforceIpRestrictedException();
                } else {
                    throw new SalesforceResponseException("Error doing the request. " + responseError + " " + error.getError() + " " + error.getErrorDescription());
                }
            }
        }
    }

    /**
     *
     * @return
     *
     * @throws
     * com.example.java.web.heroku.template.exceptions.SaleforceApiException
     */
    public GetUserInfoResult requestUserInfo() throws SaleforceApiException {
        LOG.info("Requesting user info...");
        try {
            return partnerConnection.getUserInfo();
        } catch (ConnectionException e) {
            throw new SaleforceApiException("Error getting user info", e);
        }

    }
}
