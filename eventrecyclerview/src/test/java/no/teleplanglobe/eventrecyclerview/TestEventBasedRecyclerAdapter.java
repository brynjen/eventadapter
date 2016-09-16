package no.teleplanglobe.eventrecyclerview;

import android.view.View;
import android.view.ViewGroup;

import com.android.internal.util.Predicate;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TestEventBasedRecyclerAdapter
 * Created by Brynje Nordli on 12/09/16.
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class TestEventBasedRecyclerAdapter {
    private EventBasedRecyclerAdapter<DummyClass, DummyViewHolder> testAdapter;
    private List<DummyClass> testData;
    private static final String topic = DummyClass.class.getName();
    private static final String topic2 = DummyClass2.class.getName();
    private Map<String, Integer> eventMap;
    @Before
    public void setUp() throws Exception {
        testData = new EventBasedList<>(topic);
        eventMap = new HashMap<>();

        testAdapter = new EventBasedRecyclerAdapter<DummyClass, DummyViewHolder>() {
            @Override
            public List<DummyClass> getData() {
                return testData;
            }

            @Override
            public DummyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onItemClicked(int row, DummyClass obj, DummyViewHolder viewHolder) {
            }

            @Override
            public boolean onItemLongClicked(int row, DummyClass obj, DummyViewHolder viewHolder) {
                return true;
            }
            @Override
            public void listChanged() {
                eventMap.put("notifyListSizeChanged", eventMap.get("notifyListSizeChanged")==null?1:(eventMap.get("notifyListSizeChanged")+1));
                super.listChanged();
            }
            @Override
            public void objectChanged(Object source) {
                eventMap.put("notifyObjectChanged", eventMap.get("notifyObjectChanged")==null?1:(eventMap.get("notifyObjectChanged")+1));
                super.objectChanged(source);
            }
        };
    }

    @After
    public void tearDown() throws Exception {
        testData.clear();
        testData = null;
        testAdapter.unregisterObservers(topic);
        testAdapter = null;
        eventMap.clear();
        eventMap = null;
    }

    @Test
    public void testRegisterObservers() throws Exception {
        testAdapter.registerObservers(topic);
        Assert.assertEquals(1, EventBus.getInstance().subscribers(topic).size());
    }

    @Test
    public void testUnRegisterObservers() throws Exception {
        testAdapter.registerObservers(topic);
        Assert.assertEquals(1, EventBus.getInstance().subscribers(topic).size());
        testAdapter.unregisterObservers("TotallyUnknownTopic");
        Assert.assertEquals(1, EventBus.getInstance().subscribers(topic).size());
        testAdapter.unregisterObservers(topic);
        Assert.assertEquals(0, EventBus.getInstance().subscribers(topic).size());
    }

    @Test
    public void testAddObject() throws Exception {
        testAdapter.registerObservers(topic);
        testData.add(new DummyClass());
        testData.add(new DummyClass());
        testData.add(new DummyClass());
        testData.add(new DummyClass());
        Assert.assertEquals(4, eventMap.get("notifyListSizeChanged")==null?0:eventMap.get("notifyListSizeChanged"));
    }

    @Test
    public void testRemoveObject() throws Exception {
        testData.add(new DummyClass());
        testData.add(new DummyClass());
        testData.add(new DummyClass());
        testAdapter.registerObservers(topic);
        testData.remove(0);
        testData.remove(0);
        testData.remove(0);
        Assert.assertEquals(3, eventMap.get("notifyListSizeChanged")==null?0:eventMap.get("notifyListSizeChanged"));
    }

    @Test
    public void testChangeObject() throws Exception {
        DummyClass object = new DummyClass();
        testData.add(object);
        testAdapter.registerObservers(topic);
        object.notifyObjectChanged();
        Assert.assertEquals(1, eventMap.get("notifyObjectChanged")==null?0:eventMap.get("notifyObjectChanged"));
    }

    @Test
    public void testOnBindViewHolder() throws Exception {
        View dummyView = new View(RuntimeEnvironment.application.getApplicationContext());
        final Map<String, Boolean> eventMap = new HashMap<>();
        DummyViewHolder dummyViewHolder = new DummyViewHolder(dummyView) {
            @Override
            public void bind(DummyClass objectModel) {
                eventMap.put("bound", true);
            }
        };
        testAdapter.data.add(new DummyClass());
        testAdapter.onBindViewHolder(null, 0);
        Assert.assertFalse(eventMap.get("bound")!=null);
        Assert.assertFalse(dummyViewHolder.itemView.hasOnClickListeners());

        testAdapter.onBindViewHolder(dummyViewHolder, 0);
        Assert.assertTrue(eventMap.get("bound")!=null);
        Assert.assertTrue(dummyViewHolder.itemView.hasOnClickListeners());
    }

    @Test
    public void testItemCount() throws Exception {
        Assert.assertEquals(0, testAdapter.getItemCount());
        testAdapter.data.add(new DummyClass());
        Assert.assertEquals(1, testAdapter.getItemCount());
    }

    @Test
    public void testSetFilter() throws Exception {
        testAdapter.registerObservers(topic);
        testData.add(new DummyClass());
        testAdapter.setFilter(new Predicate<DummyClass>() {
            @Override
            public boolean apply(DummyClass dummyClass) {
                return false;
            }
        });
        Assert.assertEquals(1, eventMap.get("notifyListSizeChanged")==null?0:eventMap.get("notifyListSizeChanged"));
    }

    @Test
    public void testInsertComparableEntity() throws Exception {
        final List<DummyClass2> anotherEventList = new EventBasedList<>(topic2);
        EventBasedRecyclerAdapter<DummyClass2, DummyViewHolder2> testAdapter2 = new EventBasedRecyclerAdapter<DummyClass2, DummyViewHolder2>() {
            @Override
            public List<DummyClass2> getData() {
                return anotherEventList;
            }

            @Override
            public DummyViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onItemClicked(int row, DummyClass2 obj, DummyViewHolder2 viewHolder) {

            }

            @Override
            public boolean onItemLongClicked(int row, DummyClass2 obj, DummyViewHolder2 viewHolder) {
                return false;
            }
        };
        Assert.assertEquals(0, testAdapter2.data.size());
        testAdapter2.insertEntity(0, new DummyClass2("a"));
        Assert.assertEquals(1, testAdapter2.data.size());
    }

    @Test
    public void testInsertNonComparableEntity() throws Exception {
        Assert.assertEquals(0, testAdapter.data.size());
        testAdapter.insertEntity(0, new DummyClass());
        Assert.assertEquals(1, testAdapter.data.size());
    }

    @Test
    public void testDeleteEntity() throws Exception {
        testAdapter.registerObservers(topic);
        testData.add(new DummyClass());

        Assert.assertEquals(1, testAdapter.data.size());
        testAdapter.deleteEntity(0);
        Assert.assertEquals(0, testAdapter.data.size());
    }

    @Test
    public void testMoveEntity() throws Exception {

    }

    @Test
    public void testObjectAtIndex() throws Exception {
        testAdapter.registerObservers(topic);
        DummyClass obj = new DummyClass();
        testData.add(obj);

        Assert.assertEquals(1, testAdapter.data.size());
        Assert.assertEquals(obj, testAdapter.objectAtIndex(0));
    }

    @Test
    public void testIndexOfObject() throws Exception {
        testAdapter.registerObservers(topic);
        DummyClass obj = new DummyClass();
        testData.add(new DummyClass());
        testData.add(obj);

        Assert.assertEquals(2, testAdapter.data.size());
        Assert.assertEquals(1, testAdapter.indexOfObject(obj));
    }

    @Test
    public void testNonComparableObjectChanged() throws Exception {
        testAdapter.registerObservers(topic);
    }

    @Test
    public void testComparableObjectChanged() throws Exception {
        final List<DummyClass2> anotherEventList = new EventBasedList<>(topic2);
        EventBasedRecyclerAdapter<DummyClass2, DummyViewHolder2> testAdapter2 = new EventBasedRecyclerAdapter<DummyClass2, DummyViewHolder2>() {
            @Override
            public List<DummyClass2> getData() {
                return anotherEventList;
            }

            @Override
            public DummyViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onItemClicked(int row, DummyClass2 obj, DummyViewHolder2 viewHolder) {

            }

            @Override
            public boolean onItemLongClicked(int row, DummyClass2 obj, DummyViewHolder2 viewHolder) {
                return false;
            }
        };
        DummyClass2 obj = new DummyClass2("a");
        DummyClass2 obj2 = new DummyClass2("b");
        Assert.assertEquals(0, testAdapter2.data.size());
        testAdapter2.insertEntity(0, obj);
        testAdapter2.insertEntity(0, obj2);
        Assert.assertEquals(2, testAdapter2.data.size());
        obj.setId("c");
        testAdapter2.objectChanged(obj);
    }

    @Test
    public void testGetAdapterData() throws Exception {
        testAdapter.registerObservers(topic);
        List<DummyClass> result = testAdapter.getAdapterData();
        Assert.assertEquals(0, result.size());
        testData.add(new DummyClass());
        result = testAdapter.getAdapterData();
        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testListChanged() throws Exception {

    }
}