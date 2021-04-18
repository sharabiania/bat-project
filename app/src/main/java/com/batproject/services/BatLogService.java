package com.batproject.services;

import android.util.Log;

/**
 * Created by Ali Sharabiani on 2018-03-04.
 */
public class BatLogService {

    private String tag = "";

    public BatLogService(String tag){
        this.tag = tag;
    }

    public int i(String message) {
        return Log.i(tag, message);
    }

    public int d(String message){
        return Log.d(tag, message);
    }

    public int w(String message) { return Log.w(tag, message); }

    public int e(String message) {
        return Log.e(tag, message);
    }

    public int e(String message, Throwable tr){
        return Log.e(tag, message, tr);
    }
}