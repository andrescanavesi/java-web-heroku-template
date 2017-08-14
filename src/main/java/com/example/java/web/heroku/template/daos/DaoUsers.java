package com.example.java.web.heroku.template.daos;

import com.example.java.web.heroku.template.entities.UserEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<UserEntity> findAllUsers(int start, int end) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT u FROM ").append(type.getSimpleName()).append(" u");
        Map parameters = new HashMap(); //no parameters
        return findWithQueryString(query.toString(), parameters, start, end);
    }

}
