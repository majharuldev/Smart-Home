package l2nsoft.com.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import l2nsoft.com.R;

public class ScannerActivity extends AppCompatActivity {

    ImageView qrimg;

    EditText etQr;
    Button btngenerate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);


        qrimg = findViewById(R.id.qr_img);
        btngenerate = findViewById(R.id.btngenerator);
        etQr = findViewById(R.id.qr_text);


        btngenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = etQr.getText().toString();

                MultiFormatWriter writer = new MultiFormatWriter();

                try {
                    BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, 400, 200);
                    BarcodeEncoder encoder = new BarcodeEncoder();

                    Bitmap bitmap = encoder.createBitmap(matrix);

                    qrimg.setVisibility(View.VISIBLE);
                    qrimg.setImageBitmap(bitmap);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }
}