package l2nsoft.com.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import l2nsoft.com.R;

public class FurnitureActivity extends AppCompatActivity {

    TextInputEditText etChair, ettable;
    Button btnSubmit;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furniture);


        pd = new ProgressDialog(this);

        etChair = findViewById(R.id.etchair);
        ettable = findViewById(R.id.etTable);

        btnSubmit = findViewById(R.id.btnsubmit);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String cName = etChair.getText().toString();
                String tName = ettable.getText().toString();

                if (TextUtils.isEmpty(cName)) {
                    etChair.setError("Email cannot be empty");
                    etChair.requestFocus();
                } else if (TextUtils.isEmpty(tName)) {
                    ettable.setError("Password cannot be empty");
                    ettable.requestFocus();
                } else {

                    pd.setTitle("Loading.....");
                    pd.show();


                    Map<String, Object> map = new HashMap<>();
                    map.put("Chair_name", cName);
                    map.put("table_name", tName);


                    FirebaseDatabase.getInstance().getReference().child("Furniture").push()

                            .setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    pd.dismiss();
                                    Toast.makeText(FurnitureActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    ettable.setText("");
                                    etChair.setText("");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {


                                    Toast.makeText(FurnitureActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                }


            }
        });


    }
}