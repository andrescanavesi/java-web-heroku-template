package com.example.java.web.heroku.template.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Andres Canavesi
 */
@Entity
@Table(name = UserEntity.TABLE_NAME)
public class UserEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String TABLE_NAME = "users";

    private String name;

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

}
