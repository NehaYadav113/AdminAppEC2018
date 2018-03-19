package com.dev.manan.adminappec2018.Models;

/**
 * Created by Shubham on 3/19/2018.
 */

public class PostModel {
    public String title;
    public String photoid;
    public String clubName;
    public long time;

    public PostModel() {
    }

    public PostModel(String title, String photoid, String clubName, long time) {
        this.title = title;
        this.photoid = photoid;
        this.clubName = clubName;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoid() {
        return photoid;
    }

    public void setPhotoid(String photoid) {
        this.photoid = photoid;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
