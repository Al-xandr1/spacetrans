package com.company.spacetrans.security;

import com.company.spacetrans.entity.*;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.SpecificPolicy;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "Operator access role", code = OperatorAccessRole.CODE)
public interface OperatorAccessRole extends SimpleAccessRole {

    String CODE = "operator-access";

    @EntityPolicy(entityClass = Individual.class, actions = EntityPolicyAction.ALL)
    @EntityPolicy(entityClass = Company.class, actions = EntityPolicyAction.ALL)
    @EntityPolicy(entityClass = Discounts.class, actions = EntityPolicyAction.ALL)
    @EntityPolicy(entityClass = Carrier.class, actions = EntityPolicyAction.ALL)
    @EntityPolicy(entityClass = Waybill.class, actions = EntityPolicyAction.ALL)
    @EntityPolicy(entityClass = WaybillItem.class, actions = EntityPolicyAction.ALL)
    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.READ)
    @EntityAttributePolicy(entityName = "*", attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = "st_Individual.browse")
    @ScreenPolicy(screenIds = "st_Individual.edit")
    @ScreenPolicy(screenIds = "st_Company.browse")
    @ScreenPolicy(screenIds = "st_Company.edit")
    @ScreenPolicy(screenIds = "st_Discounts.browse")
    @ScreenPolicy(screenIds = "st_Discounts.edit")
    @ScreenPolicy(screenIds = "st_Carrier.browse")
    @ScreenPolicy(screenIds = "st_Carrier.edit")
    @ScreenPolicy(screenIds = "st_Waybill.browse")
    @ScreenPolicy(screenIds = "st_Waybill.edit")
    @ScreenPolicy(screenIds = "st_WaybillItem.browse")
    @ScreenPolicy(screenIds = "st_WaybillItem.edit")
    @ScreenPolicy(screenIds = "st_User.browse")
    @ScreenPolicy(screenIds = "st_User.edit")
    //@MenuPolicy(menuIds = {"waybill-accounting", "users"}) //todo BUG HIGH this variant does not work
    @MenuPolicy(menuIds = "*")
    @SpecificPolicy(resources = "*")
    void operatorAccess();
}