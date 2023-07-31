package l2nsoft.com.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import l2nsoft.com.R;
import l2nsoft.com.model.ReportList;
import l2nsoft.com.adapter.WorkAdapter;

public class WorkList extends AppCompatActivity {


    FloatingActionButton fab;


    List<ReportList> modelList = new ArrayList<>();

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore db;
    WorkAdapter workAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_list);


      //  fab = findViewById(R.id.next);

        progressDialog = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), Roomlist.class));
//            }
//        });


        shoData();


    }

    private void shoData() {

        progressDialog.setTitle("Loading...");
        progressDialog.show();


        db.collection("WorkReport").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {


                progressDialog.dismiss();

                for (DocumentSnapshot doc : task.getResult()) {
                    ReportList model = new ReportList(doc.getString("date"), doc.getString("des"), doc.getString("room"), doc.getString("time"));

                    modelList.add(model);


                }


                workAdapter = new WorkAdapter(WorkList.this, modelList);


                recyclerView.setAdapter(workAdapter);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(WorkList.this, e.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });


    }
}