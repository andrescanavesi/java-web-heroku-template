/**
 * CODE_REVIEW_READY
 */
package com.example.java.web.heroku.template.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Represents this response: (DUMMY DATA)
 *
 * {
 * "id":"https://login.salesforce.com/id/00D50000000IZ3ZEAW/00550000001fg5OAAQ",
 *
 * "issued_at":"1296458209517",
 *
 * "scope": "id full api openid refresh_token chatter_api",
 *
 * "instance_url":"https://na1.salesforce.com",
 *
 * "token_type": "Bearer",
 *
 * "refresh_token":"5Aep862eWO5D.7wJBuW5aaARbbxQ8hssCnY1dw3qi59o1du7ob.lp23ba_3jMRnbFNT5R8X2GUKNA==",
 *
 * "id_token": "eyJhb...h97hc",
 *
 * "signature":"0/1Ldval/TIPf2tTgTKUAxRy44VwEJ7ffsFLMWFcNoA=",
 *
 * "access_token":"00D50000000IZ3Z!AQ0AQDpEDKYsn7ioKug2aSmgCjgrPjG9eRLza8jXWoW7uA90V39rvQaIy1FGxjFHN1ZtusBGljncdEi8eRiuit1QdQ1Z2KSV"
 *
 * }
 *
 * @author acanavesi
 *
 */
public class SalesforceAccessTokenResponse {

    private String id;
    @SerializedName("issued_at")
    private String issuedAt;
    private String scope;
    @SerializedName("instance_url")
    private String instanceUrl;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("refresh_token")
    private String refreshToken;
    @SerializedName("id_token")
    private String idToken;
    private String signature;
    @SerializedName("access_token")
    private String accessToken;

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getIssuedAt() {
        return issuedAt;
    }

    /**
     *
     * @param issuedAt
     */
    public void setIssuedAt(String issuedAt) {
        this.issuedAt = issuedAt;
    }

    /**
     *
     * @return
     */
    public String getScope() {
        return scope;
    }

    /**
     *
     * @param scope
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     *
     * @return
     */
    public String getInstanceUrl() {
        return instanceUrl;
    }

    /**
     *
     * @param instanceUrl
     */
    public void setInstanceUrl(String instanceUrl) {
        this.instanceUrl = instanceUrl;
    }

    /**
     *
     * @return
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     *
     * @param tokenType
     */
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    /**
     *
     * @return
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     *
     * @param refreshToken
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     *
     * @return
     */
    public String getIdToken() {
        return idToken;
    }

    /**
     *
     * @param idToken
     */
    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    /**
     *
     * @return
     */
    public String getSignature() {
        return signature;
    }

    /**
     *
     * @param signature
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     *
     * @return
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     *
     * @param accessToken
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
