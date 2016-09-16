package no.teleplanglobe.eventrecyclerview;

/**
 * DummyClass
 * Test class for testing functionality of eventRecycler
 * Created by brynjenordli on 12/09/16.
 */
public class DummyClass implements Event {
    @Override
    public void notifyObjectChanged() {
        EventBus.getInstance().notifyObjectChanged(DummyClass.class.getName(), this);
    }
}