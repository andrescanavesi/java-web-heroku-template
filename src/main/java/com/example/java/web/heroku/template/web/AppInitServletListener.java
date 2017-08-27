package com.example.java.web.heroku.template.web;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Web application lifecycle listener.
 *
 * @author Andres Canavesi
 */
@WebListener
public class AppInitServletListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(AppInitServletListener.class.getName());

    /**
     * To manage persistence
     */
    private static EntityManagerFactory emf;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        LOG.log(Level.INFO, "\n***** Initializing {0}", AppInitServletListener.class.getSimpleName());
        //emf = Persistence.createEntityManagerFactory("myPU");
        LOG.info("\n***** App initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        LOG.log(Level.INFO, "\n***** Destroying {0}", AppInitServletListener.class.getSimpleName());
        //emf.close();
        LOG.info("\n***** App destroyed");
    }

    /**
     *
     * @return
     */
    public static EntityManager createEntityManager() {
        if (emf == null) {
            throw new IllegalStateException("Context is not initialized yet.");
        }
        return emf.createEntityManager();
    }
}
