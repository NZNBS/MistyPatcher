package com.mreoz.mistypatcher2.Adaptadores;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class DebugActivity extends Activity {
  String[] errMessage = { "Invalid string operation\n", "Invalid list operation\n", "Invalid arithmetical operation\n", "Invalid toNumber block operation\n", "Invalid intent operation" };
  
  String[] exceptionType = { "StringIndexOutOfBoundsException", "IndexOutOfBoundsException", "ArithmeticException", "NumberFormatException", "ActivityNotFoundException" };
  
  protected void onCreate(Bundle paramBundle) {
    int i = 0;
    super.onCreate(paramBundle);
    Intent intent = getIntent();
    String str2 = "";
    String str1 = str2;
    if (intent != null) {
      String str = intent.getStringExtra("error");
      String[] arrayOfString = str.split("\n");
      while (true) {
        str1 = str2;
        try {
          if (i < this.exceptionType.length) {
            str1 = str2;
            if (arrayOfString[0].contains(this.exceptionType[i])) {
              str1 = str2;
              str2 = this.errMessage[i];
              str1 = str2;
              int j = arrayOfString[0].indexOf(this.exceptionType[i]);
              str1 = str2;
              i = this.exceptionType[i].length();
              str2 = (str1 = str2).valueOf(str2) + arrayOfString[0].substring(i + j, arrayOfString[0].length());
            } else {
              i++;
              continue;
            } 
          } 
          str1 = str2;
          boolean bool = str2.isEmpty();
          str1 = str2;
          if (bool)
            str1 = str; 
        } catch (Exception tr2) {}
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("MisTyPatcher tuvo un error inesperado");
        builder1.setMessage(str1);
        builder1.setNeutralButton("Finalizar app", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface param1DialogInterface, int param1Int) { DebugActivity.this.finish(); }
            });
        builder1.create().show();
        return;
      } 
    } 
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("MisTyPatcher tuvo un error inesperado");
    builder.setMessage(str1);
    builder.setNeutralButton("Finalizar app", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface param1DialogInterface, int param1Int) { DebugActivity.this.finish(); }
        });
    builder.create().show();
  }
}
