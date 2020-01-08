package com.shencode.mylogger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;

public class MyLog {
    public static MyLogAdapter setTag(String tag){
        return MyLogAdapter.setNew(tag);
    }

    public static void v(String msg){
        Logger.v(msg);
    }
    public static void v(String tag,String msg){
        Logger.t(tag).v(msg);
    }

    public static void v(@NonNull String msg, @Nullable Object... args){
        Logger.v(msg,args);
    }

    public static void d(String msg){
        Logger.d(msg);
    }
    public static void d(String tag,String msg){
        Logger.t(tag).d(msg);
    }
    public static void d(@Nullable Object object){
        Logger.d(object);
    }

    public static void i(String msg){
        Logger.i(msg);
    }
    public static void i(String tag,String msg){
        Logger.t(tag).i(msg);
    }

    public static void w(String msg){
        Logger.w(msg);
    }
    public static void w(String tag,String msg){
        Logger.t(tag).w(msg);
    }

    public static void e(String msg){
        Logger.e(msg);
    }
    public static void e(String tag,String msg){
        Logger.t(tag).e(msg);
    }
}
