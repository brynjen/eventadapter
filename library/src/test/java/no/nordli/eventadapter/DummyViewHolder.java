package no.nordli.eventadapter;

import android.view.View;

/**
 * DummyViewHolder
 * Created by Brynje Nordli on 13/09/16.
 */
public class DummyViewHolder extends EventBasedViewHolder<DummyClass> {
    public DummyViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void bind(DummyClass objectModel) {

    }
}
