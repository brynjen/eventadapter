package no.nordli.eventadapter.example;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.UUID;

public class MainActivity extends Activity {
    private TestAdapter testAdapter;
    private static final String tag = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        testAdapter = new TestAdapter();
        if (recyclerView != null) {
            recyclerView.setAdapter(testAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        }
    }

    public TestAdapter getTestAdapter() {
        return testAdapter;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (testAdapter != null) {
            testAdapter.registerObservers(GitHubber.class.getName());
        }
//        stressTest();
    }

    // Warning: running this method will screw up the tests
    private void stressTest() {
        long timestamp = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            addItem(null);
        }
        Log.i(tag, "NORA: Time spent in milliseconds to add 1000 to list:"+(System.currentTimeMillis()-timestamp));
    }

    @Override
    public void onPause() {
        super.onPause();
        if (testAdapter != null) {
            testAdapter.unregisterObservers(GitHubber.class.getName());
        }
    }

    public void addItem(View view) {
        testAdapter.addObject(new GitHubber(UUID.randomUUID().toString(), GitHubber.Fanaticism.Meh));
    }

    public void removeItem(View view) {
        if (testAdapter.getData().size() > 0) {
            // Note that I am removing the first object in the adapter (since the adapter shows a sorted list)
            testAdapter.removeObject(testAdapter.getAdapterData().get(0));
        }
    }

    public void changeItem(View view) {
        if (testAdapter.getData().size() > 0) {
            // Note also here I'm using the first object of the adapter since the list is sorted
            GitHubber gitHubber = testAdapter.getAdapterData().get(0);
            gitHubber.setEmail(UUID.randomUUID().toString());
            gitHubber.setFanaticism(gitHubber.getRandomFanaticism());
        }
    }
}