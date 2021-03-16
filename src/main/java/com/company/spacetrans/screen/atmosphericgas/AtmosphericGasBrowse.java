package com.company.spacetrans.screen.atmosphericgas;

import io.jmix.ui.screen.*;
import com.company.spacetrans.entity.AtmosphericGas;

@UiController("st_AtmosphericGas.browse")
@UiDescriptor("atmospheric-gas-browse.xml")
@LookupComponent("table")
public class AtmosphericGasBrowse extends MasterDetailScreen<AtmosphericGas> {
}