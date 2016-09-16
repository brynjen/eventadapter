package no.teleplanglobe.eventadapter;

/**
 * DummyClass2
 * Created by Brynje Nordli on 13/09/16.
 */
public class DummyClass2 implements Event, Comparable<DummyClass2> {
    private String uid;
    public DummyClass2(String uid) {
        this.uid = uid;
    }
    @Override
    public void notifyObjectChanged() {
        EventBus.getInstance().notifyObjectChanged(DummyClass.class.getName(), this);
    }

    @Override
    public int compareTo(DummyClass2 another) {
        if (another == null) return -1;
        return uid.compareTo(another.uid);
    }

    public void setId(String c) {
        this.uid = c;
    }
}
