package com.leanstacks.ws.model;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

/**
 * The parent class for all reference entities (i.e. reference data as opposed
 * to transactional data).
 * 
 * @see com.leanstacks.ws.model.TransactionalEntity
 * 
 * @author Matt Warman
 */
@MappedSuperclass
public class ReferenceEntity implements Serializable {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The primary key identifier.
     */
    @Id
    private Long id;

    /**
     * The unique code value, sometimes used for external reference.
     */
    @NotNull
    private String code;

    /**
     * A brief description of the entity.
     */
    @NotNull
    private String label;

    /**
     * The ordinal value facilitates sorting the entities.
     */
    @NotNull
    private Integer ordinal;

    /**
     * The timestamp at which the entity's values may be applied or used by the
     * system.
     */
    @NotNull
    private DateTime effectiveAt;

    /**
     * The timestamp at which the entity's values cease to be used by the
     * system. If <code>null</code> the entity is not expired.
     */
    private DateTime expiresAt;

    /**
     * The timestamp when this entity instance was created.
     */
    @NotNull
    private DateTime createdAt;

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

    public Integer getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    public DateTime getEffectiveAt() {
        return effectiveAt;
    }

    public void setEffectiveAt(DateTime effectiveAt) {
        this.effectiveAt = effectiveAt;
    }

    public DateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(DateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

}
