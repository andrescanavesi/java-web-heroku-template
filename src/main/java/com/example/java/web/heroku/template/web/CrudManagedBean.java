package com.example.java.web.heroku.template.web;

import com.example.java.web.heroku.template.daos.DaoUsers;
import com.example.java.web.heroku.template.entities.UserEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 * crud.xhtml controller
 *
 * CRUD stands for Create, Read, Update and Delete
 *
 * @author Andres Canavesi
 */
@Named(value = "crudManagedBean")
@ViewScoped
@ManagedBean
public class CrudManagedBean {

    private static final Logger LOG = Logger.getLogger(CrudManagedBean.class.getName());

    private UserEntity user = new UserEntity();
    private List<UserEntity> users;
    private DaoUsers daoUsers;

    /**
     *
     */
    @PostConstruct
    public void init() {
        daoUsers = new DaoUsers();
        users = daoUsers.findAllUsers(0, 100);
    }

    /**
     *
     */
    public void saveUser() {
        try {
            daoUsers.save(user);
            users = daoUsers.findAllUsers(0, 100);

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "User " + user.getName() + " saved"));
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

}
