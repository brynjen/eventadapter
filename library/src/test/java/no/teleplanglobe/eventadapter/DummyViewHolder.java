package no.teleplanglobe.eventadapter;

import android.view.View;

/**
 * Created by brynjenordli on 13/09/16.
 */
public class DummyViewHolder extends EventBasedViewHolder<DummyClass> {
    public DummyViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void bind(DummyClass objectModel) {

    }
}
