package com.ibao.premezcla.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.ibao.premezcla.R;
import com.ibao.premezcla.SharedPreferencesManager;
import com.ibao.premezcla.models.User;
import com.ibao.premezcla.ui.login.view.LoginActivity;
import com.ibao.premezcla.ui.mod1.views.MainDosificacionActivity;
import com.ibao.premezcla.ui.mod2.main.MainMezclaActivity;
import com.ibao.premezcla.ui.mod3.main.MainAplicacionActivity;
import com.ibao.premezcla.ui.modselector.ModSelectorActivity;

public class ActivityPreloader extends Activity {

    static LottieAnimationView lAVbackground;
    static ImageView iViewLogoEmpresa;
    static TextView tViewlogo_p1;
    static TextView tViewlogo_p2;
    static Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preloader);
        declare();
        startAnimations();
    }

    private void declare() {
        ctx = this;
        lAVbackground = findViewById(R.id.lottie);
        iViewLogoEmpresa = findViewById(R.id.iViewLogoEmpresa);
        tViewlogo_p1 = findViewById(R.id.tViewlogo_p1);
        tViewlogo_p2 = findViewById(R.id.tViewlogo_p2);
    }

    void startAnimations(){
        final Animation animLayout =
                android.view.animation.AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade_in);

        Handler handler = new Handler();
        handler.post(
                () -> {
                    lAVbackground.startAnimation(animLayout);
                    lAVbackground.setVisibility(View.VISIBLE);
                }
        );

        final Animation anim_rightFadeIn1 =
                android.view.animation.AnimationUtils.loadAnimation(getBaseContext(), R.anim.right_fade_in);

        Handler handler1 = new Handler();
        handler1.post(
                () -> {
                    iViewLogoEmpresa.startAnimation(anim_rightFadeIn1);
                    iViewLogoEmpresa.setVisibility(View.VISIBLE);
                }
        );
        final Animation anim_rightFadeIn2 =
                android.view.animation.AnimationUtils.loadAnimation(getBaseContext(), R.anim.top_fade_in);
        Handler handler2 = new Handler();
        handler2.postDelayed(
                () -> {
                    tViewlogo_p1.startAnimation(anim_rightFadeIn2);
                    tViewlogo_p1.setVisibility(View.VISIBLE);
                },500
        );
        final Animation anim_rightFadeIn3 =
                android.view.animation.AnimationUtils.loadAnimation(getBaseContext(), R.anim.top_fade_in);
        Handler handler3 = new Handler();
        handler3.postDelayed(
                () -> {
                    tViewlogo_p2.startAnimation(anim_rightFadeIn3);
                    tViewlogo_p2.setVisibility(View.VISIBLE);
                },600
        );

        Handler handler4 = new Handler();
        handler4.postDelayed(()->
            {
                User user = SharedPreferencesManager.getUser(ctx);
                if(user!=null){
                   switch (user.getCurrentModule()){
                       case 0:
                           startActivity(new Intent(this, ModSelectorActivity.class));
                           break;
                       case 1:
                           startActivity(new Intent(this, MainDosificacionActivity.class));
                           break;
                       case 2:
                           startActivity(new Intent(this, MainMezclaActivity.class));
                           break;
                       case 3:
                           startActivity(new Intent(this, MainAplicacionActivity.class));
                           break;
                   }
                }else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
            },2000);
    }

}
