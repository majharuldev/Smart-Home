package l2nsoft.com.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import l2nsoft.com.R;
import l2nsoft.com.session.LoginSession;

public class Login extends AppCompatActivity {


    TextInputEditText etLoginEmail;
    TextInputEditText etLoginPassword;
    TextView tvRegisterHere, mtext;
    Button btnLogin;

    FirebaseAuth mAuth;
    FirebaseFirestore firestore;

    ProgressDialog pd;

    String uid;
    private LoginSession loginSession;


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // get the content of both the edit text
            String emailInput = etLoginEmail.getText().toString();
            String passwordInput = etLoginPassword.getText().toString();

            // check whether both the fields are empty or not
            btnLogin.setEnabled(!emailInput.isEmpty() && !passwordInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPass);
        tvRegisterHere = findViewById(R.id.tvRegisterHere);
        btnLogin = findViewById(R.id.btnLogin);
        mtext = findViewById(R.id.marqueeText);

        pd = new ProgressDialog(this);
        mtext.setSelected(true);
        loginSession = new LoginSession(this);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        etLoginEmail.addTextChangedListener(textWatcher);
        etLoginPassword.addTextChangedListener(textWatcher);

        btnLogin.setOnClickListener(view -> {
            loginUser();
        });
        tvRegisterHere.setOnClickListener(view -> {
            startActivity(new Intent(Login.this, Register.class));
        });
    }

    private void loginUser() {


        String email = etLoginEmail.getText().toString();
        String password = etLoginPassword.getText().toString();


        if (TextUtils.isEmpty(email)) {
            etLoginEmail.setError("Email cannot be empty");
            etLoginEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etLoginPassword.setError("Password cannot be empty");
            etLoginPassword.requestFocus();
        } else {


            pd.setTitle("Loading....");
            pd.show();

//
//            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//
//                    if (task.isComplete()) {
//                        pd.dismiss();
//
//                        Toast.makeText(Login.this, "login successful", Toast.LENGTH_SHORT).show();
//                        checkUserAccess(task.getResult().getCredential().getUid());
//
//                    } else {
//
//                        pd.dismiss();
//                        Toast.makeText(Login.this, "email or password invalid", Toast.LENGTH_SHORT).show();
//
//                    }
//
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//
//                }
//            });


            mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    pd.dismiss();


                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    builder.setMessage("loggedIn Success")
                            .setTitle("")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();


                    checkUserAccess(authResult.getUser().getUid());
                    String uid = authResult.getUser().getUid();


                    loginSession.CreateUserSession(uid);

                    //   checkLogin();


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {


                    pd.dismiss();

                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    builder.setMessage("login Fail")
                            .setTitle("")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

        }
    }

    private void checkUserAccess(String uid) {

        DocumentReference df = firestore.collection("user").document(uid);

        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {


                Log.d(TAG, "onSuccess: ");

                if (documentSnapshot.getString("is User") != null) {

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();


                }


                if (documentSnapshot.getString("is Admin") != null) {

                    startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                    finish();

                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //  Log.d(TAG, "login failour: " + e.getMessage());
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();

        //String uid = null;
        //  checkLogin();
//        if (loginSession.isUserLoggedIn()) {
//
//            String uid = loginSession.getKeyToken();
//
//            DocumentReference df = firestore.collection("user").document(uid);
//
//            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                @Override
//                public void onSuccess(DocumentSnapshot documentSnapshot) {
//
//
//                    Log.d(TAG, "onSuccess: ");
//
//                    if (documentSnapshot.getString("is User") != null) {
//
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        finish();
//
//
//                    }
//
//
//                    if (documentSnapshot.getString("is Admin") != null) {
//
//                        startActivity(new Intent(getApplicationContext(), AdminActivity.class));
//                        finish();
//
//                    }
//
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Log.d(TAG, "login failour: " + e.getMessage());
//                }
//            });
//
//
//
//        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        finish();
    }

    private boolean checkLogin() {

        if (loginSession.isUserLoggedIn()) {

            String uid = loginSession.getKeyToken();

            checkUserAccess(uid);

        }

        return false;
    }


}
