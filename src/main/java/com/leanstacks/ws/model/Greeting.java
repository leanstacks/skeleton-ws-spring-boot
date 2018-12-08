package com.leanstacks.ws.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Greeting class is an entity model object.
 * 
 * @author Matt Warman
 */
@Entity
public class Greeting extends TransactionalEntity {

    private static final long serialVersionUID = 1L;

    /**
     * The text value.
     */
    @ApiModelProperty(value = "The actual text of the Greeting.",
            required = true,
            position = 100,
            example = "Hello World!")
    @NotNull
    private String text;

    /**
     * Create a new Greeting object.
     */
    public Greeting() {
        super();
    }

    /**
     * Create a new Greeting object with the supplied text value.
     * 
     * @param text A String text value.
     */
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
