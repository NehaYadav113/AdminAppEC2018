package com.dev.manan.adminappec2018.Views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dev.manan.adminappec2018.Models.PostModel;
import com.dev.manan.adminappec2018.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OtherClubsActivity extends AppCompatActivity {
    private Button qrButton, postButton, attachPhotoButton;
    private EditText editTextPostTitle;
    private SharedPreferences prefs;
    private IntentIntegrator qrScan;
    private ProgressDialog pd,pdN;
    int color = Color.rgb(59, 89, 152);
    int radius = 35;
    int strokeWidth = 20;
    private DatabaseReference mDatabase;
    private Uri filePath = null;
    private final int PICK_IMAGE_REQUEST = 71;
    private StorageReference mStorageRef;
    private Bitmap bitmap;
    private String userName, token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_clubs);

        com.google.firebase.messaging.FirebaseMessaging.getInstance().subscribeToTopic("Topic");

        mStorageRef = FirebaseStorage.getInstance().getReference();

        prefs = getApplicationContext().getSharedPreferences("com.dev.manan.adminappec2018", Context.MODE_PRIVATE);
        token = prefs.getString("token", "");
        userName = prefs.getString("username", "");

        if(userName.equals("uadmin")){
            userName = "Brixx";
        }
        editTextPostTitle = findViewById(R.id.edit_post_title);
        qrButton = (Button) findViewById(R.id.qrButton);
        postButton = (Button) findViewById(R.id.send_post);
        attachPhotoButton = findViewById(R.id.btn_attach_photo);

        findViewById(R.id.imgbtn_log_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.edit().clear().apply();
                Intent i = new Intent(OtherClubsActivity.this, LoginActivity.class)
                        .putExtra("logout", true);
                startActivity(i);
                finish();
            }
        });
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(color);
        gradientDrawable.setCornerRadius(radius);
        gradientDrawable.setStroke(strokeWidth, color);
        qrButton.setBackground(gradientDrawable);
        postButton.setBackground(gradientDrawable);

// QR Scan Code!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        qrScan = new IntentIntegrator(this);
        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable()) {
                    qrScan.initiateScan();
                }
                else {
                    MDToast.makeText(OtherClubsActivity.this, "Connect to internet!", Toast.LENGTH_SHORT, MDToast.TYPE_WARNING).show();
                }
            }
        });

// Culmyca Times Post Code!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        pd = new ProgressDialog(OtherClubsActivity.this);
        pd.setMessage("Posting post for Culmyca Times...");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);

        MDToast.makeText(this, "WELCOME " + userName.toUpperCase() + " !", Toast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("posts").child(userName);

        attachPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MDToast.makeText(OtherClubsActivity.this, "Select a Poster now!", Toast.LENGTH_SHORT, MDToast.TYPE_INFO).show();
                chooseImage();
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    PostET();
                } else {
                    MDToast.makeText(OtherClubsActivity.this, "Connect to internet!", Toast.LENGTH_SHORT, MDToast.TYPE_WARNING).show();
                }
            }
        });

    }

    private void PostET() {
        Boolean checker = validateCredentialsPost();
        pd.show();
        final String textPostTitle = editTextPostTitle.getText().toString();

        if (checker) {
            final long time = System.currentTimeMillis();
            final String postID = mDatabase.push().getKey();
            final String[] urlPhotoTemp = {"https://firebasestorage.googleapis.com/v0/b/culmyca2018.appspot.com/o/final_image.jpeg?alt=media&token=e7685e47-6762-42d9-bc12-8b8484e0fe38"};

            StorageReference mountainsRef = mStorageRef.child("postImages/" + token + getSaltString() + ".jpg");
            if (filePath != null) {
                final ProgressDialog progressDialog = new ProgressDialog(OtherClubsActivity.this);
                progressDialog.setTitle("Uploading...");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                Bitmap bmp;
                try {
                    bmp = MediaStore.Images.Media.getBitmap(OtherClubsActivity.this.getContentResolver(), filePath);
                    bmp = Bitmap.createScaledBitmap(bmp, 750, (int) ((float) bmp.getHeight() / bmp.getWidth() * 750), true);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();

                    mountainsRef.putBytes(data)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    progressDialog.dismiss();
                                    Uri downloadlink = taskSnapshot.getDownloadUrl();
                                    urlPhotoTemp[0] = downloadlink.toString();

                                    PostModel pm = new PostModel(textPostTitle, urlPhotoTemp[0], userName, time);

                                    mDatabase.child(postID).setValue(pm).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            pd.dismiss();
                                            MDToast.makeText(getApplicationContext(), "Post Successfully Posted!", Toast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
                                            editTextPostTitle.setText("");
                                            attachPhotoButton.setVisibility(View.VISIBLE);
                                        }
                                    });
                                    MDToast.makeText(OtherClubsActivity.this, "Uploaded!", Toast.LENGTH_SHORT, MDToast.TYPE_INFO).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    MDToast.makeText(OtherClubsActivity.this, "Failed!", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                            .getTotalByteCount());
                                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                                }
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                pd.dismiss();
                MDToast.makeText(OtherClubsActivity.this, "Upload a Poster First!", Toast.LENGTH_SHORT, MDToast.TYPE_INFO).show();
            }
        } else {
            pd.dismiss();
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
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
                MDToast.makeText(this, "Cancelled!", Toast.LENGTH_LONG, MDToast.TYPE_INFO).show();
            } else {
                arrivalStatus(result.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                    && data != null && data.getData() != null) {
                filePath = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    MDToast.makeText(this, "Image selected successfully.", Toast.LENGTH_LONG, MDToast.TYPE_INFO).show();
                    attachPhotoButton.setVisibility(View.GONE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        OtherClubsActivity.this.finish();
        System.exit(0);
    }
    private void arrivalStatus(final String content) {
        String url = "https://elementsculmyca2018.herokuapp.com/api/v1/events/arrived";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("prerna",response);
                    JSONObject obj = new JSONObject(response);
                    int status = obj.getInt("success");
                    String msg = obj.getString("msg");
                    Log.d("def" ,Integer.toString(status));
                    if(status == 1){
                        MDToast.makeText(OtherClubsActivity.this , "Successfully arrived" , Toast.LENGTH_LONG ,MDToast.TYPE_SUCCESS).show();
                    }
                    if(status == 0 && msg.equals("Already Arrived!")){
                        MDToast.makeText(OtherClubsActivity.this,"Already Arrived!",Toast.LENGTH_LONG,MDToast.TYPE_INFO).show();
                    }
                    if(status == 0 && msg.equals("Cross Login not Authorized!")){
                        MDToast.makeText(OtherClubsActivity.this,"Cross Login not Authorized!",Toast.LENGTH_LONG,MDToast.TYPE_INFO).show();
                    }
                    if(status == 0 && msg.equals("Payment not Done!")){
                        MDToast.makeText(OtherClubsActivity.this,"Payment not Done!",Toast.LENGTH_LONG,MDToast.TYPE_INFO).show();
                    }


                }
                catch (Exception e) {
                    // If an error occurs, this prints the error to the log

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("admintoken", token);
                map.put("qrcode", content);
                return map;
            }
        };
        queue.add(request);
    }
}

