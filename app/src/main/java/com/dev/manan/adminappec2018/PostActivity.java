package com.dev.manan.adminappec2018;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {

    ValueEventListener getPosts;
    DatabaseReference postReference;
    List<postsModel> allposts;
    RecyclerView recyclerView;
    ProgressDialog progressBar;
    RecyclerView.LayoutManager mLayoutManager;
    private CTAdapter mAdapter;
    SwipeRefreshLayout s;
    SharedPreferences prefs;
    String token;


    public static AlertDialog.Builder builder;
    public static AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);




        allposts=new ArrayList<postsModel>();
        recyclerView=findViewById(R.id.ctc_recycler_view);
        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Loading!");
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.show();
        postReference = FirebaseDatabase.getInstance().getReference("posts");


        String club;
        prefs=getApplicationContext().getSharedPreferences("com.dev.manan.adminappec2018", Context.MODE_PRIVATE);
        token = prefs.getString("token","");

        if(token=="63617169")
            club="Manan";


        s=findViewById(R.id.swipe_refresh_layout);

        s.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
            }
        });


        //postReference = FirebaseDatabase.getInstance().getReference("posts");
        postReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allposts=new ArrayList<postsModel>();
                String clubnm="";
                prefs=getApplicationContext().getSharedPreferences("com.dev.manan.adminappec2018", Context.MODE_PRIVATE);
                token = prefs.getString("token","");

                if(token.equals("63617169"))
                    clubnm="Manan";
                else
                    clubnm="Manan";

                for(DataSnapshot club:dataSnapshot.getChildren()){
                    String clubName=club.getKey();

                    System.out.println(clubName + clubnm);
                    if(clubName.equals(clubnm)) {
                        for (DataSnapshot posts : club.getChildren()) {
                            Log.d("posts", posts.toString());

                            postsModel post = posts.getValue(postsModel.class);
                            post.clubName = clubName;
                            post.postid = posts.getKey();
                            ArrayList<Comment> allcomments = new ArrayList<Comment>();
                            for (DataSnapshot comments : posts.child("comments").getChildren()) {
                                Comment comment = comments.getValue(Comment.class);
                                allcomments.add(comment);
                            }

                            post.comments = allcomments;

                            ArrayList<likesModel> alllikes = new ArrayList<likesModel>();
                            for (DataSnapshot mlikes : posts.child("likefids").getChildren()) {
                                likesModel l = mlikes.getValue(likesModel.class);
                                alllikes.add(l);
                            }

                            post.likefids = alllikes;
                            allposts.add(post);
                        }
                    }
                }
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new CTAdapter(getApplicationContext(), allposts);
                recyclerView.setAdapter(mAdapter);
                progressBar.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        mLayoutManager.smoothScrollToPosition(recyclerView,null,0);
        if(!recyclerView.canScrollVertically(-1)){
            super.onBackPressed();
        }
    }
    public void reload(){

        postReference = FirebaseDatabase.getInstance().getReference("posts");
        postReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allposts=new ArrayList<postsModel>();
                String clubnm="";
                prefs=getApplicationContext().getSharedPreferences("com.dev.manan.adminappec2018", Context.MODE_PRIVATE);
                token = prefs.getString("token","");

                if(token=="63617169")
                    clubnm="Manan";

                for(DataSnapshot club:dataSnapshot.getChildren()){
                    String clubName=club.getKey();

                    if(clubName==clubnm) {
                        for (DataSnapshot posts : club.getChildren()) {
                            Log.d("posts", posts.toString());

                            postsModel post = posts.getValue(postsModel.class);
                            post.clubName = clubName;
                            post.postid = posts.getKey();
                            ArrayList<Comment> allcomments = new ArrayList<Comment>();
                            for (DataSnapshot comments : posts.child("comments").getChildren()) {
                                Comment comment = comments.getValue(Comment.class);
                                allcomments.add(comment);
                            }

                            post.comments = allcomments;

                            ArrayList<likesModel> alllikes = new ArrayList<likesModel>();
                            for (DataSnapshot mlikes : posts.child("likefids").getChildren()) {
                                likesModel l = mlikes.getValue(likesModel.class);
                                alllikes.add(l);
                            }

                            post.likefids = alllikes;
                            allposts.add(post);
                        }
                    }
                }
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new CTAdapter(getApplicationContext(), allposts);
                recyclerView.setAdapter(mAdapter);
                progressBar.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();




    }
}
