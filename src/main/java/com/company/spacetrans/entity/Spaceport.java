package com.company.spacetrans.entity;

import io.jmix.core.entity.annotation.EmbeddedParameters;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@JmixEntity(name = "st_Spaceport")
@Table(name = "ST_SPACEPORT")
@Entity(name = "st_Spaceport")
public class Spaceport extends AbstractEntity {

    @InstanceName
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Embedded
    @EmbeddedParameters(nullAllowed = false)
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "COORDINATES_LATITUDE")),
            @AttributeOverride(name = "longitude", column = @Column(name = "COORDINATES_LONGITUDE"))
    })
    private Coordinates coordinates;

    @NotNull
    @Column(name = "IS_DEFAULT", nullable = false)
    private Boolean isDefault = false;

    @JoinColumn(name = "MOON_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Moon moon;

    @JoinColumn(name = "PLANET_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Planet planet;

    @JoinTable(name = "ST_CARRIER_SPACEPORT_LINK",
            joinColumns = @JoinColumn(name = "SPACEPORT_ID"),
            inverseJoinColumns = @JoinColumn(name = "CARRIER_ID"))
    @ManyToMany
    private List<Carrier> carriers;

    public List<Carrier> getCarriers() {
        return carriers;
    }

    public void setCarriers(List<Carrier> carriers) {
        this.carriers = carriers;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Moon getMoon() {
        return moon;
    }

    public void setMoon(Moon moon) {
        this.moon = moon;
    }

    public Planet getPlanet() {
        return planet;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}