package l2nsoft.com;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import l2nsoft.com.activity.OfficeActivity;
import l2nsoft.com.activity.UpdateActivity;
import l2nsoft.com.model.Model;

public class OfficeAdapter extends RecyclerView.Adapter<ViewHolder> {

    OfficeActivity officeActivity;
    List<Model> modelList;

    public OfficeAdapter(OfficeActivity officeActivity, List<Model> modelList) {
        this.officeActivity = officeActivity;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_item, parent, false);


        ViewHolder viewHolder = new ViewHolder(itemview);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.name.setText(modelList.get(position).getName());


        holder.cros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(officeActivity);
                alertDialogBuilder.setMessage("Are you sure, You wanted to delete this Room Name?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                officeActivity.deleteData(position);
                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(officeActivity);
                alertDialogBuilder.setMessage("Are you sure, You wanted to Update this Room Name?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                String id = modelList.get(position).getId();
                                String name = modelList.get(position).getName();
                                Intent intent = new Intent(officeActivity, UpdateActivity.class);
                                intent.putExtra("id", id);
                                intent.putExtra("name", name);
                                officeActivity.startActivity(intent);


                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


}
