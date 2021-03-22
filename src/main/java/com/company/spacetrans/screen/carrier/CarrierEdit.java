package com.company.spacetrans.screen.carrier;

import com.company.spacetrans.entity.Carrier;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("st_Carrier.edit")
@UiDescriptor("carrier-edit.xml")
@EditedEntityContainer("carrierDc")
public class CarrierEdit extends StandardEditor<Carrier> {

}