package com.company.spacetrans.entity;

import io.jmix.core.Messages;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@JmixEntity(name = "st_Coordinates")
@Embeddable
public class Coordinates {

    @Column(name = "2LATITUDE", nullable = false)
    @NotNull
    private Double latitude;

    @NotNull
    @Column(name = "LONGITUDE", nullable = false)
    private Double longitude;

    @InstanceName
    @DependsOnProperties({"latitude", "longitude"})
    public String getFullName(Messages messages) {
        return messages.formatMessage(getClass(), "Coordinates.instanceName", latitude, longitude);
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}