package com.qdrs.sketchxu.wechat.model;

import android.os.AsyncTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CocurrentUtil {

    private static final String TAG = "CocurrentUtil";

    private static CocurrentUtil mInstance;

    public static final int CORE_SIZE = 10;

    ExecutorService exec = null;

    private CocurrentUtil(){
        exec = Executors.newFixedThreadPool(CORE_SIZE);
    }

    public static  synchronized CocurrentUtil getInstance() {
        if (mInstance == null) {
            mInstance = new CocurrentUtil();
        }

        return mInstance;
    }

    public void addTask(Runnable task) {
        if (exec != null) {
            exec.execute(task);
        }
    }

    public void addAsyncTask(AsyncTask task) {
        if (exec != null) {
            task.executeOnExecutor(exec);
        }
    }
}
