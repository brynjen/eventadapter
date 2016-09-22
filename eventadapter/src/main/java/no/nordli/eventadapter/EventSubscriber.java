package no.nordli.eventadapter;

/**
 * EventSubscriber
 * Interface for the EventBus for notifying when
 * Created by Brynje Nordli on 09/09/16.
 */
public interface EventSubscriber {
    /**
     * objectChanged
     * Fired whenever a row is changed but not added/deleted/moved. The only thing changed can be internal data in the object
     * @param source The object changed, enabling interaction to happen
     */
    void objectChanged(Object source);

    /**
     * listChanged
     * Whenever a row is changed (by adding, deleting or moving) this interface should be fired on the EventBus
     */
    void listChanged();
}