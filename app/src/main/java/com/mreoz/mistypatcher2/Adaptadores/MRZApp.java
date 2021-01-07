package com.mreoz.mistypatcher2.Adaptadores;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.lody.virtual.client.core.VirtualCore;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;

public class MRZApp extends Application {
    protected void attachBaseContext(Context paramContext) {
        super.attachBaseContext(paramContext);
        try {
            VirtualCore.get().startup(paramContext);
            return;
        } catch (Throwable parmContext) {
            parmContext.printStackTrace();
            return;
        }
    }

    public void onCreate() {
        super.onCreate();
        Fabric.with(getApplicationContext(), new Kit[] { new Crashlytics(), new Answers() });
    }
}
