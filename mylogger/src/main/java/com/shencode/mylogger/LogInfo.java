package com.shencode.mylogger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogInfo {
    public static String[] priorityNames={"0","1","v","d","i","w","e","7","8","9","10"};
    public static int ID=0;
    private int id=0;
    private int priority;
    private String adapterTag;
    private String logTag;
    private String message;

    public int getPriority() {
        return priority;
    }

    public String getLogTag() {
        return logTag;
    }

    public String getAdapterTag() {
        return adapterTag;
    }

     public LogInfo(int priority, @Nullable String adapterTag,@Nullable String logTag, @NonNull String message){
        ID++;
        id=ID;
        this.priority=priority;
        this.adapterTag =adapterTag;
        this.logTag =logTag;
        this.message=message;
    }

    @NonNull
    @Override
    public String toString() {
        SimpleDateFormat formatter=new SimpleDateFormat("HH:mm:ss.SSS");
        String t=formatter.format(new Date());
        if(logTag ==null){
            return String.format("[%s][%s][%s][%s]\n%s",id,t,priorityNames[priority],adapterTag,message);
        }
        else{
            return String.format("[%s][%s][%s][%s][%s]\n%s",id,t,priorityNames[priority],adapterTag,logTag,message);
        }
    }
}
