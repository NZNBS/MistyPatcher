package com.mreoz.mistypatcher2.Adaptadores;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;

import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VActivityManager;
import com.mreoz.mistypatcher2.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class FloatingViewService extends Service {
    private View mFloatingView;

    private LinearLayout modBody;

    private WindowManager windowManager;
    private int convertDipToPixels(float paramFloat) { return (int)(paramFloat * (getResources().getDisplayMetrics()).density + 0.5F); }
    private LayerDrawable GetlayerDrawable() {
        float[] arrayOfFloat1 = new float[8];
        arrayOfFloat1[0] = 75.0F;
        arrayOfFloat1[1] = 75.0F;
        arrayOfFloat1[2] = 75.0F;
        arrayOfFloat1[3] = 75.0F;
        arrayOfFloat1[4] = 75.0F;
        arrayOfFloat1[5] = 75.0F;
        arrayOfFloat1[6] = 75.0F;
        arrayOfFloat1[7] = 75.0F;
        float[] arrayOfFloat2 = new float[8];
        arrayOfFloat2[0] = 75.0F;
        arrayOfFloat2[1] = 75.0F;
        arrayOfFloat2[2] = 75.0F;
        arrayOfFloat2[3] = 75.0F;
        arrayOfFloat2[4] = 75.0F;
        arrayOfFloat2[5] = 75.0F;
        arrayOfFloat2[6] = 75.0F;
        arrayOfFloat2[7] = 75.0F;
        ShapeDrawable shapeDrawable1 = new ShapeDrawable(new RoundRectShape(arrayOfFloat1, null, arrayOfFloat2));
        shapeDrawable1.getPaint().setColor(-65536);
        shapeDrawable1.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable1.setPadding(1, 1, 1, 1);
        ShapeDrawable shapeDrawable2 = new ShapeDrawable(new RoundRectShape(arrayOfFloat1, null, arrayOfFloat2));
        shapeDrawable2.getPaint().setColor(Color.parseColor("#ff1f2b3f"));
        shapeDrawable2.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable2.getPaint().setAntiAlias(true);
        shapeDrawable2.setPadding(5, 5, 5, 5);
        return new LayerDrawable(new Drawable[] { shapeDrawable1, shapeDrawable2 });
    }
    private void InstallMenu() {
        char c;
        if (Build.VERSION.SDK_INT >= 26) {
            c = '?';
        } else {
            c = '?';
        }
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(convertDipToPixels(60.0F), convertDipToPixels(60.0F)));
        try {
            InputStream inputStream = getAssets().open("ic_mrzfloat.png");
            imageView.setImageDrawable(Drawable.createFromStream(inputStream, null));
            inputStream.close();
            relativeLayout.addView(imageView);
            this.mFloatingView = relativeLayout;
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
            linearLayout.setBackgroundColor(Color.parseColor("#ff1f2b3f"));
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            TextView textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
            textView.setGravity(1);
            textView.setText(Html.fromHtml("<font face='fantasy'><b><font color='#57c4aa'>Hack By GameMod.Pro</b></font>"));
            textView.setTextSize(20.0F);
            WebView webView = new WebView(this);
            webView.setLayoutParams(new LinearLayout.LayoutParams(-2, convertDipToPixels(25.0F)));
            LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams)webView.getLayoutParams();
            layoutParams1.gravity = 17;
            layoutParams1.bottomMargin = 10;
            webView.setBackgroundColor(Color.parseColor("#ff1f2b3f"));
            webView.setVerticalScrollBarEnabled(false);
            webView.setHorizontalScrollBarEnabled(false);
            webView.loadData("<html><head><style>body{color: #f3c930;font-weight:bold;font-family:Courier, monospace;}</style></head><body><marquee class=\"GeneratedMarquee\" direction=\"left\" scrollamount=\"4\" behavior=\"scroll\">[GameMod.Pro] If Game Update Visit GameMod.Pro</marquee></body></html>", "text/html", "utf-8");
            ScrollView scrollView = new ScrollView(this);
            scrollView.setLayoutParams(new LinearLayout.LayoutParams(-1, convertDipToPixels(200.0F)));
            scrollView.setScrollBarSize(convertDipToPixels(5.0F));
            this.modBody = new LinearLayout(this);
            this.modBody.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
            this.modBody.setOrientation(LinearLayout.VERTICAL);
            addToggle("nazitah", new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton param1CompoundButton, boolean param1Boolean) {
                    Toast.makeText(FloatingViewService.this, "Game Mod By GameMod.Pro", Toast.LENGTH_SHORT).show();
                }
            });
            scrollView.addView(this.modBody);
            RelativeLayout relativeLayout1 = new RelativeLayout(this);
            relativeLayout1.setLayoutParams(new RelativeLayout.LayoutParams(-2, -1));
            relativeLayout1.setPadding(10, 10, 10, 10);
            relativeLayout1.setVerticalGravity(16);
            Button button1 = new Button(this);
            button1.setBackgroundColor(Color.parseColor("#12a56b"));
            button1.setText("CLOSE");
            button1.setTextColor(Color.parseColor("#e8f8f4"));
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams2.addRule(11);
            button1.setLayoutParams(layoutParams2);
            Button button2 = new Button(this);
            button2.setBackgroundColor(Color.parseColor("#c41313"));
            button2.setText("HIDE ICON");
            button2.setTextColor(Color.parseColor("#e8f8f4"));
            relativeLayout1.addView(button1);
            relativeLayout1.addView(button2);
            linearLayout.addView(textView);
            linearLayout.addView(webView);
            linearLayout.addView(scrollView);
            linearLayout.addView(relativeLayout1);
            frameLayout.addView(linearLayout);
            final AlertDialog alertDialog = (new AlertDialog.Builder(this, 2)).create();
            alertDialog.getWindow().setType(c);
            alertDialog.setView(frameLayout);
            final WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, c, 8, -3);
            layoutParams.gravity = 51;
            layoutParams.x = 0;
            layoutParams.y = 100;
            this.windowManager = (WindowManager)getSystemService("window");
            this.windowManager.addView(this.mFloatingView, layoutParams);
            this.mFloatingView.setOnTouchListener(new View.OnTouchListener() {
                private float initialTouchX;

                private float initialTouchY;

                private int initialX;

                private int initialY;

                public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
                    float f2;
                    float f1;
                    switch (param1MotionEvent.getAction()) {
                        default:
                            return false;
                        case 2:
                            f1 = Math.round(param1MotionEvent.getRawX() - this.initialTouchX);
                            f2 = Math.round(param1MotionEvent.getRawY() - this.initialTouchY);
                            layoutParams.x = this.initialX + (int)f1;
                            layoutParams.y = this.initialY + (int)f2;
                            FloatingViewService.this.windowManager.updateViewLayout(FloatingViewService.this.mFloatingView, layoutParams);
                            return true;
                        case 1:
                            alertDialog.show();
                            Toast.makeText(FloatingViewService.this, "Game Mod By GameMod.Pro", Toast.LENGTH_SHORT).show();
                            return true;
                        case 0:
                            break;
                    }
                    this.initialX = layoutParams.x;
                    this.initialY = layoutParams.y;
                    this.initialTouchX = param1MotionEvent.getRawX();
                    this.initialTouchY = param1MotionEvent.getRawY();
                    return true;
                }
            });
            button1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View param1View) { alertDialog.hide(); }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View param1View) {
                    alertDialog.hide();
                    FloatingViewService.this.stopSelf();
                }
            });
            return;
        } catch (IOException rameLayout) {
            return;
        }
    }
    private void Thread() {
        if (this.mFloatingView != null) {
            if (isChayNgam()) {
                this.mFloatingView.setVisibility(View.INVISIBLE);
                return;
            }
            this.mFloatingView.setVisibility(View.VISIBLE);
        }
    }
    private boolean isChayNgam() {
        ActivityManager.RunningAppProcessInfo runningAppProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(runningAppProcessInfo);
        return (runningAppProcessInfo.importance != 100);
    }
    public IBinder onBind(Intent paramIntent) { return null; }
    public void onCreate() {
        super.onCreate();
        InstallMenu();
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            public void run() {
                FloatingViewService.this.Thread();
                handler.postDelayed(this, 1000L);
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.mFloatingView != null)
            this.windowManager.removeView(this.mFloatingView);
    }
    private void addToggle(String paramString, CompoundButton.OnCheckedChangeListener paramOnCheckedChangeListener) {
        Switch switc = new Switch(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        layoutParams.setMargins(0, 2, 0, 0);
        switc.setLayoutParams(layoutParams);
        switc.setBackgroundColor(Color.parseColor("#2d4061"));
        switc.setPadding(10, 5, 10, 5);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<font face='fantasy'><font color='red'>[!]</font> <font color='yellow'>");
        stringBuilder.append(paramString);
        stringBuilder.append("</font></font>");
        switc.setText(Html.fromHtml(stringBuilder.toString()));
        switc.setTextSize(20.0F);
        switc.setTypeface(switc.getTypeface(), Typeface.BOLD);
        switc.setOnCheckedChangeListener(paramOnCheckedChangeListener);
        this.modBody.addView(switc);
    }
}
