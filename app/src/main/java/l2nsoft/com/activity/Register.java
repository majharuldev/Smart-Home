package l2nsoft.com.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import l2nsoft.com.R;
import l2nsoft.com.session.LoginSession;

public class Register extends AppCompatActivity {
    TextInputEditText etRegEmail;
    TextInputEditText etRegPassword;
    TextView tvLoginHere;
    Button btnRegister;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    private CheckBox isAdmin, isUser;
    ProgressDialog pd;
    private LoginSession loginSession;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // get the content of both the edit text
            String emailInput = etRegEmail.getText().toString();
            String passwordInput = etRegPassword.getText().toString();

            // check whether both the fields are empty or not
            btnRegister.setEnabled(!emailInput.isEmpty() && !passwordInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPass);
        tvLoginHere = findViewById(R.id.tvLoginHere);
        btnRegister = findViewById(R.id.btnRegister);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        pd = new ProgressDialog(this);
        isAdmin = findViewById(R.id.isAdmin);
        isUser = findViewById(R.id.isUser);


        etRegEmail.addTextChangedListener(textWatcher);
        etRegPassword.addTextChangedListener(textWatcher);


        loginSession = new LoginSession(getApplicationContext());


        isUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isAdmin.setChecked(false);


            }
        });


        isAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isUser.setChecked(false);
            }
        });


        mAuth = FirebaseAuth.getInstance();


        btnRegister.setOnClickListener(view -> {
            createUser();
        });

        tvLoginHere.setOnClickListener(view -> {
            startActivity(new Intent(Register.this, Login.class));
        });
    }

    private void createUser() {


        String email = etRegEmail.getText().toString();
        String password = etRegPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            etRegEmail.setError("Email cannot be empty");
            etRegEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etRegPassword.setError("Password cannot be empty");
            etRegPassword.requestFocus();
        } else if (!(isAdmin.isChecked() || isUser.isChecked())) {

            Toast.makeText(this, "select Account type", Toast.LENGTH_SHORT).show();


        } else {
            pd.setTitle("Creating...");
            pd.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {


                        pd.dismiss();
                        Toast.makeText(Register.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        DocumentReference df = firestore.collection("user").document(user.getUid());
                        Map<String, Object> userInfo = new HashMap<>();
                        userInfo.put("email", email);
                        userInfo.put("password", password);

                        String id = mAuth.getCurrentUser().getUid();

                        //   Toast.makeText(Register.this, id, Toast.LENGTH_SHORT).show();


                        // loginSession.CreateUserSession(id);
                        //  Log.d(TAG, "onComplete: " + user);

                        if (isUser.isChecked()) {
                            userInfo.put("is User", "0");

                        }

                        if (isAdmin.isChecked()) {

                            userInfo.put("is Admin", "1");

                        }


                        df.set(userInfo);

                        startActivity(new Intent(Register.this, Login.class));

                        finish();

                    } else {
                        pd.dismiss();
                        Toast.makeText(Register.this, "email not valid", Toast.LENGTH_SHORT).show();


                    }


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


//            mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                @Override
//                public void onSuccess(AuthResult authResult) {
//                    pd.dismiss();
//                    Toast.makeText(Register.this, "User registered successfully", Toast.LENGTH_SHORT).show();
//                    FirebaseUser user = mAuth.getCurrentUser();
//                    DocumentReference df = firestore.collection("user").document(user.getUid());
//                    Map<String, Object> userInfo = new HashMap<>();
//                    userInfo.put("email", email);
//                    userInfo.put("password", password);
//
//                    if (isUser.isChecked()) {
//                        userInfo.put("is User", "1");
//
//                    }
//
//                    if (isAdmin.isChecked()) {
//
//                        userInfo.put("is Admin", "1");
//
//                    }
//
//
//                    df.set(userInfo);
//
//                    startActivity(new Intent(Register.this, Login.class));
//                    finish();
//
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//
//                    Log.d(TAG, "onFailure: " + e.getMessage());
//
//                    Toast.makeText(Register.this, "fail", Toast.LENGTH_SHORT).show();
//                }
//            });
        }


    }
}