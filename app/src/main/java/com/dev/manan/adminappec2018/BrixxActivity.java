package com.dev.manan.adminappec2018;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class BrixxActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private Button qrButton, postButton, notificationButton;
    private ZXingScannerView zXingScannerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brixx);

        qrButton = (Button) findViewById(R.id.qrButton);
        postButton = (Button) findViewById(R.id.sendPost);
        notificationButton = (Button) findViewById(R.id.sendNotif);

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




    }
    public void scan(View view){
        zXingScannerView=new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();


    }
    @Override
    protected void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }


    @Override
    public void handleResult(Result result) {
        Toast.makeText(getApplicationContext(),result.getText(),Toast.LENGTH_SHORT).show();
        zXingScannerView.resumeCameraPreview(this);
    }
}
