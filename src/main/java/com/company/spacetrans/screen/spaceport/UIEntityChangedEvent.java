package com.company.spacetrans.screen.spaceport;

import io.jmix.core.event.EntityChangedEvent;
import org.springframework.context.ApplicationEvent;

/**
 * Понятно, что этот класс не предполагается расширять, сделал для эксперимента по идее UI событий.
 *
 * @param <T>
 */
public class UIEntityChangedEvent<T> extends ApplicationEvent {

    private final EntityChangedEvent<T> cause;

    public UIEntityChangedEvent(Object source, EntityChangedEvent<T> cause) {
        super(source);
        this.cause = cause;
    }

    public EntityChangedEvent<T> getCause() {
        return cause;
    }
}
