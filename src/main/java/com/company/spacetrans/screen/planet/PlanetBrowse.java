package com.company.spacetrans.screen.planet;

import io.jmix.ui.screen.*;
import com.company.spacetrans.entity.Planet;

@UiController("st_Planet.browse")
@UiDescriptor("planet-browse.xml")
@LookupComponent("planetsTable")
public class PlanetBrowse extends StandardLookup<Planet> {
}