package l2nsoft.com.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import l2nsoft.com.R;
import l2nsoft.com.model.VideoListresponse;
import l2nsoft.com.adapter.VideoAdapter;

public class VideoActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    FirebaseDatabase mDatabase;
    DatabaseReference mRefer;
    FirebaseStorage mstorage;

    List<VideoListresponse> list;
    VideoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);


        recyclerView = findViewById(R.id.video_recycler);
        floatingActionButton = findViewById(R.id.btnfab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), Second.class));


            }
        });


        mRefer = FirebaseDatabase.getInstance().getReference().child("Document");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        list = new ArrayList<VideoListresponse>();
        FirebaseRecyclerOptions<VideoListresponse> options =
                new FirebaseRecyclerOptions.Builder<VideoListresponse>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Document"), VideoListresponse.class)
                        .build();
        adapter = new VideoAdapter(options);

        recyclerView.setAdapter(adapter);


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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

     finish();
    }
}