package com.ibao.premezcla.ui.login.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.ibao.premezcla.R;
import com.ibao.premezcla.ui.login.presenter.LoginPresenter;
import com.ibao.premezcla.ui.login.presenter.LoginPresenterImpl;
import com.ibao.premezcla.ui.modselector.ModSelectorActivity;


public class LoginActivity extends Activity implements LoginView{


    private LoginPresenter presenter;

    //contexto
    private Context ctx;
    private ProgressBar progressBar;
    static LottieAnimationView lAVbackground;
    //boton enter
    private Button btnLogin;

    //campos de acceso
    private EditText eTextUser, eTextPassword;
    //animacion  de boton
    private Animation animBtn;
    //boton de ver password
    private ImageView iViewPasswordSetVisible;

    private Animation animLayout;
    private ConstraintLayout constCombo;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ctx =  LoginActivity.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);


        declaration();
        defineEvents();
        enterAnimation();

    }

    void declaration(){

        lAVbackground = findViewById(R.id.lottie);
        presenter = new LoginPresenterImpl(this);
        btnLogin    = findViewById(R.id.btnLogin);
        eTextUser   = findViewById(R.id.eTextEmail);
        eTextPassword   = findViewById(R.id.eTextPassword);
        iViewPasswordSetVisible = findViewById(R.id.iViewPassword);
        animBtn     = android.view.animation.AnimationUtils.loadAnimation(getBaseContext(),R.anim.press_btn);
        animLayout  = android.view.animation.AnimationUtils.loadAnimation(getBaseContext(),R.anim.left_in);
        constCombo  = findViewById(R.id.constCombo);
        progressBar = findViewById(R.id.progressBar);


        defaultAttributes();
    }

    void defaultAttributes(){
        handler.post(
                () -> {
                    constCombo.setVisibility(View.INVISIBLE);
                    hideProgressBar();
                }
        );

    }

    void defineEvents(){
        btnLogin.setOnClickListener(v -> {
            v.startAnimation(animBtn);
            handler.postDelayed(
                    () -> {
                        presenter.signIn(
                                eTextUser.getText().toString(),
                                eTextPassword.getText().toString()
                                );

                    },200
            );
        });

        iViewPasswordSetVisible.setOnClickListener(v -> {
            v.startAnimation(animBtn);
            if(eTextPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                eTextPassword.setInputType( InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }else {
                eTextPassword.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
            }
            eTextPassword.setSelection(eTextPassword.getText().length());
        });



    }


    private void enterAnimation() {

        final Animation animLayout =
                android.view.animation.AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade_in);

        Handler handler = new Handler();
        handler.post(
                () -> {
                    lAVbackground.startAnimation(animLayout);
                    lAVbackground.setVisibility(View.VISIBLE);
                }
        );


        handler.postDelayed(
                () -> {
                    constCombo.startAnimation(animLayout);
                    constCombo.setVisibility(View.VISIBLE);
                    enableInputs();
                },500
        );
    }


    @Override
    public void onResume() {
        super.onResume();
        btnLogin.setClickable(true);
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        moveTaskToBack(true);
    }


    @Override
    public void goRecoverPassword() {

    }

    @Override
    public void goHome() {

        Intent intent = new Intent(getBaseContext(), ModSelectorActivity.class);

        View viewTemp = findViewById(R.id.logo);
        ActivityOptions options = (ActivityOptions) ActivityOptions.makeSceneTransitionAnimation
                (this,
                        Pair.create(viewTemp, viewTemp.getTransitionName())
                );
        startActivity(intent, options.toBundle());

    }


    @Override
    public void enableInputs() {
        handler.post(
                () -> {
                    btnLogin.setEnabled(true);
                    eTextUser.setEnabled(true);
                    eTextPassword.setEnabled(true);
                    eTextPassword.setEnabled(true);
                    eTextPassword.setEnabled(true);
                    iViewPasswordSetVisible.setEnabled(true);
                    constCombo.setEnabled(true);
                }
        );
    }



    @Override
    public void disableInputs() {
        handler.post(
                () -> {
                    btnLogin.setEnabled(false);
                    eTextUser.setEnabled(false);
                    eTextPassword.setEnabled(false);
                    eTextPassword.setEnabled(false);
                    eTextPassword.setEnabled(false);
                    iViewPasswordSetVisible.setEnabled(false);
                    constCombo.setEnabled(false);
                }
        );

    }

    @Override
    public void hideProgressBar() {
        handler.post(
                () -> {
                    progressBar.setVisibility(View.INVISIBLE);
                }
        );

    }

    @Override
    public void showProgressBar() {
        handler.post(
                () -> {
                    progressBar.setVisibility(View.VISIBLE);
                }
        );
    }

    @Override
    public void loginError(String error) {
        handler.post(
                () -> {
                    Toast.makeText(ctx,error, Toast.LENGTH_LONG).show();
                }
        );

    }

}
