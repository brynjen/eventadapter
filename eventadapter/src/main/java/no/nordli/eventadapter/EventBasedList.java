package no.nordli.eventadapter;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;

/**
 * EventBasedList
 * List which handles sending of events when objects are added or removed.
 * Created by Brynje Nordli on 09/09/16.
 */
public class EventBasedList<M> extends ArrayList<M> {
    private String topic;
    private static final String tag = EventBasedList.class.getName();
    /**
     * EventBasedList
     * List which handles sending of events when objects are added or removed.
     * @param topic The topic used for listening for change events on
     */
    public EventBasedList(String topic) {
        this.topic = topic;
    }

    /**
     * add(int location, M object)
     * This method performs a regular add to the arraylist, as well as sending an event to the given topic that it did so
     * @param location index of object added
     * @param object object added to list
     */
    @Override
    public void add(int location, M object) {
        super.add(location, object);
        EventBus.getInstance().notifyListSizeChanged(topic);
    }

    /**
     * add(M object)
     * This method performs a regular add to the arraylist, as well as sending an event to the given topic that it did so
     * @param object object added to list
     * @return always returns true, at least until this changes in ArrayList
     */
    @Override
    public boolean add(M object) {
        boolean addResult = super.add(object);
        if (addResult) { // Note add always returns true, but adding 'if' in case it is changed later down the line
            EventBus.getInstance().notifyListSizeChanged(topic);
        }
        return addResult;
    }

    /**
     * addAll
     * Method that adds a collection of objects at a given index. Sends a change event if modified
     * @param location index of objects to be added
     * @param collection the collection to be added at the given location
     * @return true for modified or false if not
     */
    @Override
    public boolean addAll(int location, @NonNull Collection<? extends M> collection) {
        boolean result = super.addAll(location, collection);
        if (result) {
            EventBus.getInstance().notifyListSizeChanged(topic);
        }
        return result;
    }

    /**
     * addAll
     * Method that adds a collection of objects at the end of the present list. Sends a change event if modified
     * @param collection the collection to be added
     * @return true for modified or false if not
     */
    @Override
    public boolean addAll(@NonNull Collection<? extends M> collection) {
        boolean result = super.addAll(collection);
        if (result && collection.size() > 0) {
            EventBus.getInstance().notifyListSizeChanged(topic);
        }
        return result;
    }

    /**
     * clear
     * Removes all objects from array and sends a change-event if there is an actual modification
     */
    @Override
    public void clear() {
        if (size() > 0) {
            super.clear();
            EventBus.getInstance().notifyListSizeChanged(topic);
        }
    }

    /**
     * remove
     * Removes the object at the given location and sends a change-event if an actual object is removed
     * @param location the location/index to remove from
     * @return the object removed
     */
    @Override
    public M remove(int location) {
        M object = super.remove(location);
        if (object != null) {
            EventBus.getInstance().notifyListSizeChanged(topic);
        }
        return object;
    }

    /**
     * remove
     * Removes the object and sends a change-event if an actual object is removed
     * @param object the object you want to remove from the list
     * @return true if the object is removed, false if not
     */
    @Override
    public boolean remove(Object object) {
        boolean found = super.remove(object);
        if (found) {
            EventBus.getInstance().notifyListSizeChanged(topic);
        }
        return found;
    }

    /**
     * set
     * Sets the given object at the specified location, sending an event if this causes a modification
     * @param location the index you want to change/replace
     * @param object the object you want to set
     * @return the previous object in the list
     */
    @Override
    public M set(int location, M object) {
        M obj = super.set(location, object);
        Log.i(tag, "Object setted:"+location+", "+obj);
        if (obj != null) {
            EventBus.getInstance().notifyObjectChanged(topic, obj);
        }
        return obj;
    }

    /**
     * removeAll
     * Removes a given collection of objects from the list, sending a change-event if the list is modified
     * @param collection the list of objects you want to remove
     * @return true if modified, else false
     */
    @Override
    public boolean removeAll(@NonNull Collection collection) {
        boolean removed = super.removeAll(collection);
        if (removed) {
            EventBus.getInstance().notifyListSizeChanged(topic);
        }
        return removed;
    }
}
