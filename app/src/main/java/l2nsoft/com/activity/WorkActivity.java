package l2nsoft.com.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import l2nsoft.com.R;

public class WorkActivity extends AppCompatActivity {

    private TextView time;
    Button button, stop;

    TextInputEditText etDes;
    FloatingActionButton fab;

    private int seconds = 0;

    // Is the stopwatch running?
    private boolean running;

    private boolean wasRunning;
    TextView timeView = null;
    ProgressDialog progressDialog;

    String roomId, roomName;
    TextView textRoom;
    FirebaseFirestore db;


    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String date;
    TextView date_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        db = FirebaseFirestore.getInstance();
        //  time = findViewById(R.id.text_time);
        button = findViewById(R.id.bt_start);
        etDes = findViewById(R.id.et_des);
        fab = findViewById(R.id.fab);
        stop = findViewById(R.id.stop);
        textRoom = findViewById(R.id.text_Room);

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyy");

        date_time = findViewById(R.id.date_text);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

           roomId = bundle.getString("roomId");
            roomName = bundle.getString("roomName");
            textRoom.setText(roomName);


        } else {

            Toast.makeText(this, "Data empty", Toast.LENGTH_SHORT).show();

        }


        progressDialog = new ProgressDialog(this);

        timeView = (TextView) findViewById(
                R.id.text_time);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickStart(view);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickStop(view);
                date_time.setVisibility(View.VISIBLE);
                date = simpleDateFormat.format(new Date());
                date_time.setText(date);

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String roomName = textRoom.getText().toString();
                String des = etDes.getText().toString();
                String time = timeView.getText().toString();
                String currentTime = date_time.getText().toString();

                if (TextUtils.isEmpty(des)) {
                    etDes.setError("Area  cannot be empty");
                    etDes.requestFocus();


                } else {

                    progressDialog.setTitle("Loading...");
                    progressDialog.show();
//                    String id = UUID.randomUUID().toString();
                    Map<String, Object> doc = new HashMap<>();

                    doc.put("room", roomName);
                    doc.put("des", des);
                    doc.put("time", time);
                    doc.put("date", currentTime);

                    db.collection("WorkReport").document().set(doc)


                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    progressDialog.dismiss();

                                    Toast.makeText(WorkActivity.this, "Upload", Toast.LENGTH_SHORT).show();

                                      startActivity(new Intent(getApplicationContext(), WorkList.class));


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(WorkActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                }


            }
        });


        if (savedInstanceState != null) {

            // Get the previous state of the stopwatch
            // if the activity has been
            // destroyed and recreated.
            seconds
                    = savedInstanceState
                    .getInt("seconds");
            running
                    = savedInstanceState
                    .getBoolean("running");
            wasRunning
                    = savedInstanceState
                    .getBoolean("wasRunning");
        }

        runTimer();
    }


    @Override
    public void onSaveInstanceState(
            Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState
                .putInt("seconds", seconds);
        savedInstanceState
                .putBoolean("running", running);
        savedInstanceState
                .putBoolean("wasRunning", wasRunning);
    }


    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    public void onClickStart(View view) {
        button.setVisibility(View.INVISIBLE);
        stop.setVisibility(View.VISIBLE);
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
        stop.setVisibility(View.INVISIBLE);
        etDes.setVisibility(View.VISIBLE);

    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    private void runTimer() {


        final Handler handler
                = new Handler();


        handler.post(new Runnable() {
            @Override

            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time
                        = String
                        .format(Locale.getDefault(),
                                "%d:%02d:%02d", hours,
                                minutes, secs);


                timeView.setText(time);


                if (running) {
                    seconds++;
                }

                handler.postDelayed(this, 1000);
            }
        });
    }
}



