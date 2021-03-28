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

@ResourceRole(name = "Simple access role", code = SimpleAccessRole.CODE)
public interface SimpleAccessRole {

    String CODE = "simple-access";

    @EntityPolicy(entityClass = Atmosphere.class, actions = EntityPolicyAction.READ)
    @EntityPolicy(entityClass = AtmosphericGas.class, actions = EntityPolicyAction.READ)
    @EntityPolicy(entityClass = Gas.class, actions = EntityPolicyAction.READ)
    @EntityPolicy(entityClass = Moon.class, actions = EntityPolicyAction.READ)
    @EntityPolicy(entityClass = Planet.class, actions = EntityPolicyAction.READ)
    @EntityPolicy(entityClass = Spaceport.class, actions = EntityPolicyAction.READ)
    @EntityAttributePolicy(entityName = "*", attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @ScreenPolicy(screenIds = "st_Atmosphere.browse")
    @ScreenPolicy(screenIds = "st_AtmosphericGas.browse")
    @ScreenPolicy(screenIds = "st_Gas.browse")
    @ScreenPolicy(screenIds = "st_Moon.browse")
    @ScreenPolicy(screenIds = "st_Planet.browse")
    @ScreenPolicy(screenIds = "st_Spaceport.browse")
    //@MenuPolicy(menuIds = "planetary-system") //todo BUG HIGH this variant does not work
    @MenuPolicy(menuIds = "*")
    @SpecificPolicy(resources = "*")
    void baseAccess();
}
