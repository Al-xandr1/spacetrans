package com.company.spacetrans.screen.spaceport;

import com.company.spacetrans.entity.Moon;
import com.company.spacetrans.entity.Planet;
import com.company.spacetrans.entity.Spaceport;
import io.jmix.ui.action.entitypicker.EntityClearAction;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;

import javax.inject.Named;
import java.util.Set;

@UiController("st_Spaceport.edit")
@UiDescriptor("spaceport-edit.xml")
@EditedEntityContainer("spaceportDc")
public class SpaceportEdit extends StandardEditor<Spaceport> {

    @Named("moonField.clear")
    private EntityClearAction moonFieldClear;

    @Named("planetField.clear")
    private EntityClearAction planetFieldClear;


    @Subscribe("planetField")
    public void onPlanetFieldValueChange(HasValue.ValueChangeEvent<Planet> event) {
        if (event.getPrevValue() == null && event.getValue() != null) {
            moonFieldClear.execute();
        }
    }

    @Subscribe("moonField")
    public void onMoonFieldValueChange(HasValue.ValueChangeEvent<Moon> event) {
        if (event.getPrevValue() == null && event.getValue() != null) {
            planetFieldClear.execute();
        }
    }

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        DataContext dataContext = event.getDataContext();
        Set<Object> modified = dataContext.getModified();
        for (Object o : modified) {
            if (o instanceof Spaceport) {
                Spaceport spaceport = (Spaceport) o;
                Moon moon = spaceport.getMoon();
                Planet planet = spaceport.getPlanet();
                if (moon != null && planet != null) {
                    throw new IllegalArgumentException("There must be specified one of the properties: moon or planet");
                }
            }
        }
    }
}