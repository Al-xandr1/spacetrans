package com.company.spacetrans.entity;

import io.jmix.core.Messages;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@JmixEntity
@Table(name = "ST_DISCOUNTS")
@Entity(name = "st_Discounts")
public class Discounts extends AbstractEntity {

    @Column(name = "VALUE_", precision = 19, scale = 2, nullable = false)
    private BigDecimal value;

    @Column(name = "GRADE", nullable = false, unique = true)
    private String grade;

    @DependsOnProperties({"grade", "value"})
    @InstanceName
    public String getDisplayName(Messages messages) {
        return messages.formatMessage(getClass(), "Discounts.instanceName", grade, value);
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public CustomerGrade getGrade() {
        return grade == null ? null : CustomerGrade.fromId(grade);
    }

    public void setGrade(CustomerGrade grade) {
        this.grade = grade == null ? null : grade.getId();
    }
}