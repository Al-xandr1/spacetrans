package com.company.spacetrans.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.io.FileDescriptor;
import java.sql.Blob;
import java.util.UUID;

@JmixEntity(name = "st_AstronomicalBody")
@MappedSuperclass
public class AstronomicalBody {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    @InstanceName
    @Column(name = "NAME", unique = true)
    private String name;

    @Column(name = "MASS")
    private Double mass;

    //todo ссылка на файл в FileStorage - what is it? io.jmix.core.FileStorage & https://docs.jmix.io/jmix/0.x/files/fsfilestorage.html
    //todo BLOB. Замапипить через AttributeConverter
//    @Column(name = "PICTURE")
//    private FileDescriptor picture;
    @Lob
    @Column(name = "picture")
    private byte[] picture;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}