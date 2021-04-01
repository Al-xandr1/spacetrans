package com.company.spacetrans.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@JmixEntity
@Table(name = "ST_COMPANY")
@Entity(name = "st_Company")
public class Company extends Customer {

    @Column(name = "COMPANY_TYPE")
    private String companyType;

    @Column(name = "REGISTRATION_ID", nullable = false, unique = true)
    private String registrationId;

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }
}