package com.example.java.web.heroku.template.daos;

/**
 * We use environment variables, we do not store in DB so this class does not
 * extends from DaoBase.
 *
 * Go to README file to configure environment variables in Tomcat
 *
 * @author Andres Canavesi
 */
public class DaoConfigs {

    /**
     * There is not public instances of this class
     */
    private DaoConfigs() {

    }

    /**
     * Example method. IN Heroku you must run this command:
     *
     * heroku config:set CONFIG_1="your_value"
     *
     * @return
     */
    public static String getConfig1() {
        String value = System.getenv("CONFIG_1");
        if (value == null) {
            value = "default_value";
        }
        return value;

    }

    /**
     *
     * @return example "40.0"
     */
    public static String getApiVersion() {
        //heroku config:set SALESFORCE_API_VERSION="40.0"
        String value = System.getenv("SALESFORCE_API_VERSION");
        if (value == null) {
            return "40.0";
        }
        return value;
    }

    /**
     *
     * @return the Salesforce connected app client id or a default value for
     * development if it is not set
     */
    public static String getSalesforceClientId() {
        String value = System.getenv("SALESFORCE_CLIENT_ID");
        if (value == null) {
            value = "TODO";
        }
        return value;

    }

    /**
     *
     * @return the Salesforce connected app client secret or a default value for
     * development if it is not set
     */
    public static String getSalesforceClientSecret() {
        String value = System.getenv("SALESFORCE_CLIENT_SECRET");
        if (value == null) {
            value = "TODO";
        }
        return value;
    }

    /**
     *
     * @param isSandbox
     * @return the url to request the code to get the access token. The user
     * must open this url in his browser in order to get the code
     */
    public static String getUrlAuthRequestCode(boolean isSandbox) {
        String callbackUrl = DaoConfigs.getSalesforceCallbackUrl();
        String clientId = DaoConfigs.getSalesforceClientId();
        StringBuilder builder = new StringBuilder();
        String subdomain = isSandbox ? "test" : "login";
        builder.append("https://")
                .append(subdomain)
                .append(".salesforce.com/services/oauth2/authorize?response_type=code&client_id=")
                .append(clientId)
                .append("&redirect_uri=")
                .append(callbackUrl);
        return builder.toString();
    }

    /**
     *
     * @return the callback url that Salesforce hits with the code to get the
     * access token later
     */
    public static String getSalesforceCallbackUrl() {
        String value = System.getenv("SALESFORCE_CALLBACK_URL");
        if (value == null) {
            value = "SalesforceOauthServlet";
        }
        return getBaseUrl() + "/" + value;
    }

    /**
     *
     * @return the webapp base url without the "/" at the end. Returns localhost
     * if the config is not set
     */
    public static String getBaseUrl() {
        //heroku config:set BASE_URL="http://localhost:8080/java-web-heroku-template"
        String value = System.getenv("BASE_URL");
        if (value == null) {
            value = "http://localhost:8080/java-web-heroku-template";
        }
        return value;
    }
}
