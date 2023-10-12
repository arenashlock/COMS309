package com.example.flushd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Aren - Adapter class for the recycler view tied to the job table
 */
public class AdapterJob extends RecyclerView.Adapter<AdapterJob.ViewHolder> {
    /**
     * The array of jobs
     */
    private JSONArray jobs;
    /**
     * the inflater to generate the rows in the view
     */
    private LayoutInflater inflater;
    /**
     * clickListener to coordinate when a user clicks on one of the items
     */
    private ItemClickListener clickListener;

    /**
     * Constructor for the adapter
     * @param context - the screen's context
     * @param data - the array of data to populate the RecyclerView
     */
    // Data passed into the constructor
    AdapterJob(Context context, JSONArray data) {
        this.inflater = LayoutInflater.from(context);
        this.jobs = data;
    }

    /**
     * Inflate the row layout of the view after inflating each row
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return a new ViewHolder of the view after inflating each row
     */
    // Inflates the row layout from the xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.jobrv_row, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Populates the information into the row
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    // Populates the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            JSONObject job = jobs.getJSONObject(position);
            String building = job.getJSONObject("bathroom").getString("building");
            int floor = job.getJSONObject("bathroom").getInt("floor");
            String gender = job.getJSONObject("bathroom").getString("gender");
            holder.bathroomInformation.setText("Building: " + building + "\n" + "Floor: " + String.valueOf(floor) + ", Gender: " + gender);
            String type = job.getString("type");
            String severity = job.getString("severity");
            String datePosted = job.getString("datePosted");
            String status = job.getString("status");
            holder.jobInformation.setText("Type: " + type + ", Severity: " + severity + "\nDate Posted: " + datePosted + " (" + status + ")");
        }   catch (JSONException e){
            holder.jobInformation.setText("No building found");
        }
    }

    /**
     * Returns the number of rows in the RecyclerView
     * @return numItems - the number of items in the job table
     */
    // Returns the number of rows
    @Override
    public int getItemCount() {
        return jobs.length();
    }

    /**
     * Stores and recycles views as they are scrolled off the screen
     */
    // Stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView bathroomInformation, jobInformation;

        ViewHolder(View itemView) {
            super(itemView);
            bathroomInformation = itemView.findViewById(R.id.bathroomInformation);
            jobInformation = itemView.findViewById(R.id.jobInformation);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    /**
     * Method for obtaining a job's information from the database
     * @param id - the id of the item clicked
     * @return Bathroom Object - the object containing the job's information
     */
    // convenience method for getting data at click position
    JSONObject getItem(int id) {
        try {
            return jobs.getJSONObject(id);
        }
        catch (JSONException e){
            return null;
        }
    }

    /**
     * Allows click events to be caught
     * @param itemClickListener - clickListener for the RecyclerView
     */
    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    /**
     * Parent activity implements this method to respond to click events within the RecyclerView
     */
    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}