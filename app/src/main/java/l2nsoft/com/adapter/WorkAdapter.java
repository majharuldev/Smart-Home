package l2nsoft.com.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import l2nsoft.com.R;
import l2nsoft.com.model.ReportList;
import l2nsoft.com.activity.WorkList;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.ViewHolder> {

    WorkList workList;
    List<ReportList> list;

    public WorkAdapter(WorkList workList, List<ReportList> list) {
        this.workList = workList;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_item, parent, false);


        ViewHolder viewHolder = new ViewHolder(itemview);

        return viewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.des.setText(list.get(position).getDes());
        holder.name.setText(list.get(position).getRoom());
        holder.time.setText(list.get(position).getTime());
        holder.date.setText(list.get(position).getDate());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(workList, "check", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView des, name, time, date;
        ImageView edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            des = itemView.findViewById(R.id.work_des);
            name = itemView.findViewById(R.id.room_name);
            time = itemView.findViewById(R.id.time_duration);
            edit = itemView.findViewById(R.id.editImageView);
            date = itemView.findViewById(R.id.date_time_text);
        }
    }
}
