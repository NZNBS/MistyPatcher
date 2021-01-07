package com.mreoz.mistypatcher2.Actividades;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mreoz.mistypatcher2.Adaptadores.H4X;
import com.mreoz.mistypatcher2.R;

import java.io.File;

import static com.mreoz.mistypatcher2.Adaptadores.MRZH4CK3R.strdes;
import static com.mreoz.mistypatcher2.Adaptadores.MRZH4CK3R.strenc;

public class console extends AppCompatActivity {
    Button seleccionarfile;
    private DatabaseReference ServRef;
    private DatabaseReference UsersRef;
    static String pkg = "block.app.wars";
    String midid;

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
        midid = obtenerIMEI();
        setContentView(R.layout.activity_console);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(midid);
        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String estado = dataSnapshot.child("userState").child("status").getValue().toString();
                String tipomod = dataSnapshot.child("userState").child("type").child("mod").getValue().toString();
                String tipovend = dataSnapshot.child("userState").child("type").child("vend").getValue().toString();
                if(estado.equals("owner")){
                    cargar();
                    own();
                    own2();
                }else{
                    if(tipomod.equals("true")){
                        cargar();
                    }
                    if (tipovend.equals("true")){
                        vendedor();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void own() {
        findViewById(R.id.own).setVisibility(View.VISIBLE);
        ServRef = FirebaseDatabase.getInstance().getReference().child("Server2");
        final EditText inputreason = findViewById(R.id.reason);
        final EditText inputver = findViewById(R.id.ver);
        final EditText inputlink = findViewById(R.id.link);findViewById(R.id.own).setVisibility(View.VISIBLE);
        findViewById(R.id.lnzupdt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog asd = new ProgressDialog(console.this);
                asd.setTitle("Actualizando servidor..");
                asd.setCancelable(false);
                asd.setCanceledOnTouchOutside(false);
                asd.show();
                ServRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String newappv = inputver.getText().toString();
                        String appv = dataSnapshot.child("appver").getValue().toString();
                        String link = inputlink.getText().toString();
                        if (!appv.equals(newappv)){
                            ServRef.child("appver").setValue(newappv);
                            ServRef.child("update").setValue(link).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        asd.dismiss();
                                        Toast.makeText(console.this,"Servidor actualizado",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        findViewById(R.id.onof).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServRef = FirebaseDatabase.getInstance().getReference().child("Server2");
                final ProgressDialog asd = new ProgressDialog(console.this);
                asd.setTitle("Actualizando servidor..");
                asd.setCancelable(false);
                asd.setCanceledOnTouchOutside(false);
                asd.show();
                ServRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String serv = dataSnapshot.child("status").getValue().toString();
                        if(serv.equals("online")){
                            String reaso = inputreason.getText().toString();
                            ServRef.child("reason").setValue(reaso);
                            ServRef.child("status")
                                    .setValue("offline")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if (task.isSuccessful())
                                            {
                                                asd.dismiss();
                                                Button online = findViewById(R.id.onof);
                                                online.setText("Encender");
                                                Toast.makeText(console.this, "Servidor puesto en mantenimiento", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        if(!serv.equals("online")){
                            ServRef.child("status")
                                    .setValue("online")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if (task.isSuccessful())
                                            {
                                                asd.dismiss();
                                                Button online = findViewById(R.id.onof);
                                                online.setText("Apagar");
                                                Toast.makeText(console.this, "Servidor puesto en linea!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
    private void own2() {
        findViewById(R.id.vendedores).setVisibility(View.VISIBLE);
        final ProgressDialog asd = new ProgressDialog(console.this);
        asd.setTitle("Actualizando usuario");
        asd.setCancelable(false);
        asd.setCanceledOnTouchOutside(false);
        final EditText inputidc = findViewById(R.id.idclient);
        findViewById(R.id.vendedores).setVisibility(View.VISIBLE);
        final SeekBar sekbar = findViewById(R.id.conta);
        final TextView clientpud = findViewById(R.id.clientupdte);
        findViewById(R.id.tablaids).setVisibility(View.GONE);
        findViewById(R.id.searhclient).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String currentUserId = inputidc.getText().toString();
                if (TextUtils.isEmpty(currentUserId)) {
                    inputidc.setError("Coloca una ID!");
                    return;
                }
                if (currentUserId.length() < 15) {
                    inputidc.setError("Tu id es errada (+)");
                }
                if (currentUserId.length() > 15) {
                    inputidc.setError("Tu id es errada (-)");
                }
                if (currentUserId.length() == 15) {
                    UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
                    UsersRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String estado = dataSnapshot.child("userState").child("status").getValue().toString();
                            final String username = dataSnapshot.child("username").getValue().toString();
                            String mail = dataSnapshot.child("email").getValue().toString();
                            findViewById(R.id.tablaids).setVisibility(View.VISIBLE);
                            TextView name = findViewById(R.id.nameclient);
                            name.setText(username);
                            TextView maill = findViewById(R.id.clientmail);
                            maill.setText(mail);
                            TextView status = findViewById(R.id.clientstatus);
                            status.setText(estado);
                            sekbar.setMax(4);
                            sekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                @SuppressLint("ResourceAsColor")
                                @Override
                                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                    if (progress == 0) {
                                        clientpud.setText("Nada");
                                        findViewById(R.id.updclient).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(console.this, "No tienes cambios en la cuenta!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    if (progress == 1) {
                                        clientpud.setText("Normal");
                                        findViewById(R.id.updclient).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                asd.show();
                                                UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
                                                UsersRef.child("userState").child("status").setValue("normal").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            asd.dismiss();
                                                            findViewById(R.id.tablaids).setVisibility(View.GONE);
                                                            Toast.makeText(console.this, username+" actualizado a normal", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                    if (progress == 2) {
                                        clientpud.setText("Vip");
                                        findViewById(R.id.updclient).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                asd.show();
                                                UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
                                                UsersRef.child("userState").child("status").setValue("vip").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            asd.dismiss();
                                                            findViewById(R.id.tablaids).setVisibility(View.GONE);
                                                            Toast.makeText(console.this, username+" actualizado a VIP", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                    if (progress == 3) {
                                        clientpud.setText("Admin & Owner");
                                        findViewById(R.id.updclient).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                asd.show();
                                                AlertDialog.Builder seleccionartipo1 = new AlertDialog.Builder(console.this);
                                                seleccionartipo1.setTitle("A que lo quiere actualizar?").setNegativeButton("Vendedor o Modder", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        AlertDialog.Builder seleccionartipo2 = new AlertDialog.Builder(console.this);
                                                        seleccionartipo2.setTitle("Vendedor || Modder").setNegativeButton("Vendedor", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
                                                                UsersRef.child("userState").child("type").child("mod").setValue("false");
                                                                UsersRef.child("userState").child("type").child("vend").setValue("true");
                                                                UsersRef.child("userState").child("status").setValue("admin").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        Toast.makeText(console.this,"Usuario actualizado a Administrador (Vendedor)",Toast.LENGTH_LONG).show();
                                                                        asd.dismiss();
                                                                                }
                                                                });
                                                            }
                                                        }).setNeutralButton("Modder", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
                                                                UsersRef.child("userState").child("type").child("mod").setValue("true");
                                                                UsersRef.child("userState").child("type").child("vend").setValue("false");
                                                                UsersRef.child("userState").child("status").setValue("admin").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        Toast.makeText(console.this,"Usuario actualizado a Administrador (Desarollador de mods)",Toast.LENGTH_LONG).show();
                                                                        asd.dismiss();
                                                                    }
                                                                });
                                                            }
                                                        }).setPositiveButton("Ambos", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
                                                                UsersRef.child("userState").child("type").child("mod").setValue("true");
                                                                UsersRef.child("userState").child("type").child("vend").setValue("true");
                                                                UsersRef.child("userState").child("status").setValue("admin").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        Toast.makeText(console.this,"Usuario actualizado a Administrador (Vendedor y Desarollador de mods)",Toast.LENGTH_LONG).show();
                                                                        asd.dismiss();
                                                                    }
                                                                });
                                                            }
                                                        }).setCancelable(false).create().show();
                                                    }
                                                }).setNeutralButton("Admin", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
                                                        UsersRef.child("userState").child("type").child("mod").setValue("false");
                                                        UsersRef.child("userState").child("type").child("vend").setValue("false");
                                                        UsersRef.child("userState").child("status").setValue("admin").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                Toast.makeText(console.this,"Usuario actualizado a Administrador (Solo acceso offline)",Toast.LENGTH_LONG).show();
                                                                asd.dismiss();
                                                            }
                                                        });
                                                    }
                                                }).setPositiveButton("Creador", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
                                                        UsersRef.child("userState").child("type").child("mod").setValue("true");
                                                        UsersRef.child("userState").child("type").child("vend").setValue("true");
                                                        UsersRef.child("userState").child("status").setValue("owner").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                Toast.makeText(console.this,"Usuario actualizado a Creador",Toast.LENGTH_LONG).show();
                                                                asd.dismiss();
                                                            }
                                                        });
                                                    }
                                                }).setCancelable(false).create().show();
                                                findViewById(R.id.tablaids).setVisibility(View.GONE);
                                            }
                                        });
                                    }
                                    if (progress == 4) {
                                        clientpud.setText("Banned");
                                        findViewById(R.id.updclient).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                asd.show();
                                                UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
                                                UsersRef.child("userState").child("status").setValue("banned").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            asd.dismiss();
                                                            findViewById(R.id.tablaids).setVisibility(View.GONE);
                                                            Toast.makeText(console.this, username+" BANEADO!!!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onStartTrackingTouch(SeekBar seekBar) {

                                }

                                @Override
                                public void onStopTrackingTouch(SeekBar seekBar) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }
    private void vendedor() {
        findViewById(R.id.vendedores).setVisibility(View.VISIBLE);
        final ProgressDialog asd = new ProgressDialog(console.this);
        asd.setTitle("Actualizando usuario");
        asd.setCancelable(false);
        asd.setCanceledOnTouchOutside(false);
        final EditText inputidc = findViewById(R.id.idclient);
        findViewById(R.id.vendedores).setVisibility(View.VISIBLE);
        final SeekBar sekbar = findViewById(R.id.conta);
        final TextView clientpud = findViewById(R.id.clientupdte);
        findViewById(R.id.tablaids).setVisibility(View.GONE);
        findViewById(R.id.searhclient).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String currentUserId = inputidc.getText().toString();
                if (TextUtils.isEmpty(currentUserId)) {
                    inputidc.setError("Coloca una ID!");
                    return;
                }
                if (currentUserId.length() < 15) {
                    inputidc.setError("Tu id es errada (+)");
                }
                if (currentUserId.length() > 15) {
                    inputidc.setError("Tu id es errada (-)");
                }
                if (currentUserId.length() == 15) {
                    UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
                    UsersRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String estado = dataSnapshot.child("userState").child("status").getValue().toString();
                            final String username = dataSnapshot.child("username").getValue().toString();
                            String mail = dataSnapshot.child("email").getValue().toString();
                            findViewById(R.id.tablaids).setVisibility(View.VISIBLE);
                            TextView name = findViewById(R.id.nameclient);
                            name.setText(username);
                            TextView maill = findViewById(R.id.clientmail);
                            maill.setText(mail);
                            TextView status = findViewById(R.id.clientstatus);
                            status.setText(estado);
                            if (estado.equals("admin")){
                                Toast.makeText(console.this,"Es admin.. No tienes permisos suficientes para poder realizar cambios en esta cuenta :(",Toast.LENGTH_SHORT).show();
                            }if(estado.equals("owner")){
                                Toast.makeText(console.this,"ESTAS CAMBIANDOLE EL RANGO A UN CREADOR? MUY MAL MUCHACHO!",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                sekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @SuppressLint("ResourceAsColor")
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                        if (progress == 0) {
                                            clientpud.setText("Nada");
                                            findViewById(R.id.updclient).setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Toast.makeText(console.this, "No tienes cambios en la cuenta!", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        if (progress == 1) {
                                            clientpud.setText("Normal");
                                            findViewById(R.id.updclient).setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    asd.show();
                                                    UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
                                                    UsersRef.child("userState").child("status").setValue("normal").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                asd.dismiss();
                                                                findViewById(R.id.tablaids).setVisibility(View.GONE);
                                                                Toast.makeText(console.this, username+" actualizado a normal", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                        if (progress == 2) {
                                            clientpud.setText("Vip");
                                            findViewById(R.id.updclient).setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    asd.show();
                                                    UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
                                                    UsersRef.child("userState").child("status").setValue("vip").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                asd.dismiss();
                                                                findViewById(R.id.tablaids).setVisibility(View.GONE);
                                                                Toast.makeText(console.this, username+" actualizado a VIP", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                        if (progress == 3) {
                                            clientpud.setText("Banned");
                                            findViewById(R.id.updclient).setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    asd.show();
                                                    UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
                                                    UsersRef.child("userState").child("status").setValue("banned").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                asd.dismiss();
                                                                findViewById(R.id.tablaids).setVisibility(View.GONE);
                                                                Toast.makeText(console.this, username+" BANEADO!!!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {

                                    }

                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }
    private String getVersion() {
        PackageManager packageManager = getPackageManager();
        try {
            return (packageManager.getPackageInfo(this.pkg, 0)).versionName;
        } catch (android.content.pm.PackageManager.NameNotFoundException ackageManager) {
            return "N/A";
        }
    }
    private void cargar() {
        findViewById(R.id.modders).setVisibility(View.VISIBLE);
        if(!new File(Environment.getExternalStorageDirectory() + "/MrEoZ/Encripted BCW/").exists()){
            new File(Environment.getExternalStorageDirectory() + "/MrEoZ/Encripted BCW/").mkdir();
        }
        seleccionarfile = findViewById(R.id.btn_locate);
        findViewById(R.id.btn_locate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooserr();
            }
        });
        findViewById(R.id.btn_encrip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooser();
            }
        });
        findViewById(R.id.btn_sholoct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ver = getVersion().replace(".", "");
                if (new File(Environment.getExternalStorageDirectory() + strenc()+ver+".mrz").exists()){
                    startPatcher();
                }
                if (!new File(Environment.getExternalStorageDirectory() + strenc()+ver+".mrz").exists()){
                    Toast.makeText(console.this, "No tienes ningun archivo encriptado :(" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void startPatcher() {
        if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(this)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("package:");
            stringBuilder.append(getPackageName());
            startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse(stringBuilder.toString())), 123);
            return;
        }
        startFloater();
    }
    private void startFloater() {
        if (!isServiceRunning())
            startService(new Intent(this, HXD.class));
    }
   private boolean isServiceRunning() {
        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null)
            for (ActivityManager.RunningServiceInfo runningServiceInfo : activityManager.getRunningServices(2147483647)) {
                if (HXD.class.getName().equals(runningServiceInfo.service.getClassName()))
                    return true;
            }
        return false;
    }
    private void showChooserr() {
        if(new File(Environment.getExternalStorageDirectory() + strdes()).exists()){
            Toast.makeText(console.this,"Archivo seleccionado", Toast.LENGTH_SHORT).show();
        }
        if (!new File(Environment.getExternalStorageDirectory() + strdes()).exists()){
            Toast.makeText(console.this, "Ingrese el archivo" + strdes(), Toast.LENGTH_SHORT).show();
        }
    }
    private void showChooser() {
        Toast.makeText(console.this, "Encriptando archivo", Toast.LENGTH_SHORT).show();
        if (!new File(Environment.getExternalStorageDirectory() + strdes()).exists()) {
            Toast.makeText(console.this, "Ingrese el archivo" + strdes(), Toast.LENGTH_SHORT).show();
        }
        if (new File(Environment.getExternalStorageDirectory() + strdes()).exists()) {
            int flag = 1;
            String ver = getVersion().replace(".", "");
            final ProgressDialog mancito = new ProgressDialog(console.this);
            mancito.setTitle("Comprobando informacion");
            mancito.setMessage("Checking Things...");
            mancito.setIndeterminate(true);
            mancito.setCancelable(false);
            mancito.setCanceledOnTouchOutside(false);
            mancito.show();
            try {
                if (flag == 1) {
                    console.this.runOnUiThread(new Runnable() {
                        public void run() {
                            mancito.setTitle("Encriptando");
                            mancito.setMessage("Espere...");
                        }
                    });
                    H4X.encFile(this,ver);
                    Thread.sleep(700L);
                }
                console.this.runOnUiThread(new Runnable() {
                    public void run() {
                        mancito.dismiss();
                    }
                });
                return;
            } catch (Exception exception) {
                exception.printStackTrace();
                console.this.runOnUiThread(new Runnable() {
                    public void run() {
                        mancito.dismiss();
                    }
                });
                return;
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
