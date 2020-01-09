package com.qiuxs.base.entity;

import java.io.Serializable;

public interface Entity<ID extends Serializable> extends Serializable {

    /**
     * Return Identifier
     */
    ID getId();

    /**
     * Set new id
     */
    void setId(ID id);

    /**
     * Return true if persisted
     */
    boolean isPersisted();

    /**
     * Return false if persisted
     */
    boolean isTransient();

    void validate();

}
