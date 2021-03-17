package com.company.spacetrans.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import io.jmix.core.metamodel.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "ST_ATMOSPHERIC_GAS")
@Entity(name = "st_AtmosphericGas")
public class AtmosphericGas extends AbstractEntity {

    @NotNull
    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinColumn(name = "GAS_ID", nullable = false)
    @ManyToOne(optional = false)
    private Gas gas;

    //todo [BUG] При проставлении формата в дизайнере он сбрасывается при переключении фокуса с поля Number format. При попытке проставить вручную аннотацию @NumberFormat(pattern = "###.00%") - она пропадает после переключения в дизайнер
    //@NumberFormat(pattern = "###.00%") todo [LOW] либо попробовать через String и AttributeConverter
    @NumberFormat(pattern = "###.00%")
    //todo [BUG] Нельзя проставить число в поле volume - возникает алерт "Must be a double", хотя вводится значение 0.5 или 0,5
    //todo [LOW] @NotNull - вернуть после починки.
    @Column(name = "VOLUME") //, nullable = false)
    private Double volume;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @NotNull
    @JoinColumn(name = "ATMOSPHERE_ID", nullable = false)
    @ManyToOne(optional = false)
    private Atmosphere atmosphere;

    @DependsOnProperties({"gas", "atmosphere"})
    @Transient
    @JmixProperty
    public String getFullName() {
        return String.format("%s in %s",
                gas != null ? gas.getName() : null,
                atmosphere != null ? atmosphere.getDescription() : null);
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
