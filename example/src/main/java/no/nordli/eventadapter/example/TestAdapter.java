package no.nordli.eventadapter.example;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import no.nordli.eventadapter.EventBasedList;
import no.nordli.eventadapter.EventBasedRecyclerAdapter;
import no.nordli.eventadapter.EventBasedViewHolder;

/**
 * TestAdapter
 * <p>
 * Created by Brynje Nordli on 05/09/16.
 */
class TestAdapter extends EventBasedRecyclerAdapter<GitHubber, TestAdapter.ViewHolder> {
    private EventBasedList<GitHubber> data = new EventBasedList<>(GitHubber.class.getName());

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

    void addObject(GitHubber gitHubber) {
        data.add(gitHubber);
    }

    void removeObject(GitHubber gitHubber) {
        data.remove(gitHubber);
    }

    class ViewHolder extends EventBasedViewHolder<GitHubber> {
        TextView textView;
        TextView awesomeNessView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            awesomeNessView = (TextView) itemView.findViewById(R.id.awesomeness);
        }

        @Override
        protected void bind(GitHubber objectModel) {
            textView.setText(objectModel.email());
            String builder = "Level:" + objectModel.gitHubLevel().toString();
            awesomeNessView.setText(builder);
        }
    }
}
