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

@ResourceRole(name = "Manager access role", code = ManagerAccessRole.CODE)
public interface ManagerAccessRole extends OperatorAccessRole {

    String CODE = "manager-access";

    @EntityPolicy(entityClass = Atmosphere.class, actions = EntityPolicyAction.ALL)
    @EntityPolicy(entityClass = AtmosphericGas.class, actions = EntityPolicyAction.ALL)
    @EntityPolicy(entityClass = Gas.class, actions = EntityPolicyAction.ALL)
    @EntityPolicy(entityClass = Moon.class, actions = EntityPolicyAction.ALL)
    @EntityPolicy(entityClass = Planet.class, actions = EntityPolicyAction.ALL)
    @EntityPolicy(entityClass = Spaceport.class, actions = EntityPolicyAction.ALL)
    @EntityAttributePolicy(entityName = "*", attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @ScreenPolicy(screenIds = "st_Atmosphere.browse")
    @ScreenPolicy(screenIds = "st_Atmosphere.edit")
    @ScreenPolicy(screenIds = "st_AtmosphericGas.browse")
    @ScreenPolicy(screenIds = "st_AtmosphericGas.edit")
    @ScreenPolicy(screenIds = "st_Gas.browse")
    @ScreenPolicy(screenIds = "st_Gas.edit")
    @ScreenPolicy(screenIds = "st_Moon.browse")
    @ScreenPolicy(screenIds = "st_Moon.edit")
    @ScreenPolicy(screenIds = "st_Planet.browse")
    @ScreenPolicy(screenIds = "st_Planet.edit")
    @ScreenPolicy(screenIds = "st_Spaceport.browse")
    @ScreenPolicy(screenIds = "st_Spaceport.edit")
    @MenuPolicy(menuIds = "*")
    @SpecificPolicy(resources = "*")
    void managerAccess();
}