package com.example.java.web.heroku.template.web;

import com.example.java.web.heroku.template.daos.DaoUsers;
import com.example.java.web.heroku.template.entities.UserEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Andres Canavesi
 */
@Named(value = "indexManagedBean")
@ViewScoped
@ManagedBean
public class IndexManagedBean {

    private static final Logger LOG = Logger.getLogger(IndexManagedBean.class.getName());

    /**
     *
     */
    @PostConstruct
    public void init() {

    }

    /**
     *
     */
    public void saveUser() {
        try {
            LOG.info("Saving user...");
            UserEntity userEntity = new UserEntity();
            userEntity.setName("test" + Math.random());

            DaoUsers daoUsers = new DaoUsers();
            daoUsers.save(userEntity);

            LOG.info("User saved");

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "User " + userEntity.getName() + " saved"));
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

}
