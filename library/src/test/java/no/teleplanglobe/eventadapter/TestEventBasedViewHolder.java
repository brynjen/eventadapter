package no.teleplanglobe.eventadapter;

import android.widget.TextView;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * TestEventBasedViewHolder
 * Created by Brynje Nordli on 12/09/16.
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class TestEventBasedViewHolder {
    private EventBasedViewHolder<DummyClass> viewHolder;
    private Map<String, Object> eventMap = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        viewHolder = new EventBasedViewHolder<DummyClass>(new TextView(RuntimeEnvironment.application.getApplicationContext())) {
            @Override
            protected void bind(DummyClass objectModel) {
                eventMap.put("bindFired", objectModel);
            }
        };
    }

    @After
    public void tearDown() throws Exception {
        viewHolder = null;
    }

    @Test
    public void testBindHasAccessToObject() throws Exception {
        DummyClass obj = new DummyClass();
        viewHolder.bind(obj);
        Assert.assertEquals(obj, eventMap.get("bindFired"));
    }
}