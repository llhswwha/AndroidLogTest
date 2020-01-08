package com.shencode.mylogger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MyLogAdapter extends AndroidLogAdapter {

    private String tag;

//    public static MyLogAdapter instance;

    public static String lastTag=null;

    public static MyLogAdapter lastAdapter=null;

    public static MyLogAdapter setNew(String tag){
        if(lastTag==tag){
            return lastAdapter;
        }
        Logger.clearLogAdapters();
        MyLogAdapter adapterEx= MyLogAdapter.getNew(tag);
        Logger.addLogAdapter(adapterEx);
        lastAdapter=adapterEx;
        return adapterEx;
    }

    public static List<String> adapterTags =new LinkedList<>();

    public static MyLogAdapter getNew(String tag){
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(1)
                .methodOffset(5)
                .tag(tag)
                .build();
        MyLogAdapter adapterEx=new MyLogAdapter(formatStrategy);
        adapterEx.tag=tag;
        if(tag!=null && !adapterTags.contains(tag))
            adapterTags.add(tag);
        return adapterEx;
    }

    public MyLogAdapter() {
        super();
//        instance=this;
    }

    public MyLogAdapter(@NonNull FormatStrategy formatStrategy) {
        super(formatStrategy);
//        instance=this;
    }

    public static List<LogInfo> getLogs() {
        List<LogInfo> items=new LinkedList<>();
        for (LogInfo log:logs) {
            items.add(log);
        }
        return items;
    }

    public static void clearLogs() {

        logs.clear();
        LogInfo.ID=0;
        adapterTags.clear();
        logTags.clear();
    }

    public void setLogs(List<LogInfo> logs) {
        this.logs = logs;
    }

    private static List<LogInfo> logs=new LinkedList<LogInfo>();

    public static List<String> logTags =new LinkedList<>();

    @Override
    public void log(int priority, @Nullable String tag, @NonNull String message) {
        if(tag!=null && !logTags.contains(tag)){
            logTags.add(tag);
        }
        SimpleDateFormat formatter=new SimpleDateFormat("[HH:mm:ss.SSS]");
        String t=formatter.format(new Date());
        super.log(priority, tag, t+message);
        logs.add(new LogInfo(priority,this.tag,tag,message));
    }
}
