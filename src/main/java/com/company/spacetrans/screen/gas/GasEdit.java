package com.company.spacetrans.screen.gas;

import com.company.spacetrans.entity.Gas;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("st_Gas.edit")
@UiDescriptor("gas-edit.xml")
@EditedEntityContainer("gasDc")
public class GasEdit extends StandardEditor<Gas> {
}