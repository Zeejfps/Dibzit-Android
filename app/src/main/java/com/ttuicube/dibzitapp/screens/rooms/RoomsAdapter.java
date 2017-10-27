package com.ttuicube.dibzitapp.screens.rooms;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ttuicube.dibzitapp.R;
import com.ttuicube.dibzitapp.models.DibsRoom;

import java.util.List;

/**
 * Created by Zeejfps on 10/11/17.
 */

public class RoomsAdapter extends
        RecyclerView.Adapter<RoomsAdapter.ViewHolder> {

    public interface RoomSelectedCallback {
        void onRoomSelected(DibsRoom room);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final ImageView roomThumbnail;
        public final TextView roomNameLabel;
        public final TextView roomDescriptionLabel;

        private DibsRoom room;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (room != null) {
                        callback.onRoomSelected(room);
                    }
                }
            });
            roomThumbnail = (ImageView) itemView.findViewById(R.id.roomThumbnail);
            roomDescriptionLabel = (TextView) itemView.findViewById(R.id.roomDescriptionLabel);
            roomNameLabel = (TextView) itemView.findViewById(R.id.roomNameLabel);
        }

        public void setDibsRoom(DibsRoom room) {
            this.room = room;
            roomNameLabel.setText(room.name.substring(0, 1).toUpperCase() + room.name.substring(1));
            roomDescriptionLabel.setText(room.description);
        }

    }

    private List<DibsRoom> rooms;
    private final RoomSelectedCallback callback;

    public RoomsAdapter(List<DibsRoom> rooms, RoomSelectedCallback callback) {
        this.rooms = rooms;
        this.callback = callback;
    }

    @Override
    public RoomsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.listitem_room, parent, false);
        RoomsAdapter.ViewHolder viewHolder = new RoomsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RoomsAdapter.ViewHolder holder, int position) {
        DibsRoom room = rooms.get(position);
        holder.setDibsRoom(room);
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public void setData(List<DibsRoom> rooms) {
        this.rooms = rooms;
        notifyDataSetChanged();
    }

}
