package com.company.spacetrans.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "ST_PLANET")
@Entity(name = "st_Planet")
public class Planet extends AstronomicalBody {
    //todo LOW set validation on numeric fields

    @NotNull
    @Column(name = "SEMI_MAJOR_AXIS", nullable = false)
    private Double semiMajorAxis;

    @NotNull
    @Column(name = "ORBITAL_PERIOD", nullable = false)
    private Double orbitalPeriod;

    @NotNull
    @Column(name = "ROTATION_PERIOD", nullable = false)
    private Double rotationPeriod;

    @OnDeleteInverse(DeletePolicy.DENY)
    @Composition
    @NotNull
    @JoinColumn(name = "ATMOSPHERE_ID", nullable = false)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    private Atmosphere atmosphere;

    @NotNull
    @Column(name = "RINGS", nullable = false)
    private Boolean rings;

    public Boolean getRings() {
        return rings;
    }

    public void setRings(Boolean rings) {
        this.rings = rings;
    }

    public void setRotationPeriod(Double rotationPeriod) {
        this.rotationPeriod = rotationPeriod;
    }

    public Double getRotationPeriod() {
        return rotationPeriod;
    }

    public Double getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public void setOrbitalPeriod(Double orbitalPeriod) {
        this.orbitalPeriod = orbitalPeriod;
    }

    public Double getSemiMajorAxis() {
        return semiMajorAxis;
    }

    public void setSemiMajorAxis(Double semiMajorAxis) {
        this.semiMajorAxis = semiMajorAxis;
    }

    public Atmosphere getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(Atmosphere atmosphere) {
        this.atmosphere = atmosphere;
    }
}
