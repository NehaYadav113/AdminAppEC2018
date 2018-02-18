package com.dev.manan.adminappec2018;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BrixxActivity extends AppCompatActivity {
    private Button qrButton, postButton, notificationButton;

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
    }
}
