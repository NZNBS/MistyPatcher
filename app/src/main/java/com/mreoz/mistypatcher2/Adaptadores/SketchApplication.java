package com.mreoz.mistypatcher2.Adaptadores;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Process;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.lody.virtual.client.core.VirtualCore;

import java.io.PrintWriter;
import java.io.StringWriter;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;

public class SketchApplication extends Application {
  private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
  
  private String getStackTrace(Throwable paramThrowable) {
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    while (true) {
      final String str;
      if (paramThrowable == null) {
        str = stringWriter.toString();
        printWriter.close();
        return str;
      }
      Exception e = null;
      e.printStackTrace(printWriter);
      Throwable throwable = e.getCause();
    } 
  }
  
  public void onCreate() {
    this.uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
          public void uncaughtException(Thread param1Thread, Throwable param1Throwable) {
            Intent intent = new Intent(SketchApplication.this.getApplicationContext(), DebugActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("error", SketchApplication.this.getStackTrace(param1Throwable));
                PendingIntent pendingIntent = PendingIntent.getActivity(SketchApplication.this.getApplicationContext(), 11111, intent, PendingIntent.FLAG_ONE_SHOT);
            ((AlarmManager)SketchApplication.this.getSystemService(Context.ALARM_SERVICE)).set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000L, pendingIntent);
            Process.killProcess(Process.myPid());
            System.exit(2);
            SketchApplication.this.uncaughtExceptionHandler.uncaughtException(param1Thread, param1Throwable);
          }
        });
    super.onCreate();
      Fabric.with(getApplicationContext(), new Kit[] { new Crashlytics(), new Answers() });
  }
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
}
