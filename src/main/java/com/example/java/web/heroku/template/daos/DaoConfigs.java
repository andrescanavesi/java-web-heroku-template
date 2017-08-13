package com.example.java.web.heroku.template.daos;

/**
 * We use environment variables, we do not store in DB so this class does not
 * extends from DaoBase.
 *
 *
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
     *
     * @return the Salesforce connected app client id or a default value for
     * development if it is not set
     */
    public static String getConfig1() {
        String value = System.getenv("CONFIG_1");
        if (value == null) {
            value = "default_value";
        }
        return value;

    }
}
