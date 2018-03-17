package com.dev.manan.adminappec2018;

import java.util.ArrayList;

/**
 * Created by Kachucool on 17-03-2018.
 */

public class NewPost {


    public String title;
    public String photoid;
    public boolean approval;
    public int likes;
    public long time;
    public ArrayList<Comment> comments;
    public ArrayList<likesModel> likefids;

    public NewPost() {
    }

    public NewPost(String title, String photoid,boolean approval, int likes, long time, ArrayList<Comment> comments, ArrayList<likesModel> likefids) {
        this.title = title;
        this.photoid = photoid;
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


    public boolean isApproval() {
        return approval;
    }

    public void setApproval(boolean approval) {
        this.approval = approval;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
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

