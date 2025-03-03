package com.roomx.domain.event;

import java.util.ArrayList;
import java.util.List;

class DomainEventPublisher {
    private static final DomainEventPublisher instance = new DomainEventPublisher();

    private final List<Object> subscribers = new ArrayList<>();

    private DomainEventPublisher() {
    }

    public static DomainEventPublisher instance() {
        return instance;
    }

    public void register(Object subscriber) {
        subscribers.add(subscriber);
    }

    public void unregister(Object subscriber) {
        subscribers.remove(subscriber);
    }

    public void publish(Object event) {
        // In a real implementation, this would iterate through subscribers
        // and call the appropriate event handler methods.
        System.out.println("Domain Event Published: " + event.getClass().getSimpleName());
    }
}
