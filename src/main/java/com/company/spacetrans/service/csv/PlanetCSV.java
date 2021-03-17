package com.company.spacetrans.service.csv;

import com.opencsv.bean.CsvBindByPosition;

public class PlanetCSV {

    @CsvBindByPosition(position = 0)
    private String name;

    @CsvBindByPosition(position = 1)
    private String equatorialDiameter;

    @CsvBindByPosition(position = 2)
    private String mass;

    @CsvBindByPosition(position = 3)
    private String semiMajorAxis;

    @CsvBindByPosition(position = 4)
    private String orbitalPeriod;

    @CsvBindByPosition(position = 5)
    private String inclinationToSunsEquator;

    @CsvBindByPosition(position = 6)
    private String orbitalEccentricity;

    @CsvBindByPosition(position = 7)
    private String rotationPeriod;

    @CsvBindByPosition(position = 8)
    private String confirmedMoons;

    @CsvBindByPosition(position = 9)
    private String axialTilt;

    @CsvBindByPosition(position = 10)
    private String rings;

    @CsvBindByPosition(position = 11)
    private String atmosphere;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEquatorialDiameter() {
        return equatorialDiameter;
    }

    public void setEquatorialDiameter(String equatorialDiameter) {
        this.equatorialDiameter = equatorialDiameter;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getSemiMajorAxis() {
        return semiMajorAxis;
    }

    public void setSemiMajorAxis(String semiMajorAxis) {
        this.semiMajorAxis = semiMajorAxis;
    }

    public String getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public void setOrbitalPeriod(String orbitalPeriod) {
        this.orbitalPeriod = orbitalPeriod;
    }

    public String getInclinationToSunsEquator() {
        return inclinationToSunsEquator;
    }

    public void setInclinationToSunsEquator(String inclinationToSunsEquator) {
        this.inclinationToSunsEquator = inclinationToSunsEquator;
    }

    public String getOrbitalEccentricity() {
        return orbitalEccentricity;
    }

    public void setOrbitalEccentricity(String orbitalEccentricity) {
        this.orbitalEccentricity = orbitalEccentricity;
    }

    public String getRotationPeriod() {
        return rotationPeriod;
    }

    public void setRotationPeriod(String rotationPeriod) {
        this.rotationPeriod = rotationPeriod;
    }

    public String getConfirmedMoons() {
        return confirmedMoons;
    }

    public void setConfirmedMoons(String confirmedMoons) {
        this.confirmedMoons = confirmedMoons;
    }

    public String getAxialTilt() {
        return axialTilt;
    }

    public void setAxialTilt(String axialTilt) {
        this.axialTilt = axialTilt;
    }

    public String getRings() {
        return rings;
    }

    public void setRings(String rings) {
        this.rings = rings;
    }

    public String getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(String atmosphere) {
        this.atmosphere = atmosphere;
    }
}
