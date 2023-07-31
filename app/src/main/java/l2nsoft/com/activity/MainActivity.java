package l2nsoft.com.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import l2nsoft.com.R;
import l2nsoft.com.activity.GardenActivity;
import l2nsoft.com.activity.Roomlist;

public class MainActivity extends AppCompatActivity {

    String[] country = {"select", "Office Entity", "Garden Entity"};
    private TextView textView;

    String[] items = {"Office Entity", "Garden Entity"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;

    private Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spin = findViewById(R.id.spin);
        textView = findViewById(R.id.marqueeText);


        textView.setSelected(true);
//        spin.setOnItemSelectedListener(this);
//
//        //Creating the ArrayAdapter instance having the country list
//        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, country);
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        //Setting the ArrayAdapter data on the Spinner
//        spin.setAdapter(aa);


        autoCompleteTxt = findViewById(R.id.auto_complete_txt);

        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, items);
        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
//                Toast.makeText(getApplicationContext(), "Item: " + item, Toast.LENGTH_SHORT).show();

                if (id == 0) {

                    startActivity(new Intent(getApplicationContext(), Roomlist.class));

                } else {
                    startActivity(new Intent(getApplicationContext(), GardenActivity.class));

                }


            }
        });


    }

    //Performing action onItemSelected and onNothing selected
//    @Override
//    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
//        // Toast.makeText(getApplicationContext(),country[position] , Toast.LENGTH_LONG).show();
//
//        switch (position) {
//
//            case 1:
//                startActivity(new Intent(getApplicationContext(), Roomlist.class));
//                break;
//            case 2:
//                startActivity(new Intent(getApplicationContext(), GardenActivity.class));
//                break;
//
//        }
//
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> arg0) {
//
//    }

}
