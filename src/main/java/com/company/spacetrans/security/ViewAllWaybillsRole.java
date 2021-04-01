package com.company.spacetrans.security;

import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "Can view all waybills", code = ViewAllWaybillsRole.CODE)
public interface ViewAllWaybillsRole {

    String CODE = "view-all-waybills";

}
