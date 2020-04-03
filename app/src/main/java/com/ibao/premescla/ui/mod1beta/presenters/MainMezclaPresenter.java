package com.ibao.premescla.ui.mod1beta.presenters;

import android.util.Log;

import com.ibao.premescla.models.Orden;
import com.ibao.premescla.ui.mod1beta.interactor.MainMezclaInteractor;
import com.ibao.premescla.ui.mod1beta.views.MainMezclaActivity;
import com.ibao.premescla.ui.mod1beta.views.fragment.MainFragment;

import java.util.List;

public class MainMezclaPresenter {

    private MainMezclaInteractor interactor;
    private MainFragment view;
    public MainMezclaPresenter(MainFragment fragment){
        this.interactor = new MainMezclaInteractor(this);
        this.view = fragment;
    }
    String TAG = "MainPresenter.tk";
    public void requestAllData(){
        Log.d(TAG,"requestAllData()");
        this.interactor.requestAllData();
    }

    public void showOrdenList(List<Orden> ordenList){
        view.showOrderList(ordenList);
    }
    public void showError(String error){
        view.showError(error);
    }




}
