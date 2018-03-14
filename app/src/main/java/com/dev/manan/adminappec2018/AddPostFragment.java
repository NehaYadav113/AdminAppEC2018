package com.dev.manan.adminappec2018;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.manan.adminappec2018.Models.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by Himanshu on 2/18/18.
 */

public class AddPostFragment extends DialogFragment {
    private DatabaseReference mDatabase;
    private EditText mTitleField;
    private TextView post;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_post_fragment, container, false);
        getDialog().setTitle("Write Post");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mTitleField = (EditText) rootView.findViewById(R.id.field_body);
        post = (TextView) rootView.findViewById(R.id.sendpost);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });


        return rootView;
    }

    private void submitPost() {
        final String title = mTitleField.getText().toString();

        // Title is required
        if (TextUtils.isEmpty(title)) {
            mTitleField.setError("Required");
            return;
        }

        Toast.makeText(getActivity(), "Posting...", Toast.LENGTH_SHORT).show();
        final String token = FirebaseInstanceId.getInstance().getToken();
        final String body = mTitleField.getText().toString();

        mDatabase.child("users").child(token).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        writeNewPost(token, body);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        //    setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });

    }

    private void writeNewPost(String token, String post) {
        String key = mDatabase.child("posts").push().getKey();

        Post post1 = new Post(token, "brixx[to be changed]", post);
        Map<String, Object> postValues = post1.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + token + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);

    }
}
