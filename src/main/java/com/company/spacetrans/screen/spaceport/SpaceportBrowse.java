package com.company.spacetrans.screen.spaceport;

import com.company.spacetrans.entity.Spaceport;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

@UiController("st_Spaceport.browse")
@UiDescriptor("spaceport-browse.xml")
@LookupComponent("spaceportsTable")
public class SpaceportBrowse extends StandardLookup<Spaceport> implements PropertyChangeListener {

    @Autowired
    private SpaceportChangedListener spaceportChangedListener;

    @Autowired
    private CollectionLoader<Spaceport> spaceportsDl;

    @Subscribe
    protected void onAfterInit(AfterInitEvent event) {
        spaceportChangedListener.addPropertyChangeListener(this);
    }

    @Subscribe
    public void onBeforeClose(BeforeCloseEvent event) {
        spaceportChangedListener.removePropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        String propertyName = event.getPropertyName();
        if (Spaceport.IS_DEFAULT.equals(propertyName)) {
            spaceportsDl.load();
        }
    }
}