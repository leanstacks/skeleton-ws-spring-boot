package com.leanstacks.ws.model;

import javax.persistence.Entity;

/**
 * The Role class is an entity model object. A Role describes a privilege level within the application. A Role is used
 * to authorize an Account to access a set of application resources.
 * 
 * @author Matt Warman
 */
@Entity
public class Role extends ReferenceEntity {

    private static final long serialVersionUID = 1L;

}
