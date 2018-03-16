package com.dev.manan.adminappec2018;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by Himanshu on 2/18/18.
 */

public class AddPostFragment extends DialogFragment {
    private DatabaseReference mDatabase,mdatabase1;
    private EditText mTitleField;
    private TextView post;
    SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_post_fragment, container, false);
        getDialog().setTitle("Write Post");



        mTitleField = (EditText) rootView.findViewById(R.id.field_body);
        post = (TextView) rootView.findViewById(R.id.sendpost);


        final String caption=mTitleField.getText().toString();
        prefs=getActivity().getSharedPreferences("com.dev.manan.adminappec2018", Context.MODE_PRIVATE);


        final String token = prefs.getString("token","");

        if(token=="63617168") {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("posts").child("Brixx");
        }
        else {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("posts").child("Manan");
        }


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String,String> m=new HashMap<>();
                m.put("approval","false");
                m.put("caption",caption);
                m.put("image","hi");
                m.put("likes","0");
                m.put("time", ServerValue.TIMESTAMP.toString());

                String postid=mDatabase.push().getKey();

                mDatabase.child(postid).setValue(m).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getActivity(),"yeah",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        return rootView;
    }
}