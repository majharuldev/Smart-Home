package l2nsoft.com.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import l2nsoft.com.model.Model;
import l2nsoft.com.R;
import l2nsoft.com.adapter.RoomListAdapter;

public class Roomlist extends AppCompatActivity {

    List<Model> modelList = new ArrayList<>();

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore db;
    RoomListAdapter adapter;

    ProgressDialog progressDialog;
    private Object OfficeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomlist);


        progressDialog = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // fab = findViewById(R.id.fab);

        showdata();


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


                adapter = new RoomListAdapter(modelList, Roomlist.this);


                recyclerView.setAdapter(adapter);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }
}