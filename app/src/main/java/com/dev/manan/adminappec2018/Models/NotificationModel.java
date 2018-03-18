package com.dev.manan.adminappec2018.Models;

/**
 * Created by Kachucool on 18-03-2018.
 */

public class NotificationModel {

    String text;
    long time;

    NotificationModel()
    {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    NotificationModel(String text, long time)
    {
        this.text=text;
        this.time=time;

    }
}
