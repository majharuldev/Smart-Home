package l2nsoft.com.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import l2nsoft.com.R;

public class UpdateActivity extends AppCompatActivity {

    Button btnUpdate;
    TextInputEditText etName;

    String nid, name;
    ProgressDialog pd;


    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        btnUpdate = findViewById(R.id.btn_update);
        etName = findViewById(R.id.etName);
        pd = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            nid = bundle.getString("id");
            name = bundle.getString("name");
            etName.setText(name);


        }


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null) {

                    String id = nid;
                    String mname = etName.getText().toString();

                    updateData(id, mname);


                } else {


                }

            }
        });


    }

    private void updateData(String id, String mname) {
        pd.setTitle("updating....");
        pd.show();
        db.collection("RoomList").document(id).update("name", mname)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();

                        Toast.makeText(UpdateActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), OfficeActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(UpdateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                });


    }
}