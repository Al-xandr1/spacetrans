package com.company.spacetrans.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@JmixEntity
@Table(name = "ST_ATMOSPHERIC_GAS")
@Entity(name = "st_AtmosphericGas")
public class AtmosphericGas {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinColumn(name = "GAS_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    private Gas gas;

    //todo пропадает после переключения на UI @NumberFormat(pattern = "%,.2f")
    //todo либо попробовать через String и AttributeConverter
    //@NumberFormat(pattern = "%,.2f")
    @NumberFormat(pattern = "%,.2f")
    @Column(name = "VOLUME", nullable = false)
    @NotNull
    private Double volume;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "ATMOSPHERE_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Atmosphere atmosphere;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Gas getGas() {
        return gas;
    }

    public void setGas(Gas gas) {
        this.gas = gas;
    }

    public Atmosphere getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(Atmosphere atmosphere) {
        this.atmosphere = atmosphere;
    }
}
