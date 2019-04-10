package com.droiddevsa.doganator.Utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {
    private final Executor dbIO;
    private final Executor uiThread;
    private static AppExecutors sInstance;
    private static final Object LOCK = new Object();

    private static class MainThreadExceutor implements Executor{
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command)
        {
            mainThreadHandler.post(command);
        }
    }

    public Executor dbIO() {return dbIO;}

    private AppExecutors(Executor dbIO,Executor uiThread){
        this.dbIO = dbIO;
        this.uiThread = uiThread;
    }

    public static AppExecutors getInstance(){
        if(sInstance ==null)
            synchronized (LOCK){
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor(),new MainThreadExceutor());
        }
            return sInstance;
    }

}
