package com.dev.manan.adminappec2018;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;

import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class BrixxActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private Button qrButton, postButton, notificationButton;
    private ZXingScannerView zXingScannerView;
    private EditText editText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brixx);

        com.google.firebase.messaging.FirebaseMessaging.getInstance().subscribeToTopic("Topic");

        qrButton = (Button) findViewById(R.id.qrButton);
        postButton = (Button) findViewById(R.id.sendPost);
        notificationButton = (Button) findViewById(R.id.sendNotif);
        editText=(EditText)findViewById(R.id.edit);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                AddPostFragment dialogFragment = new AddPostFragment();
                dialogFragment.show(fm, "Add Post Fragment");
            }
        });
        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scan(view);
            }
        });

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Notification");

                String questionid = databaseReference.push().getKey();
                HashMap<String, String> usermap1 = new HashMap<>();
                String text=editText.getText().toString();

                usermap1.put("text", text);

                databaseReference.child(questionid).setValue(usermap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"yoyo",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });




    }
    public void scan(View view){
        /*zXingScannerView=new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();*/

        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        startActivityForResult(intent, 0);
    }
    @Override
    protected void onPause() {
        super.onPause();
      //  zXingScannerView.stopCamera();
    }


    @Override
    public void handleResult(Result result) {
        Toast.makeText(getApplicationContext(),result.getText(),Toast.LENGTH_SHORT).show();
        //zXingScannerView.resumeCameraPreview(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                Log.d("yi",contents);
                Log.d("yi",format);
                // Handle successful scan
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }
    }
}
