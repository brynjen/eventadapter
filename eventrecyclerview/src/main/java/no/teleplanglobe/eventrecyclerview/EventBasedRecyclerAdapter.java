package no.teleplanglobe.eventrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.internal.util.Predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * EventBasedRecyclerAdapter
 * Used as a container for any data adapter you want to display
 * Created by Brynje Norldi, Teleplan Globe AS on 05/09/16.
 */
public abstract class EventBasedRecyclerAdapter<M, V extends EventBasedViewHolder<M>> extends RecyclerView.Adapter<V> implements EventSubscriber, OnItemClicked<M,V> {
    protected List<M> data = new ArrayList<>();
    private Predicate<M> filter = null;

    /**
     * registerObservers
     * Method for adding the adapter to the eventBus. Before this method is called, no events are noticed.
     * @param topic The topic you want to listen to.
     */
    public void registerObservers(String topic) {
        EventBus.getInstance().subscribe(topic, this);
    }

    /**
     * unregisterObservers
     * Method for cleanly removing the adapter from the Event bus. Without using this when you are done with the adapter, leakage will occur.
     * @param topic Class of object you want to stop listening to. This should be a container/complex class.
     */
    public void unregisterObservers(String topic) {
        EventBus.getInstance().unSubscribe(topic, this);
    }

    /**
     * onBindViewHolder
     * Overridden onBindViewHolder to skip the need to implement it. It will bind the data and implement the click/longclick interfaces. If other requirements are needed, override it in your implementation and super it or do only the needed bind if you do not need clicks
     * @param viewHolder The viewHolder implemented in the adapter. Must extend EventBasedViewHolder.
     * @param position the position in the adapter
     */
    @Override
    public void onBindViewHolder(final V viewHolder, int position) {
        if (viewHolder != null) {
            M obj = data.get(position);
            // TODO: Check for validity of object before binding. Or refuse bind if bind with null?
            viewHolder.bind(obj);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClicked(viewHolder.getAdapterPosition(), data.get(viewHolder.getAdapterPosition()), viewHolder);
                }
            });
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return onItemLongClicked(viewHolder.getAdapterPosition(), data.get(viewHolder.getAdapterPosition()), viewHolder);
                }
            });
        }
    }

    /**
     * setFilter
     * Enables a filter with a predicate to be added, sorting the list based on the predicate given. Automatically updates the set each time the filter is changed.
     * @param predicate A guava predicate deciding which objects is returned
     */
    public void setFilter(Predicate<M> predicate) {
        filter = predicate;
        updateDataSet();
    }

    /**
     * getData()
     * This method must be implemented and return the actual data list, not the internal list the adapter uses.
     * @return List<M>
     */
    public abstract List<M> getData();

    /**
     * getItemCount
     * Returns the number of items shown in the adapter.
     * @return adapters current count
     */
    @Override
    public int getItemCount() {
        return data.size();
    }

    protected void insertEntity(int i, M entity) {
        data.add(i, entity);
        if (Comparable.class.isAssignableFrom(entity.getClass())) {
            List sorted = new ArrayList<>(data);
            Collections.sort(sorted);
            int newIndex = sorted.indexOf(entity);
            if (newIndex != i) {
                moveEntity(i, newIndex);
            } else {
                notifyItemInserted(i);
            }
        } else {
            notifyItemInserted(i);
        }
    }

    /**
     * deleteEntity
     * Deletes the entity and notifies the adapter of the change
     * @param i the location in the adapter of the entity to delete
     */
    protected void deleteEntity(int i) {
        data.remove(i);
        notifyItemRemoved(i);
    }

    /**
     * moveEntity
     * Moves an entity from a location to another location, assuming both locations are valid. This is an internal method.
     * @param i The location to move entity from
     * @param loc The location to add entity at. Note that I remove and add - a manual move
     */
    private void moveEntity(int i, int loc) {
        M temp = data.remove(i);
        data.add(loc, temp);
        notifyItemMoved(i, loc);
    }

    private List<M> filter(Collection<M> col, Predicate<M> predicate) {
        List<M> result = new ArrayList<>();
        for (M el : col) {
            if (predicate.apply(el)) {
                result.add(el);
            }
        }
        return result;
    }

    protected void updateDataSet() {
        List<M> oldEntries = new ArrayList<>(data);
        List<M> newEntries;
        if (filter != null) {
            newEntries = filter(this.getData(), filter);
        } else {
            newEntries = new ArrayList<>(this.getData());
        }
        List<M> del = new ArrayList<>(oldEntries);
        List<M> add = new ArrayList<>(newEntries);
        del.removeAll(newEntries);
        add.removeAll(oldEntries);

        // Remove all deleted items.
        for (int i = del.size() - 1; i >= 0; --i) {
            deleteEntity(data.indexOf(del.get(i)));
        }

        // Add and move items.
        for (int i = 0; i < add.size(); ++i) {
            M entity = add.get(i);
            int loc = getLocation(data, entity);
            if (loc < 0) {
                insertEntity(data.size(), entity);
            } else if (loc != i) {
                moveEntity(i, loc);
            }
        }
    }

    private int getLocation(List<M> data, M entity) {
        for (int j = 0; j < data.size(); ++j) {
            M newEntity = data.get(j);
            if (entity.equals(newEntity)) {
                return j;
            }
        }
        return -1;
    }

    public M objectAtIndex(int position) {
        if (position < 0) return null;
        if (position >= this.getAdapterData().size()) return null;
        return this.getAdapterData().get(position);
    }

    @SuppressWarnings("all")
    public int indexOfObject(M entity) {
        return this.getAdapterData().indexOf(entity);
    }

    public List<M> getAdapterData() {
        return data;
    }

    @Override
    public void objectChanged(Object source) {
        @SuppressWarnings("all")
        int pos = data.indexOf(source);
        notifyItemChanged(pos);
        // TODO: Causes item changed of wrong row? Check..
        if (Comparable.class.isAssignableFrom(source.getClass())) {
            List sorted = new ArrayList<>(data);
            Collections.sort(sorted);
            int newIndex = sorted.indexOf(source);
            if (newIndex != pos) {
                moveEntity(pos, newIndex);
            }
        }
    }

    @Override
    public void listChanged() {
        updateDataSet();
    }
}