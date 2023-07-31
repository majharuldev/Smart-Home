package l2nsoft.com.adapter;

import static android.widget.Toast.makeText;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import l2nsoft.com.activity.DetailsActivity;
import l2nsoft.com.R;
import l2nsoft.com.model.VideoListresponse;

public class VideoAdapter extends FirebaseRecyclerAdapter<VideoListresponse, VideoAdapter.ViewHolder> {

    public VideoAdapter(@NonNull FirebaseRecyclerOptions<VideoListresponse> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull VideoListresponse model) {

        holder.address.setText(model.getAddress());
      // Picasso.get().load().into(holder.image);



        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





            }
        });


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list, parent, false);


        ViewHolder viewHolder = new ViewHolder(itemview);

        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView address;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.crossImageView);
            address = itemView.findViewById(R.id.text_address);


        }
    }
}
