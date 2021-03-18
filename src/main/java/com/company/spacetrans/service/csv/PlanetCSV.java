package com.company.spacetrans.service.csv;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class PlanetCSV {

    @CsvBindByPosition(position = 0)
    private String name;

    @CsvBindByPosition(position = 1)
    private Double equatorialDiameter;

    @CsvBindByPosition(position = 2)
    private Double mass;

    @CsvBindByPosition(position = 3)
    private Double semiMajorAxis;

    @CsvBindByPosition(position = 4)
    private Double orbitalPeriod;

    @CsvBindByPosition(position = 5)
    private Double inclinationToSunsEquator;

    @CsvBindByPosition(position = 6)
    private Double orbitalEccentricity;

    @CsvBindByPosition(position = 7)
    private Double rotationPeriod;

    @CsvBindByPosition(position = 8)
    private Integer confirmedMoons;

    @CsvBindByPosition(position = 9)
    private Double axialTilt;

    @CsvCustomBindByPosition(position = 10, converter =BooleanConverter.class)
    private Boolean rings;

    @CsvBindByPosition(position = 11)
    private String atmosphere;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getEquatorialDiameter() {
        return equatorialDiameter;
    }

    public void setEquatorialDiameter(Double equatorialDiameter) {
        this.equatorialDiameter = equatorialDiameter;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public Double getSemiMajorAxis() {
        return semiMajorAxis;
    }

    public void setSemiMajorAxis(Double semiMajorAxis) {
        this.semiMajorAxis = semiMajorAxis;
    }

    public Double getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public void setOrbitalPeriod(Double orbitalPeriod) {
        this.orbitalPeriod = orbitalPeriod;
    }

    public Double getInclinationToSunsEquator() {
        return inclinationToSunsEquator;
    }

    public void setInclinationToSunsEquator(Double inclinationToSunsEquator) {
        this.inclinationToSunsEquator = inclinationToSunsEquator;
    }

    public Double getOrbitalEccentricity() {
        return orbitalEccentricity;
    }

    public void setOrbitalEccentricity(Double orbitalEccentricity) {
        this.orbitalEccentricity = orbitalEccentricity;
    }

    public Double getRotationPeriod() {
        return rotationPeriod;
    }

    public void setRotationPeriod(Double rotationPeriod) {
        this.rotationPeriod = rotationPeriod;
    }

    public Integer getConfirmedMoons() {
        return confirmedMoons;
    }

    public void setConfirmedMoons(Integer confirmedMoons) {
        this.confirmedMoons = confirmedMoons;
    }

    public Double getAxialTilt() {
        return axialTilt;
    }

    public void setAxialTilt(Double axialTilt) {
        this.axialTilt = axialTilt;
    }

    public Boolean getRings() {
        return rings;
    }

    public void setRings(Boolean rings) {
        this.rings = rings;
    }

    public String getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(String atmosphere) {
        this.atmosphere = atmosphere;
    }

    public static class BooleanConverter extends AbstractBeanField<Boolean> {

        @Override
        protected Boolean convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
            switch (value) {
                case "yes": {
                    return true;
                }
                case "no": {
                    return false;
                }
            }
            return false;
        }
    }
}
