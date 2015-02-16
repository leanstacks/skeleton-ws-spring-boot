package com.leanstacks.ws.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * The Role class is an entity model object. A Role describes a privilege level
 * within the application. A Role is used to authorize an Account to access a
 * set of application resources.
 * 
 * @author Matt Warman
 */
@Entity
public class Role {

    @Id
    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String label;

    public Role() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
