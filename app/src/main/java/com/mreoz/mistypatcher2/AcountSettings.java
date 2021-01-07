package com.mreoz.mistypatcher2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mreoz.mistypatcher2.R;

public class AcountSettings extends AppCompatActivity {
    private TextView email;
    private Button signOut;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private DatabaseReference UsersRef;
    String imei;
    static final Integer PHONESTATS = 0x1;

    // Con este método mostramos en un Toast con un mensaje que el usuario ha concedido los permisos a la aplicación
    private void consultarPermiso(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(AcountSettings.this, permission) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(AcountSettings.this, permission)) {

                ActivityCompat.requestPermissions(AcountSettings.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(AcountSettings.this, new String[]{permission}, requestCode);
            }
        } else {
            imei = obtenerIMEI();
         }
    }
    // Con este método consultamos al usuario si nos puede dar acceso a leer los datos internos del móvil
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case 1: {

                // Validamos si el usuario acepta el permiso para que la aplicación acceda a los datos internos del equipo, si no denegamos el acceso
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    imei = obtenerIMEI();

                } else {
                    Toast.makeText(AcountSettings.this, "Necesitamos ese permiso", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }
    @SuppressLint("MissingPermission")
    private String obtenerIMEI() {
        final TelephonyManager telephonyManager= (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Hacemos la validación de métodos, ya que el método getDeviceId() ya no se admite para android Oreo en adelante, debemos usar el método getImei()
            return telephonyManager.getImei();
        }
        else {
            return telephonyManager.getDeviceId();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resent);
        consultarPermiso(Manifest.permission.READ_PHONE_STATE, PHONESTATS);
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(AcountSettings.this, LoginActivity.class));
                    finish();
                }
            }
        };
        String id4 = imei;
        TextView id1 = findViewById(R.id.id);
        final TextView acountype = findViewById(R.id.acounttype);
        id1.setText(id4);
        signOut = findViewById(R.id.sign_out);
        email = findViewById(R.id.email);
        findViewById(R.id.copyid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id2 = imei;
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text",  id2);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(AcountSettings.this, "Copiado",Toast.LENGTH_LONG).show();
            }
        });
        email.setText("Correo: "+user.getEmail());
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        UsersRef.child(imei).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child("userState").hasChild("status"))
                {
                    String state = dataSnapshot.child("userState").child("status").getValue().toString();

                    if (state.equals("normal"))
                    {
                        acountype.setText("Normal");
                    }
                    if (state.equals("vip"))
                    {
                        acountype.setText("Vip");
                    }
                    if (state.equals("admin"))
                    {
                        acountype.setText("Administrator");
                    }
                    if (state.equals("banned"))
                    {
                        finish();
                    }
                }
                else
                {
                    String state = dataSnapshot.child("userState").child("status").getValue().toString();

                    if (state.equals("normal"))
                    {
                        acountype.setText("Normal");
                    }
                    if (state.equals("vip"))
                    {
                        acountype.setText("Vip");
                    }
                    if (state.equals("admin"))
                    {
                        acountype.setText("Administrator");
                    }
                    if (state.equals("banned"))
                    {
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                finish();
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //sign out method
    public void signOut() {
        auth.signOut();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
