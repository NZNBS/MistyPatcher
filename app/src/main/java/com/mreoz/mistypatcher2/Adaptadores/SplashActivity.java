package com.mreoz.mistypatcher2.Adaptadores;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.mreoz.mistypatcher2.Actividades.IntroActivity;
import com.mreoz.mistypatcher2.LoginActivity;
import com.mreoz.mistypatcher2.Preferencias.Prefs;
import com.mreoz.mistypatcher2.R;

import java.io.File;

public class SplashActivity extends AppCompatActivity {
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_splash_screen);
        new File(Environment.getExternalStorageDirectory() + File.separator + "mreoz/update/MisTyPatcher.apk").delete();
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                if (!Prefs.with(SplashActivity.this).readBoolean("firstboot", false)) {
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                } else {
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                SplashActivity.this.finish();
            }
        },700L);
    }
}
