package com.erp.store.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "unit_conversion_mapping")
public class UnitConversionMapping implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_conversion_id")
    private long id;

    @OneToOne
    @JoinColumn(name = "converted_from", referencedColumnName = "unit_id")
    private Unit convertedFrom;

    @OneToOne
    @JoinColumn(name = "converted_to", referencedColumnName = "unit_id")
    private Unit convertedTo;

    private double value;

    private Date createdOn;

    private Date updatedOn;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Unit getConvertedFrom() {
        return convertedFrom;
    }

    public void setConvertedFrom(Unit convertedFrom) {
        this.convertedFrom = convertedFrom;
    }

    public Unit getConvertedTo() {
        return convertedTo;
    }

    public void setConvertedTo(Unit convertedTo) {
        this.convertedTo = convertedTo;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }
}
