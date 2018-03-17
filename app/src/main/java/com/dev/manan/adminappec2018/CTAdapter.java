package com.dev.manan.adminappec2018;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;



public class CTAdapter extends RecyclerView.Adapter<CTAdapter.MyViewHolder>{

    private List<postsModel> postsList;
    private Context context;
    private postsModel topic;
    private String token="";
    SharedPreferences prefs;
    int liked=0;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView clubName,caption,likes,comments,postTime,approved;
        public ImageView clubIcon,like, comment,share;
        public Button acceptbutton,rejectbutton;


        public MyViewHolder(View view) {
            super(view);
            clubName = (TextView) view.findViewById(R.id.ctc_clubname);
            caption = (TextView) view.findViewById(R.id.ctc_posttitle);
            likes = (TextView) view.findViewById(R.id.ctc_likescount);
            comments = (TextView) view.findViewById(R.id.ctc_commentscount);
            postTime = (TextView) view.findViewById(R.id.ctc_posttime);
            clubIcon = (ImageView) view.findViewById(R.id.ctc_clubicon);
            like = (ImageView) view.findViewById(R.id.ctc_likebtn);
            comment = (ImageView) view.findViewById(R.id.ctc_commentbtn);
            share = (ImageView) view.findViewById(R.id.ctc_sharebtn);

            if(token.equals("63617168"))
            {
                acceptbutton=(Button)view.findViewById(R.id.post_accept);
                rejectbutton=(Button)view.findViewById(R.id.post_reject);
            }
            else
            {
                approved=(TextView)view.findViewById(R.id.status);
            }
        }
    }


    public CTAdapter(Context context,List<postsModel> topicList) {

        prefs=context.getSharedPreferences("com.dev.manan.adminappec2018", Context.MODE_PRIVATE);
        token = prefs.getString("token","");

        this.postsList = topicList;
        this.context =context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(token.equals("63617168")) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.culmyca_card1, parent, false);
        }
        else
        {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.culmyca_card, parent, false);
        }

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        topic = postsList.get(position);
        holder.caption.setText(topic.title);
        holder.postTime.setText(Long.toString(topic.time));
        holder.caption.setText(topic.title);
        holder.clubName.setText(topic.clubName);
        holder.likes.setText(Integer.toString(topic.likes) + " likes");
        holder.comments.setText(Integer.toString(topic.comments.size()) + " comments");

        if(token.equals("63617168")) {


            holder.acceptbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DatabaseReference mdatabase= FirebaseDatabase.getInstance().getReference().child("posts").child(topic.clubName).child(topic.postid).child("approval");
                    mdatabase.setValue(true);
                }
            });
        }
        else
        {
            if (topic.isApproval())
                holder.approved.setText("Approved");
            else
                holder.approved.setText("Pending");
        }

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(new Intent(context, CommentActivity.class)
                        .putExtra("clubName", topic.getClubName())
                        .putExtra("eventId", topic.getPostid()));
            }
        });
    }

    @Override
    public int getItemCount() {

        return postsList.size();
    }
}