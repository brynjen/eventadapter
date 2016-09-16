package no.teleplanglobe.eventadapter;

/**
 * OnItemClicked
 * Interface that exposes when a row in an eventAdapter is clicked or longClicked
 * Created by Brynje Nordli on 05/09/16.
 */
public interface OnItemClicked<M,V> {
    /**
     * onItemClicked
     * Method for handling clicks on a row
     * @param row Denotes the adapters row clicked.
     * @param obj Object model contained in the clicked row.
     * @param viewHolder Reference to the viewHolder clicked so any kind of manipulation or data retrieving can be done.
     */
    void onItemClicked(int row, M obj, V viewHolder);
    /**
     * onItemLongClicked
     * Method for handling long clicks on a row.
     * @param row Denotes the adapters row long clicked
     * @param obj Object model contained in the long clicked row
     * @param viewHolder Reference to the viewHolder long clicked so any kind of manipulation or data retrieving can be done.
     * @return true if there are no more interaction with the touch event. If false, click event will also fire.
     */
    boolean onItemLongClicked(int row, M obj, V viewHolder);
}
