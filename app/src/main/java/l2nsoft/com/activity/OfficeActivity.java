package l2nsoft.com.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import l2nsoft.com.model.Model;
import l2nsoft.com.OfficeAdapter;
import l2nsoft.com.R;


public class OfficeActivity extends AppCompatActivity {


    List<Model> modelList = new ArrayList<>();

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore db;
    OfficeAdapter officeAdapter;

    ProgressDialog progressDialog;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office);


        progressDialog = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        fab = findViewById(R.id.fab);

        showdata();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RoomActivity.class));
            }
        });

    }

    private void showdata() {

        progressDialog.setTitle("loading");
        progressDialog.show();

        db.collection("RoomList").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                progressDialog.dismiss();


                for (DocumentSnapshot doc : task.getResult()) {
                    Model model = new Model(doc.getString("id"), doc.getString("name"));
                    modelList.add(model);


                }


//                officeAdapter = new OfficeAdapter(modelList, OfficeActivity.this);


                officeAdapter = new OfficeAdapter(OfficeActivity.this, modelList);


                recyclerView.setAdapter(officeAdapter);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }


    public void deleteData(int position) {


        progressDialog.setTitle("Deleteing");
        progressDialog.show();

        db.collection("RoomList").document(modelList.get(position).getId())
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        progressDialog.dismiss();
                        modelList.clear();
                        Toast.makeText(OfficeActivity.this, "Delete", Toast.LENGTH_SHORT).show();

                        showdata();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(OfficeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                });


    }


}