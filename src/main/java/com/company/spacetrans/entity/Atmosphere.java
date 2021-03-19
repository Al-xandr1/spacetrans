package com.company.spacetrans.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@JmixEntity
@Table(name = "ST_ATMOSPHERE")
@Entity(name = "st_Atmosphere")
public class Atmosphere extends AbstractEntity {

    @NotNull
    @InstanceName
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "PRESSURE")
    private Double pressure;

    @OnDeleteInverse(DeletePolicy.DENY)
    @Composition
    @OneToMany(mappedBy = "atmosphere")
    private List<AtmosphericGas> gases;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public List<AtmosphericGas> getGases() {
        return gases;
    }

    public void setGases(List<AtmosphericGas> gases) {
        this.gases = gases;
    }
}
