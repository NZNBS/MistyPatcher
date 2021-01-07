package com.mreoz.mistypatcher2.Adaptadores;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import static com.mreoz.mistypatcher2.Adaptadores.MRZH4CK3R.getdwcdir;
import static com.mreoz.mistypatcher2.Adaptadores.MRZH4CK3R.getdwdir;
import static com.mreoz.mistypatcher2.Adaptadores.MRZH4CK3R.password;
import static com.mreoz.mistypatcher2.Adaptadores.MRZH4CK3R.strdes;
import static com.mreoz.mistypatcher2.Adaptadores.MRZH4CK3R.strenc;
import static com.mreoz.mistypatcher2.Adaptadores.MRZH4CK3R.strtest;

public class H4X {

    public static void dencFile(Context paramContext) {
        String enc = paramContext.getFilesDir() + getdwdir();
        File des = new File(paramContext.getFilesDir() + getdwcdir());
        String str2 = password();
        try {
            FileInputStream fileInputStream = new FileInputStream(enc);
            FileOutputStream fileOutputStream = new FileOutputStream(des);
            byte[] arrayOfByte2 = str2.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("SHA-1").digest(arrayOfByte2), 16), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            CipherInputStream cipherInputStream = new CipherInputStream(fileInputStream, cipher);
            byte[] arrayOfByte1 = new byte[8];
            while (true) {
                int i = cipherInputStream.read(arrayOfByte1);
                if (i == -1) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    cipherInputStream.close();
                    return;
                }
                fileOutputStream.write(arrayOfByte1, 0, i);
            }
        } catch (IOException file) {
            file.printStackTrace();
            Toast.makeText(paramContext, "Error IOException " + file.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        } catch (InvalidKeyException file) {
            file.printStackTrace();
            Toast.makeText(paramContext, "Error InvalidKeyException " + file.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        } catch (NoSuchAlgorithmException file) {
            file.printStackTrace();
            Toast.makeText(paramContext, "Error NoSuchAlgorithmException " + file.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        } catch (NoSuchPaddingException file) {
            file.printStackTrace();
            Toast.makeText(paramContext, "Error NoSuchPaddingException " + file.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
    }
    public static void dencFileAD(Context paramContext, String ver) {
        String enc = Environment.getExternalStorageDirectory() + strenc()+ver+".mrz";
        File des = new File(Environment.getExternalStorageDirectory() + strtest());
        String str2 = password();
        try {
            FileInputStream fileInputStream = new FileInputStream(enc);
            FileOutputStream fileOutputStream = new FileOutputStream(des);
            byte[] arrayOfByte2 = str2.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("SHA-1").digest(arrayOfByte2), 16), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            CipherInputStream cipherInputStream = new CipherInputStream(fileInputStream, cipher);
            byte[] arrayOfByte1 = new byte[8];
            while (true) {
                int i = cipherInputStream.read(arrayOfByte1);
                if (i == -1) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    cipherInputStream.close();
                    return;
                }
                fileOutputStream.write(arrayOfByte1, 0, i);
            }
        } catch (IOException file) {
            file.printStackTrace();
            Toast.makeText(paramContext, "Error IOException " + file.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        } catch (InvalidKeyException file) {
            file.printStackTrace();
            Toast.makeText(paramContext, "Error InvalidKeyException " + file.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        } catch (NoSuchAlgorithmException file) {
            file.printStackTrace();
            Toast.makeText(paramContext, "Error NoSuchAlgorithmException " + file.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        } catch (NoSuchPaddingException file) {
            file.printStackTrace();
            Toast.makeText(paramContext, "Error NoSuchPaddingException " + file.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
    }
    public static void encFile(Context paramContext, String ver) {
        String enc = Environment.getExternalStorageDirectory() + strdes();
        String des = Environment.getExternalStorageDirectory() + strenc()+ver+".mrz";
        String key = password();
        try {
            FileInputStream fileInputStream = new FileInputStream(enc);
            FileOutputStream fileOutputStream = new FileOutputStream(des);
            byte[] arrayOfByte2 = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("SHA-1").digest(arrayOfByte2), 16), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            CipherInputStream cipherInputStream = new CipherInputStream(fileInputStream, cipher);
            byte[] arrayOfByte1 = new byte[8];
            while (true) {
                int i = cipherInputStream.read(arrayOfByte1);
                if (i == -1) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    cipherInputStream.close();
                    return;
                }
                fileOutputStream.write(arrayOfByte1, 0, i);
            }
        } catch (IOException file) {
            file.printStackTrace();
            Toast.makeText(paramContext, "Error IOException " + file.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        } catch (InvalidKeyException file) {
            file.printStackTrace();
            Toast.makeText(paramContext, "Error InvalidKeyException " + file.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        } catch (NoSuchAlgorithmException file) {
            file.printStackTrace();
            Toast.makeText(paramContext, "Error NoSuchAlgorithmException " + file.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        } catch (NoSuchPaddingException file) {
            file.printStackTrace();
            Toast.makeText(paramContext, "Error NoSuchPaddingException " + file.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
    }
}