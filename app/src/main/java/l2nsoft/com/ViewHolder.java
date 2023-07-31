package l2nsoft.com;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    View mview;
    ImageView edit, cros;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);


        mview = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // mclickListener.onItemLongClick(view, getAbsoluteAdapterPosition());

            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                return false;
            }
        });

        name = itemView.findViewById(R.id.name);
        edit = itemView.findViewById(R.id.editImageView);
        cros = itemView.findViewById(R.id.crossImageView);


    }


    private ViewHolder.ClickListener mclickListener;

    public interface ClickListener {

        void OnItemClick(View view, int position);

        void onItemLongClick(View view, int position);

    }


    public void setOnClickListener(ViewHolder.ClickListener clickListener) {

        mclickListener = clickListener;

    }
}
