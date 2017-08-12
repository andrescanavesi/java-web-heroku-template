package com.example.java.web.heroku.template.daos;

import com.example.java.web.heroku.template.entities.UserEntity;

/**
 *
 * @author Andres Canavesi
 */
public class DaoUsers extends DaoBase<UserEntity> {

    /**
     *
     */
    public DaoUsers() {
        super(UserEntity.class);
    }

}
