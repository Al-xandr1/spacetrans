package com.company.spacetrans.entity;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.util.List;

@JmixEntity
@Table(name = "ST_CARRIER")
@Entity(name = "st_Carrier")
public class Carrier extends AbstractEntity {

    @JoinTable(name = "ST_CARRIER_SPACEPORT_LINK",
            joinColumns = @JoinColumn(name = "CARRIER_ID"),
            inverseJoinColumns = @JoinColumn(name = "SPACEPORT_ID"))
    @ManyToMany
    private List<Spaceport> ports;

    @InstanceName
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    public List<Spaceport> getPorts() {
        return ports;
    }

    public void setPorts(List<Spaceport> ports) {
        this.ports = ports;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}