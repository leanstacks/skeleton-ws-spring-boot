package com.leanstacks.ws.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * The Greeting class is an entity model object.
 * 
 * @author Matt Warman
 */
@Entity
public class Greeting {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String text;

    public Greeting() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
