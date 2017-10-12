package com.ttuicube.dibzitapp.modules.timeslots;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ttuicube.dibzitapp.R;
import com.ttuicube.dibzitapp.model.TimeSlot;
import com.ttuicube.dibzitapp.modules.rooms.RoomsActivity;

import java.util.List;

/**
 * Created by zeejfps on 10/10/2017.
 */

public class TimeSlotsAdapter extends
        RecyclerView.Adapter<TimeSlotsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView timeTextView;
        public final TextView roomCountTextView;

        private TimeSlot timeSlot;

        public ViewHolder(View itemView) {
            super(itemView);
            timeTextView = (TextView) itemView.findViewById(R.id.timeTextView);
            roomCountTextView = (TextView) itemView.findViewById(R.id.roomCountTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (timeSlot != null) {
                        Intent intent = new Intent(context, RoomsActivity.class);
                        intent.putExtra(RoomsActivity.TIME_SLOT_KEY, timeSlot);
                        context.startActivity(intent);
                    }
                }
            });
        }

        public void setTimeSlot(TimeSlot slot) {
            this.timeSlot = slot;
            timeTextView.setText(slot.startTime.toString("hh:00 aa") + " - " + slot.endTime.toString("hh:00 aa"));
            roomCountTextView.setText(Integer.toString(slot.rooms.size()));
        }

    }

    private final Context context;
    private List<TimeSlot> timeSlots;

    public TimeSlotsAdapter(Context context, List<TimeSlot> timeSlots) {
        this.context = context;
        this.timeSlots = timeSlots;
    }

    public void setData(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.listitem_timeslot, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TimeSlot slot = timeSlots.get(position);
        holder.setTimeSlot(slot);
    }

    @Override
    public int getItemCount() {
        return timeSlots.size();
    }

}
