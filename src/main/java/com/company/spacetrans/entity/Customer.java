package com.company.spacetrans.entity;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.Email;

@JmixEntity
@Table(name = "ST_CUSTOMER")
@Entity(name = "st_Customer")
public class Customer extends AbstractEntity {

    @InstanceName
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Email
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "GRADE")
    private String grade;

    public CustomerGrade getGrade() {
        return grade == null ? null : CustomerGrade.fromId(grade);
    }

    public void setGrade(CustomerGrade grade) {
        this.grade = grade == null ? null : grade.getId();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}