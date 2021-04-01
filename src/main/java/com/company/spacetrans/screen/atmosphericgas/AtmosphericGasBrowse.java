package com.company.spacetrans.screen.atmosphericgas;

import com.company.spacetrans.entity.AtmosphericGas;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.MasterDetailScreen;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("st_AtmosphericGas.browse")
@UiDescriptor("atmospheric-gas-browse.xml")
@LookupComponent("table")
public class AtmosphericGasBrowse extends MasterDetailScreen<AtmosphericGas> {
}