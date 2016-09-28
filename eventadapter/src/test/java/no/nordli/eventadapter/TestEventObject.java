package no.nordli.eventadapter;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Brynje Nordli on 28/09/16.
 */

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class TestEventObject {
    private EventObject eventObject;
    private String topic = "testTopic";

    @Before
    public void setUp() throws Exception {
        eventObject = new EventObject(topic);
    }

    @After
    public void tearDown() throws Exception {
        eventObject = null;
    }

    @Test
    public void testEventSentWhenChange() throws Exception {
        final Map<String, Integer> eventMap = new HashMap<>();
        EventSubscriber subscriber = new EventSubscriber() {
            @Override
            public void objectChanged(Object source) {
                if (eventMap.get(topic) == null) {
                    eventMap.put(topic, 1);
                } else {
                    eventMap.put(topic, (eventMap.get(topic)+1));
                }
            }

            @Override
            public void listChanged() {

            }
        };
        EventBus.getInstance().subscribe(topic, subscriber);
        Assert.assertEquals(0, eventMap.get(topic)==null?0:eventMap.get(topic));
        eventObject.notifyObjectChanged();
        Assert.assertEquals(1, eventMap.get(topic)==null?0:eventMap.get(topic));

        EventBus.getInstance().unSubscribe(topic, subscriber);
        eventObject.notifyObjectChanged();
        Assert.assertEquals(1, eventMap.get(topic)==null?0:eventMap.get(topic));
    }
}