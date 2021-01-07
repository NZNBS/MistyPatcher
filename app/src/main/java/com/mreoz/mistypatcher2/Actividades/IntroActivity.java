package com.mreoz.mistypatcher2.Actividades;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;
import com.mreoz.mistypatcher2.LoginActivity;
import com.mreoz.mistypatcher2.Preferencias.Prefs;
import com.mreoz.mistypatcher2.R;

public class IntroActivity extends AppIntro2 {
    protected void onCreate(@Nullable Bundle paramBundle) {
        super.onCreate(paramBundle);
        SliderPage sliderPage = new SliderPage();
        sliderPage.setTitle("Welcome!");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("This is New ");
        stringBuilder.append("MisTyPatcher");
        sliderPage.setDescription(stringBuilder.toString());
        sliderPage.setImageDrawable(R.drawable.ic_mfloat1);
        sliderPage.setBgColor(getResources().getColor(R.color.colorAccent));
        addSlide(AppIntroFragment.newInstance(sliderPage));
        sliderPage = new SliderPage();
        sliderPage.setTitle("Exclusive Mods");
        sliderPage.setDescription("MisTyPatcher Offers Big List of Mods");
        sliderPage.setImageDrawable(R.drawable.stickers_pack);
        sliderPage.setBgColor(getResources().getColor(R.color.colorAccent));
        addSlide(AppIntroFragment.newInstance(sliderPage));
        sliderPage = new SliderPage();
        sliderPage.setTitle("Simple, yet Customizable");
        sliderPage.setDescription("The SuperPatcher have Live Patching Capability");
        sliderPage.setImageDrawable(R.drawable.photo_paint);
        sliderPage.setBgColor(getResources().getColor(R.color.colorAccent));
        addSlide(AppIntroFragment.newInstance(sliderPage));
        sliderPage = new SliderPage();
        sliderPage.setTitle("Explore");
        sliderPage.setDescription("Feel free to explore the rest of the SuperPatcher's Features!");
        sliderPage.setImageDrawable(R.drawable.search_web);
        sliderPage.setBgColor(getResources().getColor(R.color.colorAccent));
        addSlide(AppIntroFragment.newInstance(sliderPage));
        setFlowAnimation();
        askForPermissions(new String[] { "android.permission.WRITE_EXTERNAL_STORAGE" }, 1);
        askForPermissions(new String[] { "android.permission.READ_PHONE_STATE" }, 2);
        showSkipButton(false);
        showStatusBar(false);
        setBarColor(getResources().getColor(R.color.colorAccent));
    }

    public void onDonePressed(Fragment paramFragment) {
        super.onDonePressed(paramFragment);
        Prefs.with(this).writeBoolean("firstboot", true);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
