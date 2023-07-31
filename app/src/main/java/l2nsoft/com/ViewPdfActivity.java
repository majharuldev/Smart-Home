package l2nsoft.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import l2nsoft.com.activity.PdfAdapter;

public class ViewPdfActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    List<PdfClass> uploadpdf;

    PdfAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);

        fab = findViewById(R.id.fab_view);
        uploadpdf = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        retrivePdf();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UploadPdf.class));
            }
        });


    }

    private void retrivePdf() {

        databaseReference = FirebaseDatabase.getInstance().getReference("pdfFile");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {


                    PdfClass pdfClass = ds.getValue(l2nsoft.com.PdfClass.class);

                    uploadpdf.add(pdfClass);

                    adapter = new PdfAdapter(uploadpdf, ViewPdfActivity.this);


                    recyclerView.setAdapter(adapter);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
