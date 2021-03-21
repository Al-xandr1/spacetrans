package com.company.spacetrans.screen.atmosphere;

import com.company.spacetrans.entity.Atmosphere;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("st_Atmosphere.browse")
@UiDescriptor("atmosphere-browse.xml")
@LookupComponent("atmospheresTable")
public class AtmosphereBrowse extends StandardLookup<Atmosphere> {
}