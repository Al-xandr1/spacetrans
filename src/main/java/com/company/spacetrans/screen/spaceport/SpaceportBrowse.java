package com.company.spacetrans.screen.spaceport;

import com.company.spacetrans.entity.Spaceport;
import io.jmix.ui.action.list.RefreshAction;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

@UiController("st_Spaceport.browse")
@UiDescriptor("spaceport-browse.xml")
@LookupComponent("spaceportsTable")
public class SpaceportBrowse extends StandardLookup<Spaceport> implements PropertyChangeListener {

    @Autowired
    private SpaceportChangedListener spaceportChangedListener;

    @Named("spaceportsTable.refresh")
    private RefreshAction spaceportsTableRefresh;

    public SpaceportBrowse() {
        addAfterInitListener(event -> spaceportChangedListener.addPropertyChangeListener(this));
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        String propertyName = event.getPropertyName();
        switch (propertyName) {
            case Spaceport.IS_DEFAULT: {
                spaceportsTableRefresh.execute();
            }
        }
    }

/*  alternative method of table refreshing after changing 'isDefault' attribute
    @Subscribe(id = "spaceportsDc", target = Target.DATA_CONTAINER)
    public void onSpaceportsDcCollectionChange(CollectionContainer.CollectionChangeEvent<Spaceport> event) {
        CollectionChangeType changeType = event.getChangeType();
        switch (changeType) {
            case ADD_ITEMS:
            case REMOVE_ITEMS:
            case SET_ITEM: {
                spaceportsTableRefresh.execute();
            }
        }
    }
*/
}