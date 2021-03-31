
package com.company.spacetrans.screen.spaceport;

import com.company.spacetrans.entity.Spaceport;
import io.jmix.core.event.AttributeChanges;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@UiController("st_Spaceport.browse")
@UiDescriptor("spaceport-browse.xml")
@LookupComponent("spaceportsTable")
public class SpaceportBrowse extends StandardLookup<Spaceport> {

    @Autowired
    private CollectionLoader<Spaceport> spaceportsDl;

    @Autowired
    private SpringUIEventListener springUIEventListener;

    @Subscribe
    public void onAfterInit(AfterInitEvent event) {
        springUIEventListener.setBrowser(this);
    }


    @Component
    private static class SpringUIEventListener {

        private SpaceportBrowse browser;

        public void setBrowser(SpaceportBrowse spaceportBrowse) {
            this.browser = spaceportBrowse;
        }

        @EventListener
        public void propertyChange(UIEntityChangedEvent<Spaceport> event) {
            AttributeChanges changes = event.getCause().getChanges();
            if (changes.isChanged(Spaceport.IS_DEFAULT)) {
                browser.spaceportsDl.load();
            }
        }
    }
}