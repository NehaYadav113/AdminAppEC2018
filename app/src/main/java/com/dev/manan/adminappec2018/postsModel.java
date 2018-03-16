package com.dev.manan.adminappec2018;

import java.util.ArrayList;

/**
 * Created by KASHISH on 10-03-2018.
 */

public class postsModel {


    public String title;
    public String photoid;
    public String clubName;
    public String postid;
    public boolean approval;
    public String likes;
    public String time;
    public ArrayList<Comment> comments;
    public ArrayList<likesModel> likefids;

    public postsModel() {
    }

    public void setTime(String time) {
        this.time = time;
    }

    public postsModel(String title, String photoid, String clubName, String postid, boolean approval, String likes, String time, ArrayList<Comment> comments, ArrayList<likesModel> likefids) {
        this.title = title;
        this.photoid = photoid;
        this.clubName = clubName;
        this.postid = postid;

        this.approval = approval;
        this.likes = likes;
        this.time = time;
        this.comments = comments;
        this.likefids = likefids;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.photoid = photoid;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public boolean isApproval() {
        return approval;
    }

    public void setApproval(boolean approval) {
        this.approval = approval;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }



    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public ArrayList<likesModel> getLikefids() {
        return likefids;
    }

    public void setLikefids(ArrayList<likesModel> likefids) {
        this.likefids = likefids;
    }



    public String getPhotoid() {
        return photoid;
    }

    public void setPhotoid(String photoid) {
        this.photoid = photoid;
    }

}
