package no.teleplanglobe.eventrecyclerview;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.Map;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class TestEventBus {
    private EventBus eventBus;

    @Before
    public void setUp() throws Exception {
        eventBus = new EventBus();
    }

    @After
    public void tearDown() throws Exception {
        eventBus = null;
    }

    @Test
    public void testInitialization() throws Exception {
        Assert.assertNotNull(eventBus);
    }

    @Test
    public void testAddSubscriber() throws Exception {
        EventSubscriber subscriber = new EventSubscriber() {
            @Override
            public void objectChanged(Object source) {
            }
            @Override
            public void listChanged() {
            }
        };
        Assert.assertEquals(0, eventBus.subscribers(DummyClass.class.getName()).size());

        eventBus.subscribe(DummyClass.class.getName(), subscriber);
        Assert.assertEquals(1, eventBus.subscribers(DummyClass.class.getName()).size());
        eventBus.unSubscribe(DummyClass.class.getName(), subscriber);
    }

    @Test
    public void testRemoveSubscriber() throws Exception {
        EventSubscriber subscriber = new EventSubscriber() {
            @Override
            public void objectChanged(Object source) {
            }

            @Override
            public void listChanged() {
            }
        };
        EventSubscriber subscriber2 = new EventSubscriber() {
            @Override
            public void objectChanged(Object source) {
            }

            @Override
            public void listChanged() {
            }
        };

        Assert.assertEquals(0, eventBus.subscribers(DummyClass.class.getName()).size());
        eventBus.subscribe(DummyClass.class.getName(), subscriber);
        Assert.assertEquals(1, eventBus.subscribers(DummyClass.class.getName()).size());
        eventBus.unSubscribe(DummyClass.class.getName(), subscriber2);
        Assert.assertEquals(1, eventBus.subscribers(DummyClass.class.getName()).size());
        eventBus.unSubscribe(DummyClass.class.getName(), subscriber);
        Assert.assertEquals(0, eventBus.subscribers(DummyClass.class.getName()).size());
    }

    @Test
    public void testEventFlow() throws Exception {
        DummyClass testObject = new DummyClass();
        final Map<String, Boolean> subMap = new HashMap<>();
        EventSubscriber subscriber = new EventSubscriber() {
            @Override
            public void objectChanged(Object source) {
                subMap.put("notifyObjectChanged", true);
            }

            @Override
            public void listChanged() {
                subMap.put("notifyListSizeChanged", true);
            }
        };

        eventBus.notifyListSizeChanged(DummyClass.class.getName());
        Assert.assertTrue("Listened to value notifyListSizeChanged even though not subscribed", subMap.get("notifyListSizeChanged") == null || !subMap.get("notifyListSizeChanged"));
        eventBus.notifyObjectChanged(DummyClass.class.getName(), testObject);
        Assert.assertTrue("Listened to value notifyObjectChanged even though not subscribed", subMap.get("notifyObjectChanged") == null || !subMap.get("notifyObjectChanged"));

        subMap.clear();
        eventBus.subscribe(DummyClass.class.getName(), subscriber);

        eventBus.notifyListSizeChanged(DummyClass.class.getName());
        Assert.assertTrue("Subscription did not work for notifyListSizeChanged", subMap.get("notifyListSizeChanged"));
        eventBus.notifyObjectChanged(DummyClass.class.getName(), testObject);
        Assert.assertTrue("Subscription did not work for notifyObjectChanged", subMap.get("notifyObjectChanged"));

        subMap.clear();
        eventBus.unSubscribe(DummyClass.class.getName(), subscriber);

        eventBus.notifyListSizeChanged(DummyClass.class.getName());
        Assert.assertTrue("Listened to value notifyListSizeChanged even though not subscribed", subMap.get("notifyListSizeChanged") == null || !subMap.get("notifyListSizeChanged"));
        eventBus.notifyObjectChanged(DummyClass.class.getName(), testObject);
        Assert.assertTrue("Listened to value notifyObjectChanged even though not subscribed", subMap.get("notifyObjectChanged") == null || !subMap.get("notifyObjectChanged"));

        subMap.clear();
    }

    @Test
    public void testUnSubscribeAll() throws Exception {
        EventSubscriber subscriber = new EventSubscriber() {
            @Override
            public void objectChanged(Object source) {

            }

            @Override
            public void listChanged() {

            }
        };
        eventBus.subscribe("test", subscriber);
        eventBus.subscribe("test2", subscriber);
        eventBus.subscribe("test3", subscriber);
        Assert.assertEquals(1, eventBus.subscribers("test").size());
        Assert.assertEquals(1, eventBus.subscribers("test2").size());
        Assert.assertEquals(1, eventBus.subscribers("test3").size());
        eventBus.unSubscribeAll(subscriber);
        Assert.assertEquals(0, eventBus.subscribers("test").size());
        Assert.assertEquals(0, eventBus.subscribers("test2").size());
        Assert.assertEquals(0, eventBus.subscribers("test3").size());
    }
}