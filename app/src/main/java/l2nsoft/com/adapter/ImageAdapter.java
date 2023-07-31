package l2nsoft.com.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import l2nsoft.com.model.ImageList;
import l2nsoft.com.R;

public class ImageAdapter extends FirebaseRecyclerAdapter<ImageList, ImageAdapter.ViewHolder> {

    public ImageAdapter(@NonNull FirebaseRecyclerOptions<ImageList> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ImageList model) {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
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
