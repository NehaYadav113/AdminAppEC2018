package com.dev.manan.adminappec2018;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.manan.adminappec2018.Models.NotificationModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class BrixxActivity extends AppCompatActivity {
    private Button qrButton, postButton, notificationButton, viewposts;
    private EditText editText;
    SharedPreferences prefs;
    private IntentIntegrator qrScan;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brixx);

        com.google.firebase.messaging.FirebaseMessaging.getInstance().subscribeToTopic("Topic");

        prefs = getApplicationContext().getSharedPreferences("com.dev.manan.adminappec2018", Context.MODE_PRIVATE);


        final String token = prefs.getString("token", "");
        System.out.println(token);


        qrButton = (Button) findViewById(R.id.qrButton);
        postButton = (Button) findViewById(R.id.sendPost);
        notificationButton = (Button) findViewById(R.id.sendNotif);
        editText = (EditText) findViewById(R.id.edit);
        viewposts = (Button) findViewById(R.id.view_posts);

        qrScan = new IntentIntegrator(this);

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
                qrScan.initiateScan();
            }
        });

        pd = new ProgressDialog(BrixxActivity.this);
        pd.setMessage("Sending Notification to Humans...");
        pd.setCancelable(false);

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                String text = editText.getText().toString();
                if (!text.equals("")) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Notification");
                    String notificationid = databaseReference.push().getKey();

                    long time = System.currentTimeMillis();

                    NotificationModel notificationModel = new NotificationModel(text, time);

                    databaseReference.child(notificationid).setValue(notificationModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), "Notification Sent Successfully!", Toast.LENGTH_SHORT).show();
                            editText.setText("");
                        }
                    });
                } else {
                    pd.dismiss();
                    Toast.makeText(BrixxActivity.this, "Notification Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewposts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), PostActivity.class);
                startActivity(i);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
