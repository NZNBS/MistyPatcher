package com.mreoz.mistypatcher2;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mreoz.mistypatcher2.Actividades.MRZActivity;
import com.mreoz.mistypatcher2.Actividades.MRZActivityADMIN;
import com.mreoz.mistypatcher2.Actividades.MRZActivityVIP;
import com.mreoz.mistypatcher2.Preferencias.Prefs;
import com.mreoz.mistypatcher2.R;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import static com.mreoz.mistypatcher2.Adaptadores.MRZH4CK3R.cs;
import static com.mreoz.mistypatcher2.Adaptadores.MRZH4CK3R.getfirma1;
import static com.mreoz.mistypatcher2.Adaptadores.MRZH4CK3R.getweb;

/**
 * Created by Akshay Raj on 10/17/2016.
 * Snow Corporation Inc.
 * www.snowcorp.org
 */

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    private DatabaseReference UsersRef;
    private DatabaseReference ServRef;
    public static String status = null;
    String imei;
    final String appver = "3.17";
    public static String USERN = "MRZ";

    @SuppressLint("MissingPermission")
    public String obtenerIMEI() {
        final TelephonyManager telephonyManager= (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return telephonyManager.getImei();
        }
        else {
            return telephonyManager.getDeviceId();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imei = obtenerIMEI();
        try {
            for (Signature signature : (getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES)).signatures) {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());
                if (!getfirma1().equals(Base64.encodeToString(messageDigest.digest(), 0).trim())){
                    android.app.AlertDialog.Builder lolcito = new android.app.AlertDialog.Builder(LoginActivity.this);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Please download original patcher");
                    lolcito.setMessage(stringBuilder.toString());
                    lolcito.setTitle("App not original!!").setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface param3DialogInterface, int param3Int) {
                            finish();
                        }
                    }).setCancelable(false).create().show();
                }
            }
        } catch (Exception aramBundle) {
            aramBundle.printStackTrace();
        }
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        ServRef = FirebaseDatabase.getInstance().getReference().child("Server2");
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            Checqueo();
        }else {
            Toast.makeText(LoginActivity.this, "Bienvenido!",Toast.LENGTH_LONG);
        }
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        btnSignup = findViewById(R.id.btn_signup);
        btnLogin = findViewById(R.id.btn_login);
        btnReset = findViewById(R.id.btn_reset_password);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
                //startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    String deviceToken = FirebaseInstanceId.getInstance().getToken();
                                    UsersRef.child(imei).child("email").setValue(email);
                                    UsersRef.child(imei).child("device_token")
                                            .setValue(deviceToken)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task)
                                                {
                                                    if (task.isSuccessful())
                                                    {
                                                        Checqueo();
                                                        Toast.makeText(LoginActivity.this, "Logged in Successful...", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            }
                        });
            }
        });
    }
    void setS(String paramString1, String paramString2) { Prefs.with(this).write(paramString1, paramString2); }
    public void Checqueo() {
        ProgressDialog asd = new ProgressDialog(LoginActivity.this);
        asd.setTitle("Obteniendo datos de la cuenta..");
        asd.setCancelable(false);
        asd.setCanceledOnTouchOutside(false);
        asd.show();
        final String currentUserId = obtenerIMEI();
        UsersRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child("userState").hasChild("status"))
                {
                    String state = dataSnapshot.child("userState").child("status").getValue().toString();
                    String username = dataSnapshot.child("username").getValue().toString();
                    if (state.equals("normal"))
                    {
                        comprobarserv("normal");
                    }
                    if (state.equals("vip"))
                    {
                        setS(USERN, username);
                        comprobarserv("vip");
                    }
                    if (state.equals("admin"))
                    {
                        setS(USERN, username);
                        comprobarserv("admin");
                    }
                    if (state.equals("owner"))
                    {
                        setS(USERN, username);
                        comprobarserv("owner");
                    }
                    if (state.equals("banned"))
                    {
                        Banned();
                    }
                }
                else
                {
                    String username = dataSnapshot.child("username").getValue().toString();
                    String state = dataSnapshot.child("userState").child("status").getValue().toString();
                    if (state.equals("normal"))
                    {
                        comprobarserv("normal");
                    }
                    if (state.equals("vip"))
                    {
                        setS(USERN, username);
                        comprobarserv("vip");
                    }
                    if (state.equals("admin"))
                    {
                        setS(USERN, username);
                        comprobarserv("admin");
                    }
                    if (state.equals("owner"))
                    {
                        setS(USERN, username);
                        comprobarserv("owner");
                    }
                    if (state.equals("banned"))
                    {
                        Banned();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void comprobarserv(final String status) {
        ProgressDialog asd = new ProgressDialog(LoginActivity.this);
        asd.setTitle("Obteniendo datos del servidor..");
        asd.setCancelable(false);
        asd.setCanceledOnTouchOutside(false);
        asd.show();
        ServRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String appv = dataSnapshot.child("appver").getValue().toString();
                String serv = dataSnapshot.child("status").getValue().toString();
                final String link = dataSnapshot.child("update").getValue().toString();
                if(!serv.equals("online") && appv.equals(appver)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("MisTyPatcher " +appv);
                    builder.setTitle(stringBuilder.toString());
                    String reason = dataSnapshot.child("reason").getValue().toString();
                    builder.setMessage(reason);
                    builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                            Toast.makeText(LoginActivity.this, "Sorry!", Toast.LENGTH_LONG).show();
                            param2DialogInterface.dismiss();
                            finish();
                        }
                    });
                    builder.create().show();
                    if (status.equals("admin")){
                        SendUserToAdmin();
                    }
                    if (status.equals("owner")){
                        SendUserToAdmin();
                        Toast.makeText(LoginActivity.this,"El servidor se encuentra apagado!",Toast.LENGTH_LONG).show();
                    }
                }
                if(!appv.equals(appver) && serv.equals("online")){
                    WebView webView = new WebView(LoginActivity.this);
                    webView.loadUrl(getweb()+cs());
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("New Update Of MisTyPatcher " + appv);
                    builder.setTitle(stringBuilder.toString());
                    builder.setView(webView);
                    builder.setPositiveButton("Download Now", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                            new DownloadFile().execute(link);
                        }
                    });
                    builder.setNegativeButton("Later", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                            Toast.makeText(LoginActivity.this, "Ok Update Later!", Toast.LENGTH_LONG).show();
                            param2DialogInterface.dismiss();
                            if (status.equals("normal")){
                                SendUserToNormal();
                            }
                            if (status.equals("vip")){
                                SendUserToVip();
                            }
                            if (status.equals("admin")){
                                SendUserToAdmin();
                            }
                            if (status.equals("owner")){
                                SendUserToAdmin();
                            }
                        }
                    });
                    builder.create().show();
                }
                if (!appv.equals(appver) && !serv.equals("online")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("MisTyPatcher Offline " +appv);
                    builder.setTitle(stringBuilder.toString());
                    String reason = dataSnapshot.child("reason").getValue().toString();
                    builder.setMessage(reason);
                    builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                            Toast.makeText(LoginActivity.this, "Sorry!", Toast.LENGTH_LONG).show();
                            param2DialogInterface.dismiss();
                            finish();
                        }
                    });
                    builder.create().show();
                    if (status.equals("admin")){
                        SendUserToAdmin();
                    }
                    if (status.equals("owner")){
                        SendUserToAdmin();
                        Toast.makeText(LoginActivity.this,"El servidor se encuentra apagado y con una actualizacion sin lanzar!",Toast.LENGTH_LONG).show();
                    }
                }
                if (appv.equals(appver)  && serv.equals("online")){
                    if (status.equals("normal")){
                        SendUserToNormal();
                    }
                    if (status.equals("vip")){
                        SendUserToVip();
                    }
                    if (status.equals("admin")){
                        SendUserToAdmin();
                    }
                    if (status.equals("owner")){
                        SendUserToAdmin();
                        Toast.makeText(LoginActivity.this,"El servidor se encuentra en estado normal",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void Banned() {
        android.app.AlertDialog.Builder lolcito = new android.app.AlertDialog.Builder(LoginActivity.this);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Tu cuenta a sido baneada :(");
        lolcito.setMessage(stringBuilder.toString());
        lolcito.setTitle("Error!!").setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param3DialogInterface, int param3Int) {
                finish();
            }
        }).setCancelable(false).create().show();
    }

    private void resetPassword() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_reset_password, null);
        dialogBuilder.setView(dialogView);

        final EditText editEmail = dialogView.findViewById(R.id.email);
        final Button btnReset = dialogView.findViewById(R.id.btn_reset_password);
        final ProgressBar progressBar1 = dialogView.findViewById(R.id.progressBar);

        //dialogBuilder.setTitle("Send Photos");
        final AlertDialog dialog = dialogBuilder.create();

        btnReset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = editEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar1.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }

                                progressBar1.setVisibility(View.GONE);
                                dialog.dismiss();
                            }
                        });
                findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });
        dialog.show();
    }
    private class DownloadFile extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;
        private String fileName;
        private String fname = null;
        private String folder = Environment.getExternalStorageDirectory() + File.separator + "mreoz/update/";


        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            new File(folder+"MisTyPatcher.apk").delete();
            this.progressDialog = new ProgressDialog(LoginActivity.this);
            this.progressDialog.setTitle("Descargando la actualizacion");
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection =  url.openConnection();
                connection.connect();
                // getting file length
                int lengthOfFile = connection.getContentLength();


                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

                //Extract file name from URL
                fileName = f_url[0].substring(f_url[0].lastIndexOf('/') + 1, f_url[0].length());

                //Append timestamp to file name
                fileName = timestamp + "_" + fileName;

                //External directory path to save file
                folder = Environment.getExternalStorageDirectory() + File.separator + "mreoz/update/";
                if(!new File(folder).exists()){
                    new File(folder).mkdir();
                }
                OutputStream output = new FileOutputStream(folder+"MisTyPatcher.apk");

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lengthOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();
                return "Downloaded at: " + folder + fileName;

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return "Something went wrong";
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }


        @Override
        protected void onPostExecute(String message) {
            // dismiss the dialog after the file was downloaded
            this.progressDialog.dismiss();
            install();
        }
    }

    private void install() {
        String folder = Environment.getExternalStorageDirectory() + File.separator + "mreoz/update/";
        File file = new File(folder, "MisTyPatcher.apk");
        Intent intent1 = new Intent("android.intent.action.VIEW");
		Intent intent3 = new Intent("android.intent.action.EDIT");
		intent1.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		Intent intent2 = intent3.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive").createChooser(intent3, "Open in...");
		intent2.putExtra("android.intent.extra.INITIAL_INTENTS", new Intent[] { intent1 });
		startActivity(intent2);
    }

    private void SendUserToNormal()
    {
        Intent mainIntent = new Intent(LoginActivity.this, MRZActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
    private void SendUserToVip()
    {
        Intent mainIntent = new Intent(LoginActivity.this, MRZActivityVIP.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
    private void SendUserToAdmin()
    {
        Intent mainIntent = new Intent(LoginActivity.this, MRZActivityADMIN.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}