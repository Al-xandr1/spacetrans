package com.company.spacetrans.security;

import com.company.spacetrans.entity.Waybill;
import com.company.spacetrans.entity.WaybillItem;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "Can view only owned waybills", code = ViewOwnedWaybills.CODE)
public interface ViewOwnedWaybills {

    String CODE = "view-owned-waybills";

    @JpqlRowLevelPolicy(entityClass = Waybill.class, where = "{E}.creator.username = :current_user_username")
    void waybills();

    @JpqlRowLevelPolicy(entityClass = WaybillItem.class, where = "{E}.waybill.creator.username = :current_user_username")
    void waybillItems();
}
