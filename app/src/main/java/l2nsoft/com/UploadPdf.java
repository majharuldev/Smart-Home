package l2nsoft.com;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadPdf extends AppCompatActivity {


    private Button btnUpload;
    private EditText etFile;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);


        btnUpload = findViewById(R.id.btnUpload);
        etFile = findViewById(R.id.et_file);


        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("pdfFile");


        // btnUpload.setEnabled(false);


        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectPdf();
            }
        });


    }

    private void SelectPdf() {


        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "PDF FILE SELECT"), 12);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (requestCode == 12 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            // btnUpload.setEnabled(true);
            UploadFile(data.getData());
            //  etFile.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/") + 1));


        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void UploadFile(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("loading...");
        progressDialog.show();

        StorageReference reference = storageReference.child(new StringBuilder().append("pdfFile").append(System.currentTimeMillis()).append(".pdf").toString());


        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();

                while (!uriTask.isComplete()) ;
                Uri uri = uriTask.getResult();

                PdfClass pdfClass = new PdfClass(etFile.getText().toString(), uri.toString());

                databaseReference.child(databaseReference.push().getKey()).setValue(pdfClass);
                Toast.makeText(UploadPdf.this, "File upload", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                startActivity(new Intent(getApplicationContext(), ViewPdfActivity.class));
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();

                progressDialog.setMessage(new StringBuilder().append("File Upload").append((int) progress).append("%").toString());


            }


        });


    }


    public void retrive(View view) {

        //  startActivity(new Intent(getApplicationContext(), ViewPdfActivity.class));


    }


}
