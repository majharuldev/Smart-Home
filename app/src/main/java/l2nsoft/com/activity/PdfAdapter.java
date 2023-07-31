package l2nsoft.com.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import l2nsoft.com.PdfClass;
import l2nsoft.com.R;

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.ViewHolder> {


    List<PdfClass> list;
    Context context;

    public PdfAdapter(List<PdfClass> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pdf, parent, false);


        ViewHolder viewHolder = new ViewHolder(itemview);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.textView.setText(list.get(position).getName());

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PdfClass pdfClass = list.get(position);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("application/pdf");
                intent.setData(Uri.parse(pdfClass.url));
                view.getContext().startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            textView = itemView.findViewById(R.id.pdf_name);

        }
    }
}
