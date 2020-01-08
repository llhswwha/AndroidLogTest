package com.shencode.mylogger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.shencode.mylogger.LogInfo;
import com.shencode.mylogger.LogListAdapter;
import com.shencode.mylogger.MyLog;
import com.shencode.mylogger.MyLogAdapter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LogViewerActivity extends AppCompatActivity {
    public static String TAG="LogViewerActivity";
    private Spinner spinnerActivityList;
    private Spinner spinnerTagList;
    private CheckBox cbV;
    private CheckBox cbD;
    private CheckBox cbI;
    private CheckBox cbW;
    private CheckBox cbE;
    private CompoundButton.OnCheckedChangeListener cbChangeLisener;
    private String adapterTag;
    private String logTag;
    private boolean enableFilter=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_viewer);
        initControls();
    }
    private void initControls(){
        initSpinner();
        MyLog.setTag(TAG);
        showLogs();
        cbChangeLisener=new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Logger.v("onCheckedChanged:"+buttonView+"|"+isChecked);
                filterLogs();
            }
        };
        cbV=findViewById(R.id.cbLogLevelV);
        cbV.setOnCheckedChangeListener(cbChangeLisener);
        cbD=findViewById(R.id.cbLogLevelD);
        cbD.setOnCheckedChangeListener(cbChangeLisener);
        cbI=findViewById(R.id.cbLogLevelI);
        cbI.setOnCheckedChangeListener(cbChangeLisener);
        cbW=findViewById(R.id.cbLogLevelW);
        cbW.setOnCheckedChangeListener(cbChangeLisener);
        cbE=findViewById(R.id.cbLogLevelE);
        cbE.setOnCheckedChangeListener(cbChangeLisener);
        initButton();
    }
    private void initButton() {
        findViewById(R.id.btnTestLog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testLogger();
            }
        });
        findViewById(R.id.btnTestLog2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyLog.setTag(TAG+"1");
                Logger.d("debug");
                Logger.e("error");
                Logger.v("v");
                Logger.i("info");

                Logger.w("no thread info and only 1 method");
                Logger.i("no thread info and method info");
                Logger.t("tag").e("Custom tag for only one use");
                Logger.json("{ \"key\": 3, \"value\": something}");
                Logger.d(Arrays.asList("foo", "bar"));
                Map<String, String> map = new HashMap<>();
                map.put("key", "value");
                map.put("key1", "value2");
                Logger.d(map);

                MyLog.setTag(TAG+"2");
                Logger.t("tag1").e("tag1");
                Logger.t("tag2").e("tag2");

                MyLog.setTag(TAG+"3");
                Logger.t("tag11").e("tag11");
                Logger.t("tag21").e("tag21");

                showLogs();
            }
        });
        findViewById(R.id.btnShowLog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogs();
            }
        });
        findViewById(R.id.btnClearLog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyLogAdapter.clearLogs();
                showLogs();
            }
        });
        findViewById(R.id.btnClearFilterLog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableFilter=false;
                spinnerTagList.setSelection(0);
                spinnerActivityList.setSelection(0);
                cbV.setChecked(true);
                cbD.setChecked(true);
                cbI.setChecked(true);
                cbW.setChecked(true);
                cbE.setChecked(true);
                enableFilter=true;
                filterLogs();
            }
        });
    }

    private void initSpinner() {
        spinnerActivityList=findViewById(R.id.spinnerActivityList);
        spinnerActivityList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView=(TextView)view;
                adapterTag=textView.getText().toString();
                //Logger.v(view+"|"+adapterTag);
                filterLogs();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                adapterTag=null;
            }
        });
        spinnerTagList=findViewById(R.id.spinnerTagList);
        spinnerTagList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView=(TextView)view;
                logTag=textView.getText().toString();
                //Logger.v(view+"|"+logTag);
                filterLogs();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                logTag=null;
            }
        });
    }

    private void filterLogs(){
        if(enableFilter==false)return;
        Logger.v("filterLogs,%s,%s",adapterTag,logTag);
        List<Integer> levels=new LinkedList<>();
        levels.add(0);
        levels.add(1);

        if(cbV.isChecked()){
            levels.add(2);
        }
        if(cbD.isChecked()){
            levels.add(3);
        }
        if(cbI.isChecked()){
            levels.add(4);
        }
        if(cbW.isChecked()){
            levels.add(5);
        }
        if(cbE.isChecked()){
            levels.add(6);
        }

        List<LogInfo> logInfoList= MyLogAdapter.getLogs();
        for (int i=0;i<logInfoList.size();i++){
            LogInfo log=logInfoList.get(i);
            if(!levels.contains(log.getPriority())){
                logInfoList.remove(i);
                i--;
            }
        }

        if(adapterTag!=null && adapterTag!="None"){
            for (int i=0;i<logInfoList.size();i++){
                LogInfo log=logInfoList.get(i);
                if(log.getAdapterTag()!=adapterTag){
                    logInfoList.remove(i);
                    i--;
                }
            }
        }
        if(logTag!=null && logTag!="None"){
            for (int i=0;i<logInfoList.size();i++){
                LogInfo log=logInfoList.get(i);
                if(log.getLogTag()!=logTag){
                    logInfoList.remove(i);
                    i--;
                }
            }
        }
        showLogs(logInfoList);
    }

    private void showLogs(){
        List<LogInfo> logInfoList= MyLogAdapter.getLogs();
        showLogs(logInfoList);

        showActivityList(spinnerActivityList, MyLogAdapter.adapterTags);
        showActivityList(spinnerTagList, MyLogAdapter.logTags);
    }

    private void showLogs(List<LogInfo> logInfoList){
        ListView listView = findViewById(R.id.lvLogList);
        LogListAdapter logAdapter=new LogListAdapter(logInfoList);
        listView.setAdapter(logAdapter);
    }

    private void showActivityList(Spinner spinner,List<String> items){
        List<String> dropItems=new LinkedList<>();
        dropItems.add("None");
        for (String item:items) {
            dropItems.add(item);
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, dropItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    private void testLogger(){
        Log.d("Tag", "I'm a log which you don't see easily, hehe");
        Log.d("json content", "{ \"key\": 3, \n \"value\": something}");
        Log.d("error", "There is a crash somewhere or any warning");

        Logger.addLogAdapter(new MyLogAdapter());
        Logger.d("message");

        Logger.clearLogAdapters();


        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(3)        // (Optional) Skips some method invokes in stack trace. Default 5
//        .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("My custom tag")   // (Optional) Custom tag for each log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new MyLogAdapter(formatStrategy));

        Logger.addLogAdapter(new MyLogAdapter() {
            @Override public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

        Logger.addLogAdapter(new DiskLogAdapter());


        Logger.w("no thread info and only 1 method");

        Logger.clearLogAdapters();
        formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .build();

        Logger.addLogAdapter(new MyLogAdapter(formatStrategy));
        Logger.i("no thread info and method info");

        Logger.t("tag").e("Custom tag for only one use");

        Logger.json("{ \"key\": 3, \"value\": something}");

        Logger.d(Arrays.asList("foo", "bar"));

        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        map.put("key1", "value2");

        Logger.d(map);

        Logger.clearLogAdapters();
        formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .tag("MyTag")
                .build();
        Logger.addLogAdapter(new MyLogAdapter(formatStrategy));

        Logger.w("my log message with my tag");
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
