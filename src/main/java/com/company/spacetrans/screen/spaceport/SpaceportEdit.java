package com.company.spacetrans.screen.spaceport;

import com.company.spacetrans.entity.Moon;
import com.company.spacetrans.entity.Planet;
import com.company.spacetrans.entity.Spaceport;
import com.company.spacetrans.service.SpaceportRepository;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.event.AttributeChanges;
import io.jmix.core.event.EntityChangedEvent;
import io.jmix.ui.action.entitypicker.EntityClearAction;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import java.util.Set;

@UiController("st_Spaceport.edit")
@UiDescriptor("spaceport-edit.xml")
@EditedEntityContainer("spaceportDc")
public class SpaceportEdit extends StandardEditor<Spaceport> {

    private static final Logger LOG = LoggerFactory.getLogger(Spaceport.class);

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


    @Component()
    public static class SpaceportChangedListener {

        @Autowired
        private DataManager dataManager;

        @Autowired
        private SpaceportRepository spaceportRepository;

        @EventListener
        public void spaceportChanged(EntityChangedEvent<Spaceport> event) {
            AttributeChanges changes = event.getChanges();
            if (changes.isChanged(Spaceport.IS_DEFAULT)) {
                Id<Spaceport> entityId = event.getEntityId();

                Spaceport changedSpaceport = dataManager.load(entityId.getEntityClass()).id(entityId.getValue()).one();
                if (changedSpaceport.getIsDefault()) {
                    spaceportRepository.unsetDefaultOtherSpaceports(changedSpaceport);
                }
            }
        }

    }
}