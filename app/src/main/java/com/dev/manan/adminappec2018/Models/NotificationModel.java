package com.dev.manan.adminappec2018.Models;

/**
 * Created by Kachucool on 18-03-2018.
 */

public class NotificationModel {
    String text,textHeading;
    long time;

    NotificationModel()
    {}

    public NotificationModel(String text, String textHeading, long time) {
        this.text = text;
        this.textHeading = textHeading;
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextHeading() {
        return textHeading;
    }

    public void setTextHeading(String textHeading) {
        this.textHeading = textHeading;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
