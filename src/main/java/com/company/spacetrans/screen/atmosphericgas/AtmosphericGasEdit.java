package com.company.spacetrans.screen.atmosphericgas;

import com.company.spacetrans.entity.AtmosphericGas;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("st_AtmosphericGas.edit")
@UiDescriptor("atmospheric-gas-edit.xml")
@EditedEntityContainer("atmosphericGasDc")
public class AtmosphericGasEdit extends StandardEditor<AtmosphericGas> {
}