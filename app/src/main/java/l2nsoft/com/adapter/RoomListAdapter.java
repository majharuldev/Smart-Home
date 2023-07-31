package l2nsoft.com.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import l2nsoft.com.model.Model;
import l2nsoft.com.R;
import l2nsoft.com.activity.WorkListUpdate;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.ViewHolder> {

    List<Model> modelList;
    Context context;

    public RoomListAdapter(List<Model> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.office_recylcer, parent, false);


        ViewHolder viewHolder = new ViewHolder(itemview);

        return viewHolder;


    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(modelList.get(position).getName());

//        holder.add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setMessage("Are You want add work");
//                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//
//                        String id = modelList.get(position).getId();
//                        String name = modelList.get(position).getName();
//                        Intent intent = new Intent(context, ImageWork.class);
//                        intent.putExtra("roomId", id);
//                        intent.putExtra("roomName", name);
//                        context.startActivity(intent);
//                        //    context.startActivity(new Intent(context, ImageWork.class));
//
//
//                    }
//                });
//
//                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
//
//
//            }
//
//
//        });

        holder.list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = modelList.get(position).getId();
                String name = modelList.get(position).getName();
                Intent intent = new Intent(context, WorkListUpdate.class);
                intent.putExtra("roomId", id);
                intent.putExtra("roomName", name);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView name;

        ImageView add, list;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.name);
            // add = itemView.findViewById(R.id.editImageView);
            list = itemView.findViewById(R.id.crossImageView);


        }
    }
}
