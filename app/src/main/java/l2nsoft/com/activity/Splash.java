package l2nsoft.com.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import l2nsoft.com.R;
import l2nsoft.com.session.LoginSession;

public class Splash extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT = 2000;

    LoginSession loginSession;
    FirebaseFirestore firestore;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.purple_500));
        }

        firestore = FirebaseFirestore.getInstance();


        loginSession = new LoginSession(this);


    }


    @Override
    protected void onStart() {
        super.onStart();

        if (loginSession.isUserLoggedIn()) {

            String uid = loginSession.getKeyToken();

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
                   // Log.d(TAG, "login failour: " + e.getMessage());
                }
            });


        } else {


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(Splash.this,
                            Login.class);
                    startActivity(i);
                    finish();


                }


            }, SPLASH_SCREEN_TIME_OUT);
        }

    }




}