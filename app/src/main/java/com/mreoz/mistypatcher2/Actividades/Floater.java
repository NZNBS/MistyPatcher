package com.mreoz.mistypatcher2.Actividades;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.mreoz.mistypatcher2.Adaptadores.H4X;
import com.mreoz.mistypatcher2.Adaptadores.MRZH4CK3R;
import com.mreoz.mistypatcher2.R;
import com.sunfusheng.marqueeview.MarqueeView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.DexClassLoader;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.mreoz.mistypatcher2.Adaptadores.MRZH4CK3R.getcasses;
import static com.mreoz.mistypatcher2.Adaptadores.MRZH4CK3R.getdwcdir;
import static com.mreoz.mistypatcher2.Adaptadores.MRZH4CK3R.getfirma1;
public class Floater extends Service {
    static ImageView ficon;
    private static Method method;
    private static Object ob;
    private View collapsedView;
    private View expandedView;
    private WindowManager mWindowManager;
    private View mFloatingView;
    static String pkg = "block.app.wars";
    static String lib = "libil2cpp";
    static String name = "Block City Wars";
    @SuppressLint("ResourceType")
    private void FloatButton() {
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null);
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WRAP_CONTENT,
                WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);


        //getting windows services and adding the floating view to it
        mWindowManager.addView(mFloatingView, params);


        //getting the collapsed and expanded view from the floating view
        collapsedView = mFloatingView.findViewById(R.id.mrz_floater_container);
        expandedView = mFloatingView.findViewById(R.id.mrz_menu_container);

        //adding click listener to close button and expanded view
        MRZH4CK3R mrzh4CK3R = new MRZH4CK3R();
        ((WebView)this.mFloatingView.findViewById(R.id.mrz_webv)).loadUrl(mrzh4CK3R.getSrvhd());
        mFloatingView.findViewById(R.id.mrz_fclose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Floater.this,"Cerrando patcher", Toast.LENGTH_LONG).show();
                chau();
                stopSelf();
            }
        });
        mFloatingView.findViewById(R.id.mrz_mclose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collapsedView.setVisibility(View.VISIBLE);
                expandedView.setVisibility(View.GONE);
            }
        });
        //adding an touchlistener to make drag movement of the floating widget
        mFloatingView.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;

                    case MotionEvent.ACTION_UP:
                        //when the drag is ended switching the state of the widget
                        collapsedView.setVisibility(View.GONE);
                        expandedView.setVisibility(View.VISIBLE);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        //this code is helping the widget to move around the screen with fingers
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        mWindowManager.updateViewLayout(mFloatingView, params);
                        return true;
                }
                return false;
            }
        });
        try {
            String stringBuilder = (getApplicationInfo()).dataDir + "/virtual/data/app/"+Floater.pkg+"/lib/"+Floater.lib+".so";
            RandomAccessFile randomAccessFile = new RandomAccessFile(stringBuilder, "rw");
            method.invoke(ob, this.mFloatingView, this, randomAccessFile);
            return;
        } catch (FileNotFoundException layoutParams) {
            Toast.makeText(Floater.this,"Lib no encontrada, Intente iniciar el juego primero" + layoutParams.getMessage(),Toast.LENGTH_LONG).show();
            stopSelf();

        } catch (Exception layoutParam) {
            layoutParam.printStackTrace();
            Toast.makeText(Floater.this,"Floater no encontrado "+layoutParam.getMessage(),Toast.LENGTH_LONG).show();
            stopSelf();
            return;
        }
    }
    public IBinder onBind(Intent paramIntent) { throw new UnsupportedOperationException("Not yet implemented"); }
    public void onCreate() {
        super.onCreate();
        H4X.dencFile(this);
        try {
            for (Signature signature : (getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES)).signatures) {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());
                if (!getfirma1().equals(Base64.encodeToString(messageDigest.digest(), 0).trim())){
                    AlertDialog.Builder lolcito = new AlertDialog.Builder(Floater.this);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Please download original patcher");
                    lolcito.setMessage(stringBuilder.toString());
                    lolcito.setTitle("App not original!!").setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface param3DialogInterface, int param3Int) {
                            stopSelf();
                        }
                    }).setCancelable(false).create().show();
                }
            }
        } catch (Exception aramBundle) {
            aramBundle.printStackTrace();
        }
        try {
            String str = getFilesDir() + getdwcdir();
            if ((new File(str)).exists()) {
                Class clazz = (new DexClassLoader(str, getDir("webview", 0).getAbsolutePath(), null, getClassLoader())).loadClass(getcasses());
                ob = clazz.newInstance();
                method = clazz.getMethod("Init", View.class, Context.class, RandomAccessFile.class);
                method.setAccessible(true);
                chau();
                return;
            }
            if (!(new File(str)).exists()) {
                Toast.makeText(Floater.this,"Archivo no econtrado",Toast.LENGTH_LONG).show();
                stopSelf();
                return;
            }if (!(new File(str)).exists()) {
                Toast.makeText(Floater.this,"El archivo de mods no encontrado",Toast.LENGTH_LONG).show();
                return;
            }else{
                Toast.makeText(Floater.this,"El archivo no se ha podido cargar",Toast.LENGTH_LONG).show();
            }
            return;
        } catch (Exception xception) {
            xception.printStackTrace();
            Toast.makeText(Floater.this,xception.getMessage(),Toast.LENGTH_LONG).show();
            chau();
            stopSelf();
            return;
        }
    }
    private void chau() {
        new File(getFilesDir() + getdwcdir()).delete();
    }
    public void onDestroy() {
        super.onDestroy();
        if (this.mFloatingView != null) {
            mWindowManager.removeView(this.mFloatingView);
            ficon = null;
        }
    }
    public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
        FloatButton();
        return START_STICKY;
    }
}
