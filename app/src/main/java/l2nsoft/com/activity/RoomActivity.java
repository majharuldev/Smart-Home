package l2nsoft.com.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import l2nsoft.com.R;

public class RoomActivity extends AppCompatActivity {

    FirebaseDatabase mDatabase;
    DatabaseReference mRefer;
    FirebaseStorage mstorage;
    private ProgressDialog progressDialog;

    FirebaseFirestore db;


    private TextInputEditText etName;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);





        mDatabase = FirebaseDatabase.getInstance();
        mstorage = FirebaseStorage.getInstance();
        progressDialog = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();

        etName = findViewById(R.id.etName);
        fab = findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etName.getText().toString().trim();


                if (TextUtils.isEmpty(name)) {
                    etName.setError("Room name  cannot be empty");
                    etName.requestFocus();


                } else {

                    uploadData(name);

                }


            }
        });


    }

    private void uploadData(String name) {


        progressDialog.setTitle("uploading....");
        progressDialog.show();


        String id = UUID.randomUUID().toString();
        Map<String, Object> doc = new HashMap<>();
        doc.put("id", id);
        doc.put("name", name);

        db.collection("RoomList").document(id).set(doc)

                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        progressDialog.dismiss();
                        Toast.makeText(RoomActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(), OfficeActivity.class));
                        etName.setText("");


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                      ///  Log.d(TAG, "onFailure: " + e.getMessage());

                        progressDialog.dismiss();

                    }
                });


//        Map<String, Object> map = new HashMap<>();
//        map.put("name", name);
//
//        FirebaseDatabase.getInstance().getReference().child("RoomList").push()
//                .setValue(map)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//
//                        progressDialog.dismiss();
//                        Toast.makeText(RoomActivity.this, "success", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(getApplicationContext(), OfficeActivity.class));
//
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        progressDialog.dismiss();
//                        Toast.makeText(RoomActivity.this, "fail", Toast.LENGTH_SHORT).show();
//                        Log.d(TAG, "onFailure: " + e.getMessage());
//                    }
//                });


    }
}