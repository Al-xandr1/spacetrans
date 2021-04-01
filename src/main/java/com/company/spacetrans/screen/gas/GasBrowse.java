package com.company.spacetrans.screen.gas;

import com.company.spacetrans.entity.Gas;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("st_Gas.browse")
@UiDescriptor("gas-browse.xml")
@LookupComponent("gasesTable")
public class GasBrowse extends StandardLookup<Gas> {
}