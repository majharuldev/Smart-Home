package l2nsoft.com.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import l2nsoft.com.R;
import l2nsoft.com.RoomAdapter;
import l2nsoft.com.RoomResponse;
import l2nsoft.com.activity.ImageWork;
import l2nsoft.com.activity.Login;
import l2nsoft.com.session.LoginSession;

public class WorkListUpdate extends AppCompatActivity {
    FirebaseDatabase mDatabase;
    DatabaseReference mRefer;
    FirebaseStorage mstorage;

    RecyclerView recyclerView;
    List<RoomResponse> responses;
    RoomAdapter adapter;
    String roomid, roomname;

    FloatingActionButton addfab;

    private Toolbar toolbar;

    private LoginSession loginSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_list_update);


        loginSession = new LoginSession(this);

        addfab = findViewById(R.id.addfab);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {


            roomid = bundle.getString("roomId");
            roomname = bundle.getString("roomName");


        } else {

            //  Toast.makeText(this, "empty", Toast.LENGTH_SHORT).show();


        }


        addfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ImageWork.class);

                intent.putExtra("roomname", roomname);
                startActivity(intent);


            }
        });


        mRefer = FirebaseDatabase.getInstance().getReference().child("testing").child(roomname);

        recyclerView = findViewById(R.id.rcler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        responses = new ArrayList<RoomResponse>();
        FirebaseRecyclerOptions<RoomResponse> options =
                new FirebaseRecyclerOptions.Builder<RoomResponse>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("testing").child(roomname), RoomResponse.class)
                        .build();
        adapter = new RoomAdapter(options);

        recyclerView.setAdapter(adapter);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        int id = item.getItemId();
        switch (id) {
            case R.id.item1:


                loginSession.logoutUser();
                startActivity(new Intent(getApplicationContext(), Login.class));
                //  Toast.makeText(getApplicationContext(),"Item 1 Selected",Toast.LENGTH_LONG).show();
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}