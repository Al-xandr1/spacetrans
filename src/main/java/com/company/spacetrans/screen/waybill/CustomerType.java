package com.company.spacetrans.screen.waybill;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

public enum CustomerType implements EnumClass<String> {
    INDIVIDUAL, COMPANY;

    @Override
    public String getId() {
        return name();
    }
}
