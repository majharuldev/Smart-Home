package l2nsoft.com;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class RoomAdapter extends FirebaseRecyclerAdapter<RoomResponse, RoomAdapter.ViewHolder> {


    public RoomAdapter(@NonNull FirebaseRecyclerOptions<RoomResponse> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull RoomResponse model) {

        holder.name.setText(model.getTitle());
        holder.date.setText(model.getStarttime());
        holder.time.setText(model.getEndtime());
        holder.des.setText(model.getDetails());
        Picasso.get().load(model.getImage()).into(holder.imageView);


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        return new ViewHolder(view);


    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView des, name, time, date;
        ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            des = itemView.findViewById(R.id.work_des);
            name = itemView.findViewById(R.id.room_name);
            time = itemView.findViewById(R.id.time_duration);
            date = itemView.findViewById(R.id.date_time_text);
            imageView = itemView.findViewById(R.id.image_show);
        }
    }
}
