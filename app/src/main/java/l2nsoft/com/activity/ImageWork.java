package l2nsoft.com.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import l2nsoft.com.R;

public class ImageWork extends AppCompatActivity {

    private int mYear, mMonth, mDay, mHour, mMinute;

    FirebaseDatabase mDatabase;
    DatabaseReference mRefer;
    FirebaseStorage mstorage;
    TextView stTime, endTime;

    TextInputEditText title, desc;
    private static final int Gallery_code = 1;
    private static final int VIDEO_CAPTURE = 2602;

    private ImageView imageView, video;
    Uri imageurl;

    private MaterialButton btnsave;
    String roomId, roomName;
    ProgressDialog progressDialog;
    private static final int DOCUMENT_REQUEST = 2;
    private static final int CAMERA_REQUEST = 0;
    private Uri fileUri;

    VideoView mvideoView;


    private Uri videouri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_work);

        stTime = findViewById(R.id.st_time);
        endTime = findViewById(R.id.st_end);
        title = findViewById(R.id.et_title);
        desc = findViewById(R.id.et_desc);
        imageView = findViewById(R.id.img_id);

//        video = findViewById(R.id.video_id);
        btnsave = findViewById(R.id.btn_save);
        progressDialog = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance();
        Bundle bundle = getIntent().getExtras();

          mvideoView = findViewById(R.id.v_id);


        if (bundle != null) {


            // roomId = bundle.getString("");
            roomName = bundle.getString("roomname");


        }


        mRefer = mDatabase.getReference().child("testing").child(roomName);
        mstorage = FirebaseStorage.getInstance();


        stTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(ImageWork.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                stTime.setText(new StringBuilder().append(hourOfDay).append(":").append(minute).toString());
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                timepick();


            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final CharSequence[] options = {"Camera", "Gallery", "Exit"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ImageWork.this);

                builder.setTitle("Choose Image From ");

                builder.setItems(options, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (options[item].equals("Camera")) {
                            Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePicture, CAMERA_REQUEST);

                        } else if (options[item].equals("Gallery")) {
                            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                            photoPickerIntent.setType("image/*");
                            startActivityForResult(photoPickerIntent, Gallery_code);

                        } else if (options[item].equals("Exit")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();


            }
        });

//        video.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                final CharSequence[] options = {"video", "file", "Exit"};
//                AlertDialog.Builder builder = new AlertDialog.Builder(ImageWork.this);
//
//                builder.setTitle("Choose Image From ");
//
//                builder.setItems(options, new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int item) {
//
//                        if (options[item].equals("video")) {
//
//                            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//                            if (intent.resolveActivity(getPackageManager()) != null) {
//
//                                startActivityForResult(intent, VIDEO_CAPTURE);
//
//                            }
//
//
//                        } else if (options[item].equals("file")) {
//                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                            intent.addCategory(Intent.CATEGORY_OPENABLE);
//                            intent.setType("*/*");
//                            startActivityForResult(Intent.createChooser(intent, "ChooseFile"), DOCUMENT_REQUEST);
//
//                        } else if (options[item].equals("Exit")) {
//                            dialog.dismiss();
//                        }
//                    }
//                });
//                builder.show();
//
//
//            }
//        });


    }

    private void timepick() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(ImageWork.this,
                new TimePickerDialog.OnTimeSetListener() {


                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        endTime.setText(new StringBuilder().append(hourOfDay).append(" :").append(minute).toString());
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {


            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), bitmap, "fil", null);
            imageurl = Uri.parse(path);
            imageView.setImageURI(imageurl);

            buttonclick(imageurl);


            //   buttonclick(imageurl);

//        *    imageurl=data.getData();
//                imageView.setImageURI(imageurl);
//          //  imageView.setImageBitmap(selectedImage);
//            buttonclick(imageurl);


        } else if (requestCode == Gallery_code && resultCode == RESULT_OK) {


            imageurl = data.getData();
            imageView.setImageURI(imageurl);
            buttonclick(imageurl);


        } else if (requestCode == VIDEO_CAPTURE && resultCode == RESULT_OK) {


//            AlertDialog.Builder builder = new AlertDialog.Builder(ImageWork.this);
//
//            VideoView videoView = new VideoView(ImageWork.this);
//            videoView.setVideoURI(data.getData());
//
//            videoView.start();
//            builder.setView(videoView).show();


            // videouri = data.getData();


//            mvideoView.setVisibility(View.VISIBLE);
//
//            mvideoView.setVideoURI(data.getData());
//            mvideoView.start();
//
//            videouri=data.getData();
//
//            Toast.makeText(this, videouri.toString(), Toast.LENGTH_SHORT).show();


        }
        super.onActivityResult(requestCode, resultCode, data);


    }

    private void buttonclick(Uri imageurl) {


        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mtitle = title.getText().toString();
                String dese = desc.getText().toString();
                String startTime = stTime.getText().toString();
                String endtime = endTime.getText().toString();


                if (TextUtils.isEmpty(mtitle)) {
                    title.setError("title  cannot be empty");
                    title.requestFocus();


                }
                if (TextUtils.isEmpty(dese)) {
                    desc.setError("can not be ");
                    desc.requestFocus();


                } else {
                    progressDialog.setTitle("Loading");
                    progressDialog.show();


                    StorageReference filepath = mstorage.getReference().child("file").child(imageurl.getLastPathSegment());

                    filepath.putFile(imageurl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> downloadurl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {

                                    DatabaseReference newpost = mRefer.push();
                                    newpost.child("image").setValue(task.getResult().toString());

                                    newpost.child("title").setValue(mtitle);
                                    newpost.child("Details").setValue(dese);
                                    newpost.child("starttime").setValue(startTime);
                                    newpost.child("endtime").setValue(endtime);

                                    progressDialog.dismiss();


                                    AlertDialog.Builder builder = new AlertDialog.Builder(ImageWork.this);
                                    builder.setMessage("loggedIn Success")
                                            .setTitle("")
                                            .setPositiveButton(android.R.string.ok, null);
                                    AlertDialog dialog = builder.create();
                                    dialog.show();


                                    startActivity(new Intent(getApplicationContext(), WorkListUpdate.class));
//                                    Intent intent = new Intent(getApplicationContext(), WorkListUpdate.class);
//                                    intent.putExtra("roomid", roomId);
//                                    intent.putExtra("room", roomName);
                                    //  startActivity(intent);
                                    title.setText("");
                                    stTime.setText("");
                                    imageView.setImageURI(null);
                                    endTime.setText("");
                                    desc.setText("");


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {


                                    progressDialog.dismiss();
                                    Toast.makeText(ImageWork.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });


                        }
                    });


                }


            }
        });


    }


}