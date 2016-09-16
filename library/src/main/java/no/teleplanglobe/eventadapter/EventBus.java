package no.teleplanglobe.eventadapter;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * EventBus
 * Class for handling event sending of data changes. Will always send the changes on the main thread so that the ui can handle them.
 * Created by Brynje Nordli on 09/09/16.
 */
public class EventBus {
    private final static HashMap<String, Set<EventSubscriber>> subscribersPerTopic = new HashMap<>();
    private static EventBus instance = null;

    @MainThread
    public static EventBus getInstance() {
        if (instance == null) {
            synchronized (EventBus.class) {
                instance = new EventBus();
            }
        }
        return instance;
    }

    /**
     * subscribers
     * Test method for finding subscribers of a particular class
     * @param topic The topic you want to find subscribers of
     * @return The set with listeners
     */
    @MainThread
    protected Set<EventSubscriber> subscribers(@NonNull String topic) {
        Set<EventSubscriber> subscribers = subscribersPerTopic.get(topic);
        if (subscribers == null) return new HashSet<>();
        return subscribers;
    }

    /**
     * removeAllSubscribers
     * Test method for manually clearing all subscribers
     */
    @MainThread
    protected void removeAllSubscribers() {
        subscribersPerTopic.clear();
    }

    /**
     * subscribe
     * Adds a subscriber with a given class/type. There are no way
     * @param topic
     * @param subscriber
     */
    @MainThread
    public void subscribe(@NonNull String topic, @NonNull EventSubscriber subscriber) {
        Set<EventSubscriber> subscribers = subscribersPerTopic.get(topic);
        if (subscribers == null) {
            subscribers = new CopyOnWriteArraySet<>();
        }
        subscribers.add(subscriber);
        subscribersPerTopic.put(topic, subscribers);
    }

    /**
     * unSubscribe
     * Removes a given subscriber from the class type you previously subscribed to
     * @param topic The topic you want to unSubscribe from
     * @param subscriber The subscriber you want to remove the class type subscription from
     */
    @MainThread
    public void unSubscribe(@NonNull String topic, @NonNull EventSubscriber subscriber) {
        Set<EventSubscriber> subscribers = subscribersPerTopic.get(topic);
        if (subscribers == null) return;
        subscribers.remove(subscriber);
        subscribersPerTopic.put(topic, subscribers);
    }

    /**
     * unSubscribeAll
     * Removes all topic subscriptions from a given subscriber
     * @param subscriber The subscriber you want to remove all events from
     */
    @MainThread
    public void unSubscribeAll(@NonNull EventSubscriber subscriber) {
        for (Set<EventSubscriber> subscribers : subscribersPerTopic.values()) {
            subscribers.remove(subscriber);
        }
    }

    /**
     * notifyObjectChanged
     * Method for the EventBus to notify to all subscribers that a single row of a specific type has been changed (given the model's class).
     * Note that it will always post this on the main thread regardless of thread it is on.
     * @param model The object that is changing its data
     */
    @MainThread
    public void notifyObjectChanged(String topic, @NonNull final Object model) {
        Set<EventSubscriber> subscribers = subscribersPerTopic.get(topic);
        if (subscribers == null) return;
        for (EventSubscriber subscriber : subscribers) {
            subscriber.objectChanged(model);
        }
    }

    /**
     * notifyListSizeChanged
     * Method for the EventBus to notify to all subscribers that the number of rows changed regarding the given objects class.
     * Note that it will always post this on the main thread regardless of thread it is on.
     * @param topic The topic that is changing rows
     */
    @MainThread
    public void notifyListSizeChanged(String topic) {
        Set<EventSubscriber> subscribers = subscribersPerTopic.get(topic);
        if (subscribers == null) return;
        for (EventSubscriber subscriber : subscribers) {
            subscriber.listChanged();
        }
    }
}