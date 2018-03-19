package com.dev.manan.adminappec2018;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.manan.adminappec2018.Models.NotificationModel;
import com.dev.manan.adminappec2018.Models.PostModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class BrixxActivity extends AppCompatActivity {
    private Button qrButton, postButton, notificationButton, attachPhotoButton;
    private EditText editText, editTextTitle, editTextPostTitle;
    SharedPreferences prefs;
    private IntentIntegrator qrScan;
    private ProgressDialog pd;
    int color = Color.rgb(59, 89, 152);
    int radius = 35; //radius will be 5px
    int strokeWidth = 20;
    private DatabaseReference mDatabase;
    private String clubName = "None";
    private String CLUB_NAME = "clubname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brixx);

        com.google.firebase.messaging.FirebaseMessaging.getInstance().subscribeToTopic("Topic");

        prefs = getApplicationContext().getSharedPreferences("com.dev.manan.adminappec2018", Context.MODE_PRIVATE);
        final String token = prefs.getString("token", "");
        System.out.println(token);

        editTextPostTitle = findViewById(R.id.edit_post_title);
        editTextTitle = findViewById(R.id.edit_heading);
        qrButton = (Button) findViewById(R.id.qrButton);
        postButton = (Button) findViewById(R.id.send_post);
        attachPhotoButton = findViewById(R.id.btn_attach_photo);
        notificationButton = (Button) findViewById(R.id.sendNotif);
        editText = (EditText) findViewById(R.id.edit);

        findViewById(R.id.imgbtn_log_out).setOnClickListener(new View.OnClickListener() {
            //TODO
            @Override
            public void onClick(View v) {
                Toast.makeText(BrixxActivity.this, "Logout here!", Toast.LENGTH_SHORT).show();
            }
        });


        qrScan = new IntentIntegrator(this);
        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.initiateScan();
            }
        });

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(color);
        gradientDrawable.setCornerRadius(radius);
        gradientDrawable.setStroke(strokeWidth, color);
        qrButton.setBackground(gradientDrawable);
        notificationButton.setBackground(gradientDrawable);
        postButton.setBackground(gradientDrawable);

        pd = new ProgressDialog(BrixxActivity.this);
        pd.setMessage("Sending Notification to Humans...");
        pd.setCancelable(false);

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checker = validateCredentials();
                pd.show();
                String text = editText.getText().toString();
                String textHeading = editTextTitle.getText().toString();

                if (checker) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Notification");
                    String notificationid = databaseReference.push().getKey();

                    long time = System.currentTimeMillis();

                    NotificationModel notificationModel = new NotificationModel(text, textHeading, time);

                    databaseReference.child(notificationid).setValue(notificationModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), "Notification Sent Successfully!", Toast.LENGTH_SHORT).show();
                            editText.setText("");
                            editTextTitle.setText("");
                        }
                    });
                } else {
                    pd.dismiss();
                }
            }
        });

        pd = new ProgressDialog(BrixxActivity.this);
        pd.setMessage("Posting post for Culmyca Times...");
        pd.setCancelable(false);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            clubName = bd.getString(CLUB_NAME);
        }
        Toast.makeText(this, "WELCOME "+ clubName.toUpperCase(), Toast.LENGTH_SHORT).show();

        prefs = getSharedPreferences("com.dev.manan.adminappec2018", Context.MODE_PRIVATE);
        final String token1 = prefs.getString("token", "");

        if (token1.equals("63617168")) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("posts").child("Brixx");
        } else {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("posts").child("Manan");
        }

        //TODO
        attachPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BrixxActivity.this, "Attach Poster Code!", Toast.LENGTH_SHORT).show();
            }
        });
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean checker = validateCredentialsPost();
                pd.show();
                String textPostTitle = editTextPostTitle.getText().toString();

                if (checker) {
                    long time = System.currentTimeMillis();
                    String postID = mDatabase.push().getKey();
                    String urlPhotoTemp = "https://firebasestorage.googleapis.com/v0/b/culmyca2018.appspot.com/o/final_image.jpeg?alt=media&token=e7685e47-6762-42d9-bc12-8b8484e0fe38";

                    PostModel pm = new PostModel(textPostTitle, urlPhotoTemp, clubName, time);

                    mDatabase.child(postID).setValue(pm).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), "Post Successfully Posted!", Toast.LENGTH_SHORT).show();
                            editTextPostTitle.setText("");
                        }
                    });
                } else {
                    pd.dismiss();
                }
            }
        });

    }

    private Boolean validateCredentials() {
        if (editText.getText().toString().equals("")) {
            editText.setError("Enter Notification Description!");
            return false;
        }
        if (editTextTitle.getText().toString().equals("")) {
            editTextTitle.setError("Enter Notification Title!");
            return false;
        }
        return true;
    }

    private Boolean validateCredentialsPost() {
        if (editTextPostTitle.getText().toString().equals("")) {
            editTextPostTitle.setError("Enter a title for post!");
            return false;
        }
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
