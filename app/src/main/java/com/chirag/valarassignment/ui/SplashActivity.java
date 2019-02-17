package com.chirag.valarassignment.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.chirag.valarassignment.R;
import com.chirag.valarassignment.databinding.ActivitySplashBinding;
import com.chirag.valarassignment.ui.base.BaseActivity;
import com.chirag.valarassignment.ui.home.MainActivity;
import com.chirag.valarassignment.utils.AppAnimationUtils;

public class SplashActivity extends BaseActivity {

    ActivitySplashBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        setFullScreenView();

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(300);
        rotateAnimation.setRepeatCount(Animation.INFINITE);

        AppAnimationUtils.fadeInAnimation(mBinding.tvAppLogo, 1600, 1000);

        runSplash();
    }

    //Hiding status/navigation bar
    public void setFullScreenView() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_splash);
    }

    private void runSplash() {
        //animateImage();
        new Handler().postDelayed(this::startActivity, 4000);
    }

    private void startActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
