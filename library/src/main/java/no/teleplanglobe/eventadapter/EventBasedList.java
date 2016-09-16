package no.teleplanglobe.eventadapter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * EventBasedList
 * List which handles sending of events when objects are added or removed.
 * Created by Brynje Nordli on 09/09/16.
 */
public class EventBasedList<M> extends ArrayList<M> {
    private String topic;

    public EventBasedList(String topic) {
        this.topic = topic;
    }

    @Override
    public void add(int location, M object) {
        super.add(location, object);
        EventBus.getInstance().notifyListSizeChanged(topic);
    }

    @Override
    public boolean add(M object) {
        super.add(object);
        EventBus.getInstance().notifyListSizeChanged(topic);
        return true;
    }

    @Override
    public boolean addAll(int location, @NonNull Collection collection) {
        boolean result = super.addAll(location, collection);
        if (result) {
            EventBus.getInstance().notifyListSizeChanged(topic);
        }
        return result;
    }

    @Override
    public boolean addAll(@NonNull Collection collection) {
        boolean result = super.addAll(collection);
        if (result && collection.size() > 0) {
            EventBus.getInstance().notifyListSizeChanged(topic);
        }
        return result;
    }

    @Override
    public void clear() {
        if (size() > 0) {
            super.clear();
            EventBus.getInstance().notifyListSizeChanged(topic);
        }
    }

    @Override
    public M remove(int location) {
        M object = super.remove(location);
        if (object != null) {
            EventBus.getInstance().notifyListSizeChanged(topic);
        }
        return object;
    }

    @Override
    public boolean remove(Object object) {
        boolean found = super.remove(object);
        if (found) {
            EventBus.getInstance().notifyListSizeChanged(topic);
        }
        return found;
    }

    @Override
    public M set(int location, M object) {
        M obj = super.set(location, object);
        if (obj != null) {
            EventBus.getInstance().notifyListSizeChanged(topic);
        }
        return obj;
    }

    @Override
    public boolean removeAll(@NonNull Collection collection) {
        boolean removed = super.removeAll(collection);
        if (removed) {
            EventBus.getInstance().notifyListSizeChanged(topic);
        }
        return removed;
    }
}
