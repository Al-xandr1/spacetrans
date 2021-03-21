package com.company.spacetrans.screen.atmosphere;

import com.company.spacetrans.entity.Atmosphere;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("st_Atmosphere.edit")
@UiDescriptor("atmosphere-edit.xml")
@EditedEntityContainer("atmosphereDc")
public class AtmosphereEdit extends StandardEditor<Atmosphere> {
}