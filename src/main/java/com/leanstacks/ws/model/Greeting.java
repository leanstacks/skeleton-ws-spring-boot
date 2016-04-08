package com.leanstacks.ws.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * The Greeting class is an entity model object.
 * 
 * @author Matt Warman
 */
@Entity
public class Greeting extends TransactionalEntity {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String text;

    public Greeting() {
        super();
    }

    public Greeting(final String text) {
        super();
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

}
