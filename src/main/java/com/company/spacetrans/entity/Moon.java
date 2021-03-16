package com.company.spacetrans.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "ST_MOON")
@Entity(name = "st_Moon")
public class Moon extends AstronomicalBody {

    @OnDeleteInverse(DeletePolicy.DENY)
    //todo [HIGH] @OnDelete(DeletePolicy.CASCADE) это нужно? удаляется после переключения в дизайнер
    @Composition
    @NotNull
    @JoinColumn(name = "ATMOSPHERE_ID", nullable = false)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Atmosphere atmosphere;

    @JoinColumn(name = "PLANET_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Planet planet;

    public Planet getPlanet() {
        return planet;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }

    public Atmosphere getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(Atmosphere atmosphere) {
        this.atmosphere = atmosphere;
    }
}