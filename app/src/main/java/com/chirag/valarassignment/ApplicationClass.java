package com.chirag.valarassignment;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;


public class ApplicationClass extends Application {

    public static ApplicationClass context;

    public static Context getAppContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

}
