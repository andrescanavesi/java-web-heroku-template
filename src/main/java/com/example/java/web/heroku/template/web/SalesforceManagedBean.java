package com.example.java.web.heroku.template.web;

import com.example.java.web.heroku.template.daos.DaoConfigs;
import com.example.java.web.heroku.template.exceptions.SaleforceApiException;
import com.example.java.web.heroku.template.util.SalesforceApiHelper;
import com.sforce.soap.partner.GetUserInfoResult;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;

/**
 * salesforce.xhtml controller
 *
 * @author Andres Canavesi
 */
@Named(value = "salesforceManagedBean")
@ViewScoped
@ManagedBean
public class SalesforceManagedBean {

    private static final Logger LOG = Logger.getLogger(SalesforceManagedBean.class.getName());
    private boolean salesforceAuthenticated = false;
    /**
     * This the URL that the user has to open in the browser to request a code.
     * Then we are able to request the access token with that code (this
     * operation occurs at SalesforceOauthServlet)
     */
    private String urlAuthRequestCode = "#";
    private SalesforceApiHelper salesforceApiHelper;
    private GetUserInfoResult userInfo;

    /**
     *
     */
    @PostConstruct
    public void init() {
        try {
            //First we need to see if we are already authenticated with Salesforce
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            if (session != null) {
                LOG.info("Salesforce authenticated");
                //this attribute is put at SalesforceOauthServlet
                salesforceApiHelper = (SalesforceApiHelper) session.getAttribute("salesforceApiHelper");
                if (salesforceApiHelper != null) {
                    salesforceAuthenticated = true;
                    userInfo = salesforceApiHelper.requestUserInfo();
                }

            } else {
                salesforceAuthenticated = false;

            }
            if (!salesforceAuthenticated) {
                //TODO for now we do not support sandbox orgs
                boolean isSandbox = false;
                urlAuthRequestCode = DaoConfigs.getSalesforceUrlAuthRequestCode(isSandbox);
            }
        } catch (SaleforceApiException | IllegalStateException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean isSalesforceAuthenticated() {
        return salesforceAuthenticated;
    }

    public String getUrlAuthRequestCode() {
        return urlAuthRequestCode;
    }

    public GetUserInfoResult getUserInfo() {
        return userInfo;
    }

}
