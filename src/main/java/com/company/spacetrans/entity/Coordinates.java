package com.company.spacetrans.entity;

import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.NumberFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@JmixEntity(name = "st_Coordinates")
@Embeddable
public class Coordinates {

    @NotNull
    //@NumberFormat(pattern = "###.######", decimalSeparator = ".", groupingSeparator = ",") todo LOW fix
    @NumberFormat(pattern = "###.######", decimalSeparator = ".", groupingSeparator = ",")
    @Column(name = "LATITUDE", nullable = false)
    private Double latitude;

    @NotNull
    //@NumberFormat(pattern = "###.######", decimalSeparator = ".", groupingSeparator = ",") todo LOW fix
    @NumberFormat(pattern = "###.######", decimalSeparator = ".", groupingSeparator = ",")
    @Column(name = "LONGITUDE", nullable = false)
    private Double longitude;

    @InstanceName
    @DependsOnProperties({"latitude", "longitude"})
    public String getDisplayName() {
        return String.format("(lat=%s, long=%s)", latitude, longitude);
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