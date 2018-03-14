package com.dev.manan.adminappec2018.Models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Himanshu on 3/14/18.
 */

public class Post {
    public String token;
    public String author;
    public String body;
    public int likes = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public Post(String token, String author, String body) {
        this.token = token;
        this.author = author;
        this.body = body;
    }

    public Post() {

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("author", author);
        result.put("body", body);
        result.put("likes", likes);

        return result;
    }
}
