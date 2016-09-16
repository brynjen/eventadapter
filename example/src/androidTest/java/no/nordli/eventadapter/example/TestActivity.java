package no.nordli.eventadapter.example;

import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import junit.framework.Assert;

/**
 * TestActivity
 * Tests the activity for the adapter to successfully add/change/remove elements when the buttons/list is clicked
 * Created by Brynje Nordli on 12/09/16.
 */
@SuppressWarnings("rawtypes")
public class TestActivity extends ActivityInstrumentationTestCase2 {
    private static final String ACTIVITY_NAME = "MainActivity";
    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "no.nordli.eventadapter.example."+ACTIVITY_NAME;

    private Solo solo;
    private static Class<?> launcherActivityClass;

    static {
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public TestActivity() throws ClassNotFoundException {
        super(launcherActivityClass);
    }

    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation());
        getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testClickingInActivity() {
        Assert.assertTrue("Activity not started correctly", solo.waitForActivity(ACTIVITY_NAME, 2000));
        MainActivity mainActivity = (MainActivity)getActivity();
        TestAdapter adapter = mainActivity.getTestAdapter();
        Assert.assertEquals("Starts with wrong initial value", 0, adapter.getData().size());

        // Fake a click on add button
        solo.clickOnButton(0);
        Assert.assertTrue("Object did not appear in list", solo.waitForText("Level:Meh"));
        Assert.assertEquals("Did not add to list", 1, adapter.getData().size());

        // Fake a click on view in list
        solo.clickLongInRecycleView(0);
        Assert.assertTrue("Object did not change in list", solo.waitForText("Level:MadMan"));

        // Fake a click on the change button
        solo.clickOnButton(1);
        Assert.assertFalse("Change did not work", solo.waitForText("Level:MadMan", 1, 2000));

        // Fake a click on the delete button
        solo.clickOnButton(2);
        Assert.assertTrue("Unable to find RecyclerView", solo.waitForView(RecyclerView.class, 1, 2000));
        Assert.assertEquals("Remove failed", 0, adapter.getData().size());
    }
}