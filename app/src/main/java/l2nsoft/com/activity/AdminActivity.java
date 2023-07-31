package l2nsoft.com.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import l2nsoft.com.R;
import l2nsoft.com.session.LoginSession;

public class AdminActivity extends AppCompatActivity {

    private TextView mtext;

    LinearLayout layoutRoomList, layoutAreaList, layoutLogout, layoutWorkReport, layoutRoom;

    private LoginSession loginSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        mtext = findViewById(R.id.marqueeText);
        mtext.setSelected(true);


        loginSession = new LoginSession(this);


        layoutRoomList = findViewById(R.id.layout_room_list);
        layoutAreaList = findViewById(R.id.layout_list);
        layoutLogout = findViewById(R.id.layout_logout);
        layoutWorkReport = findViewById(R.id.work_report);

        onclickList();


    }

    private void onclickList() {


        layoutRoomList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(getApplicationContext(), OfficeActivity.class));


            }
        });
        layoutAreaList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), AddArea.class));

            }
        });
        layoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                loginSession.logoutUser();
                startActivity(new Intent(getApplicationContext(), Login.class));


            }
        });
        layoutWorkReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), WorkList.class));

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();

    }
}