package com.newtech.newtech_sfm.Metier;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by stagiaireit2 on 26/07/2016.
 */
public class LogSync {
    String date,
            msg,
            type;

    int success;

    public LogSync() {
    }

    public LogSync(String msg, String type,int success) {
        DateFormat df = new SimpleDateFormat("HH:mm:ss ");
        String date = df.format(Calendar.getInstance().getTime());

        this.date = date;
        this.msg = msg;
        this.type = type;
        this.success = success;
    }

    public LogSync(String msg, String type) {
        DateFormat df = new SimpleDateFormat("HH:mm:ss ");
        String date = df.format(Calendar.getInstance().getTime());

        this.date = date;
        this.msg = msg;
        this.type = type;
    }


    public String getMsg() {
        return msg;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "LogSync{" +
                "date='" + date + '\'' +
                ", msg='" + msg + '\'' +
                ", type='" + type + '\'' +
                ", success=" + success +
                '}';
    }
}
