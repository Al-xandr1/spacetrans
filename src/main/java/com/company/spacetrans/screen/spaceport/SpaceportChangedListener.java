package com.company.spacetrans.screen.spaceport;

import com.company.spacetrans.entity.Spaceport;
import com.company.spacetrans.service.SpaceportService;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.event.AttributeChanges;
import io.jmix.core.event.EntityChangedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

@Component
public class SpaceportChangedListener {

    private final DataManager dataManager;

    private final SpaceportService spaceportService;

    private final PropertyChangeSupport propertyChangeSupport;

    public SpaceportChangedListener(DataManager dataManager, SpaceportService spaceportService) {
        this.dataManager = dataManager;
        this.spaceportService = spaceportService;
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    @EventListener
    public void spaceportChanged(EntityChangedEvent<Spaceport> event) {
        AttributeChanges changes = event.getChanges();
        if (changes.isChanged(Spaceport.IS_DEFAULT)) {
            Id<Spaceport> entityId = event.getEntityId();

            Spaceport changedSpaceport = dataManager.load(entityId.getEntityClass())
                                                    .id(entityId.getValue())
                                                    .one();
            if (changedSpaceport.getIsDefault()) {
                spaceportService.unsetDefaultOtherSpaceports(changedSpaceport);
                propertyChangeSupport
                        .firePropertyChange(Spaceport.IS_DEFAULT, changes.getOldValue(Spaceport.IS_DEFAULT), changedSpaceport.getIsDefault());
            }
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
