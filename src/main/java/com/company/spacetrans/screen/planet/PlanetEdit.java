package com.company.spacetrans.screen.planet;

import com.company.spacetrans.entity.Planet;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("st_Planet.edit")
@UiDescriptor("planet-edit.xml")
@EditedEntityContainer("planetDc")
public class PlanetEdit extends StandardEditor<Planet> {
}