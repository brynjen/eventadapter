package no.teleplanglobe.eventadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * EventBasedViewHolder
 * Specially designed ViewHolder with generic template that abstracts out the binding of a generic data to the method.
 * Used to handle all kinds of data types in the recycler
 * Created by Brynje Nordli on 06/09/16.
 */
public abstract class EventBasedViewHolder<M> extends RecyclerView.ViewHolder {
    public EventBasedViewHolder(View itemView) {
        super(itemView);
    }

    /**
     * bind
     * Method that lets the adapter bind an object model up to the viewHolder.
     * @param objectModel the object used to bind to the viewHolder
     */
    protected abstract void bind(M objectModel);
}