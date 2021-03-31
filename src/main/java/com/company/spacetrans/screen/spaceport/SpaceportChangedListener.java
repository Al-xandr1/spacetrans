package com.company.spacetrans.screen.spaceport;

import com.company.spacetrans.entity.Spaceport;
import com.company.spacetrans.service.SpaceportService;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.event.AttributeChanges;
import io.jmix.core.event.EntityChangedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SpaceportChangedListener {

    private final DataManager dataManager;

    private final SpaceportService spaceportService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

//    todo  QA this does not work, because listeners are disabled in process of screen detaching
//    @Autowired
//    private UiEventPublisher uiEventPublisher;

    public SpaceportChangedListener(DataManager dataManager, SpaceportService spaceportService) {
        this.dataManager = dataManager;
        this.spaceportService = spaceportService;
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
                applicationEventPublisher.publishEvent(new UIEntityChangedEvent<>(this, event));
//                uiEventPublisher.publishEvent(
//                        new UIEntityChangedEvent<>(this,
//                                                   Id.of(changedSpaceport),
//                                                   EntityChangedEvent.Type.UPDATED,
//                                                   changes,
//                                                   UIEntityChangedEvent.extractMetaClass(event))
//                );

            }
        }
    }
}
