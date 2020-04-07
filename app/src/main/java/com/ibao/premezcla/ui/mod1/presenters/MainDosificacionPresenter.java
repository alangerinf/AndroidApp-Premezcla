package com.ibao.premezcla.ui.mod1.presenters;

import android.util.Log;

import com.ibao.premezcla.models.Orden;
import com.ibao.premezcla.ui.mod1.interactor.MainDosificacionInteractor;
import com.ibao.premezcla.ui.mod1.views.fragment.main.MainFragment;

import java.util.List;

public class MainDosificacionPresenter {

    private MainDosificacionInteractor interactor;
    private MainFragment view;
    public MainDosificacionPresenter(MainFragment fragment){
        this.interactor = new MainDosificacionInteractor(this);
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
