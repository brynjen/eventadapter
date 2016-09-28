package no.nordli.eventadapter;

/**
 * EventObject
 * Object that can be extended to quickly implement event based change events
 * Created by Brynje Nordli on 28/09/16.
 */
public class EventObject implements Event {
    private String topic;

    /**
     * EventObject
     * Object meant to give you quick access to eventBased notifications when changes occur. Note you need to run the notifyObjectChanged method each time a change occurs for this to work.
     * This should mostly be used for container classes
     * @param topic Topic declares what notification is to be sent
     */
    public EventObject(String topic) {
        this.topic = topic;
    }

    @Override
    public void notifyObjectChanged() {
        EventBus.getInstance().notifyObjectChanged(topic, this);
    }
}
