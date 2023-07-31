package l2nsoft.com.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import l2nsoft.com.R;
import l2nsoft.com.UploadPdf;
import l2nsoft.com.ViewPdfActivity;

public class Second extends AppCompatActivity {


    FusedLocationProviderClient fusedLocationProviderClient;
    TextView textCity, textAddress, textScanner;

    private final static int REQUEST_CODE = 100;
    private static final int VIDEO_CAPTURE = 2602;
    private static final int DOCUMENT_REQUEST = 2;
    ProgressDialog progressDialog;

    FirebaseDatabase mDatabase;
    DatabaseReference mRefer;
    FirebaseStorage mstorage;

    ImageView imageView;
    VideoView mvideo;
    Uri videouri;

    Button btnsave, btngenerate;

    ArrayList<Uri> FileList = new ArrayList<Uri>();
    private int upload_count = 0;
    private Uri FileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textAddress = findViewById(R.id.textaddress);
        textCity = findViewById(R.id.text_city);
        imageView = findViewById(R.id.video_id);
        mvideo = findViewById(R.id.v_id);
        btnsave = findViewById(R.id.btn_save);
        mDatabase = FirebaseDatabase.getInstance();
        mRefer = mDatabase.getReference().child("Document");
        mstorage = FirebaseStorage.getInstance();

        progressDialog = new ProgressDialog(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        textAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLatLocation();
            }


        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] options = {"Video", "Exit"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Second.this);

                builder.setTitle("Choose File From ");

                builder.setItems(options, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {
//
                        if (options[item].equals("Video")) {

                            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                            if (intent.resolveActivity(getPackageManager()) != null) {

                                startActivityForResult(intent, VIDEO_CAPTURE);

                            }


                        } else if (options[item].equals("Exit")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();

            }
        });


    }


    private void getLatLocation() {


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    if (location != null) {
                        Geocoder geocoder = new Geocoder(Second.this, Locale.getDefault());
                        List<Address> address = null;
                        try {
                            address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            textAddress.setText(address.get(0).getAddressLine(0));
                            //  textAddress.setText(new StringBuilder().append("Address:").append(address.get(0).getAddressLine(0)).toString());
                            //  textCity.setVisibility(View.VISIBLE);
                            textCity.setText(new StringBuilder().append("city:").append(address.get(0).getLocality()).toString());
                            // textCity.setText(address.get(0). );
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                }
            });


        } else {

            askPermission();

        }


    }

    private void askPermission() {

        ActivityCompat.requestPermissions(Second.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getLatLocation();

            } else {

                Toast.makeText(this, "resquest", Toast.LENGTH_SHORT).show();


            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null && result.getContents() != null) {
            new AlertDialog.Builder(Second.this).setTitle("ScanResult").setMessage(result.getContents()).setPositiveButton("copy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData data = ClipData.newPlainText("result", result.getContents());
                    manager.setPrimaryClip(data);
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        }

        if (requestCode == VIDEO_CAPTURE && resultCode == RESULT_OK) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(ImageWork.this);
//
//            VideoView videoView = new VideoView(ImageWork.this);
//            videoView.setVideoURI(data.getData());
//
//            videoView.start();
//            builder.setView(videoView).show();


            // videouri = data.getData();
            mvideo.setVisibility(View.VISIBLE);
            mvideo.setVideoURI(data.getData());
            mvideo.start();
            videouri = data.getData();
            Toast.makeText(this, videouri.toString(), Toast.LENGTH_SHORT).show();

            savedata(videouri);


        }

//        if (requestCode == DOCUMENT_REQUEST && resultCode == RESULT_OK && data != null) {
//
//
//            Uri videouri = data.getData();
//
//            savedata(videouri);
//
//
//        }


        super.onActivityResult(requestCode, resultCode, data);

    }

    private void savedata(Uri videouri) {


        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addres = textAddress.getText().toString();


                if (TextUtils.isEmpty(addres)) {

                    textAddress.setError("  cannot be empty");
                    textAddress.requestFocus();


                }


                progressDialog.setTitle("Loading......");
                progressDialog.show();

                StorageReference filepath = mstorage.getReference().child("doc").child(videouri.getLastPathSegment());
                filepath.putFile(videouri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> download = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                try {

                                    progressDialog.dismiss();
                                    String t = task.getResult().toString();
                                    DatabaseReference newpost = mRefer.push();
                                    newpost.child("Document").setValue(task.getResult().toString());
                                    newpost.child("address").setValue(addres);
                                    //  newpost.child("pdf").setValue(task.getResult().toString());
                                    startActivity(new Intent(getApplicationContext(), VideoActivity.class));


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


            }
        });


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.scan, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        int id = item.getItemId();
        switch (id) {
            case R.id.item1:

                startActivity(new Intent(getApplicationContext(), ScannerActivity.class));

                break;


            case R.id.item2:

                IntentIntegrator intentIntegrator = new IntentIntegrator(Second.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.setCameraId(0);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setPrompt("Scanning");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setBarcodeImageEnabled(true);
                intentIntegrator.initiateScan();


                break;

            case R.id.item3:

                startActivity(new Intent(getApplicationContext(), ViewPdfActivity.class));

            default:
                return super.onOptionsItemSelected(item);

        }

        return true;
    }


}


