package com.example.java.web.heroku.template.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to process heavy work in background without using web dynos.
 *
 * It contains a main method that is called from Procfile
 *
 * @author Andres Canavesi
 */
public class MyWorker {

    private static final Logger LOG = Logger.getLogger(MyWorker.class.getName());

    /**
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        while (true) {
            try {
                /**
                 * Do your heavy work here
                 */
                LOG.info("I'm a worker");
                Thread.sleep(1000 * 2);
            } catch (InterruptedException ex) {
                LOG.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }

}
