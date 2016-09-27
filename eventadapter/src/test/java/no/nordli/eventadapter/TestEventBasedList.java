package no.nordli.eventadapter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * TestEventBasedList
 * Created by Brynje Nordli on 12/09/16.
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class TestEventBasedList {
    private EventBasedList<DummyClass> eventBasedList;

    @Before
    public void setUp() throws Exception {
        eventBasedList = new EventBasedList<>(DummyClass.class.getName());
    }

    @After
    public void tearDown() throws Exception {
        eventBasedList = null;
        EventBus.getInstance().removeAllSubscribers();
    }

    private EventSubscriber getSubscriber(final Map<String, Integer> eventMap) {
        return new EventSubscriber() {
            @Override
            public void objectChanged(Object source) {
                if (eventMap.get("notifyObjectChanged") == null) {
                    eventMap.put("notifyObjectChanged", 1);
                } else {
                    eventMap.put("notifyObjectChanged", eventMap.get("notifyObjectChanged")+1);
                }
            }
            @Override
            public void listChanged() {
                if (eventMap.get("notifyListSizeChanged") == null) {
                    eventMap.put("notifyListSizeChanged", 1);
                } else {
                    eventMap.put("notifyListSizeChanged", eventMap.get("notifyListSizeChanged")+1);
                }
            }
        };
    }

    @Test
    public void testAddFiresEvent() throws Exception {
        final Map<String, Integer> eventMap = new HashMap<>();
        EventSubscriber subscriber = getSubscriber(eventMap);

        Assert.assertEquals(0, EventBus.getInstance().subscribers(DummyClass.class.getName()).size());
        EventBus.getInstance().subscribe(DummyClass.class.getName(), subscriber);
        Assert.assertEquals(1, EventBus.getInstance().subscribers(DummyClass.class.getName()).size());
        eventBasedList.add(new DummyClass());

        Integer value;
        value = eventMap.get("notifyListSizeChanged");
        Assert.assertNotNull(value);
        Assert.assertEquals(1, (int)value);

        eventBasedList.add(0, new DummyClass());
        Assert.assertEquals(2, (int)eventMap.get("notifyListSizeChanged"));

        List<DummyClass> coll = new ArrayList<>();
        coll.add(new DummyClass());
        coll.add(new DummyClass());
        eventBasedList.addAll(coll);
        Assert.assertEquals(3, (int)eventMap.get("notifyListSizeChanged"));
        eventBasedList.addAll(0, coll);
        Assert.assertEquals(4, (int)eventMap.get("notifyListSizeChanged"));
        Assert.assertEquals(6, eventBasedList.size());
    }

    @Test
    public void testRemoveFiresEvent() throws Exception {
        final Map<String, Integer> eventMap = new HashMap<>();
        EventSubscriber subscriber = getSubscriber(eventMap);

        eventBasedList.add(new DummyClass());
        DummyClass obj = new DummyClass();
        eventBasedList.add(obj);
        List<DummyClass> coll = new ArrayList<>();
        coll.add(new DummyClass());
        coll.add(new DummyClass());
        eventBasedList.addAll(coll);
        eventBasedList.add(new DummyClass());

        Assert.assertEquals(0, EventBus.getInstance().subscribers(DummyClass.class.getName()).size());
        EventBus.getInstance().subscribe(DummyClass.class.getName(), subscriber);
        Assert.assertEquals(1, EventBus.getInstance().subscribers(DummyClass.class.getName()).size());

        eventBasedList.remove(0);
        Assert.assertEquals(1, eventMap.get("notifyListSizeChanged")==null?0:eventMap.get("notifyListSizeChanged"));

        eventBasedList.remove(obj);
        Assert.assertEquals(2, eventMap.get("notifyListSizeChanged")==null?0:eventMap.get("notifyListSizeChanged"));

        eventBasedList.remove(obj);
        Assert.assertEquals(2, eventMap.get("notifyListSizeChanged")==null?0:eventMap.get("notifyListSizeChanged"));

        eventBasedList.removeAll(coll);
        Assert.assertEquals(3, eventMap.get("notifyListSizeChanged")==null?0:eventMap.get("notifyListSizeChanged"));

        Assert.assertEquals(1, eventBasedList.size());

        eventBasedList.clear();
        Assert.assertEquals(4, eventMap.get("notifyListSizeChanged")==null?0:eventMap.get("notifyListSizeChanged"));
        Assert.assertEquals(0, eventBasedList.size());
    }

    @Test
    public void testSetObjectFiresEvent() throws Exception {
        final Map<String, Integer> eventMap = new HashMap<>();
        EventSubscriber subscriber = getSubscriber(eventMap);

        eventBasedList.add(new DummyClass());

        Assert.assertEquals(0, EventBus.getInstance().subscribers(DummyClass.class.getName()).size());
        EventBus.getInstance().subscribe(DummyClass.class.getName(), subscriber);
        Assert.assertEquals(1, EventBus.getInstance().subscribers(DummyClass.class.getName()).size());

        DummyClass obj = new DummyClass();
        eventBasedList.set(0, obj);

        Assert.assertEquals(1, eventMap.get("notifyObjectChanged")==null?0:eventMap.get("notifyObjectChanged"));

        eventBasedList.set(0, new DummyClass());

        Assert.assertEquals(2, eventMap.get("notifyObjectChanged")==null?0:eventMap.get("notifyObjectChanged"));
    }
}