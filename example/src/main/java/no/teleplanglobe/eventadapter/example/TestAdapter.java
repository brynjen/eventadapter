package no.teleplanglobe.eventadapter.example;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import no.teleplanglobe.eventadapter.EventBasedList;
import no.teleplanglobe.eventadapter.EventBasedRecyclerAdapter;
import no.teleplanglobe.eventadapter.EventBasedViewHolder;

/**
 * TestAdapter
 *
 * Created by Brynje Nordli on 05/09/16.
 */
public class TestAdapter extends EventBasedRecyclerAdapter<GitHubber, TestAdapter.ViewHolder> {
    private List<GitHubber> data = new EventBasedList<>(GitHubber.class.getName());

    @Override
    public List<GitHubber> getData() {
        return data;
    }

    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.testrow, parent, false));
    }

    @Override
    public void onItemClicked(int row, GitHubber obj, TestAdapter.ViewHolder viewHolder) {
        obj.setFanaticism(obj.getRandomFanaticism());
    }

    @Override
    public boolean onItemLongClicked(int row, GitHubber obj, TestAdapter.ViewHolder viewHolder) {
        obj.setFanaticism(GitHubber.Fanaticism.MadMan);
        return true;
    }

    public void addObject(GitHubber gitHubber) {
        data.add(gitHubber);
    }

    public void removeObject(GitHubber gitHubber) {
        data.remove(gitHubber);
    }

    protected class ViewHolder extends EventBasedViewHolder<GitHubber> {
        public TextView textView;
        public TextView awesomeNessView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.textView);
            awesomeNessView = (TextView)itemView.findViewById(R.id.awesomeness);
        }

        @Override
        protected void bind(GitHubber objectModel) {
            textView.setText(objectModel.email());
            String builder = "Level:" + objectModel.githHubLevel().toString();
            awesomeNessView.setText(builder);
        }
    }
}
