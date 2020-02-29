package com.ibao.premescla.ui.mod1.main.presenters;

import android.util.Log;

import com.ibao.premescla.models.Orden;
import com.ibao.premescla.ui.mod1.main.interactor.MainInteractor;
import com.ibao.premescla.ui.mod1.main.views.MainActivity;

import java.util.List;

public class MainPresenter {

    private MainInteractor interactor;
    private MainActivity view;
    public MainPresenter(MainActivity activity){
        this.interactor = new MainInteractor(this);
        this.view = activity;
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
