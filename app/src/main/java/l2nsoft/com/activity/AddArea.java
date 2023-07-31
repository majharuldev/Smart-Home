package l2nsoft.com.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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

public class AddArea extends AppCompatActivity {


    TextInputEditText etAreaName;
    Button btnSub;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_area);


        etAreaName = findViewById(R.id.et_table);
        btnSub = findViewById(R.id.btn_submit);
        pd = new ProgressDialog(this);


        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String arName = etAreaName.getText().toString();


                if (TextUtils.isEmpty(arName)) {
                    etAreaName.setError("Area  cannot be empty");
                    etAreaName.requestFocus();


                } else {

                    pd.setTitle("Loading...");
                    pd.show();

                    Map<String, Object> map = new HashMap<>();
                    map.put("Area_name", arName);
                    FirebaseDatabase.getInstance().getReference().child("Area_List ").push().setValue(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    pd.dismiss();
                                 //   Toast.makeText(AddArea.this, "Upload", Toast.LENGTH_SHORT).show();
                                    etAreaName.setText(""); AlertDialog.Builder builder = new AlertDialog.Builder(AddArea.this);
                                    builder.setMessage("Upload")
                                            .setTitle("")
                                            .setPositiveButton(android.R.string.ok, null);
                                    AlertDialog dialog = builder.create();
                                    dialog.show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(AddArea.this, e.getMessage(), Toast.LENGTH_SHORT).show();


                                }
                            });


                }


            }
        });


    }
}