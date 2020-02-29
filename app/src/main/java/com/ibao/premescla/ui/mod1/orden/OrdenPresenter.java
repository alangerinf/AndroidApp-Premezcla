package com.ibao.premescla.ui.mod1.orden;

import android.util.Log;

import com.ibao.premescla.models.Orden;

public class OrdenPresenter {

    private OrdenInteractor interactor;
    private ActivityOrden view;
    private int id;
    public OrdenPresenter(ActivityOrden activity,int id){
        this.interactor = new OrdenInteractor(this);
        this.view = activity;
        this.id = id;
    }

    String TAG = "MainPresenter.tk";
    public void requestAllData(){
        Log.d(TAG,"requestAllData()");
        this.interactor.requestAllData(id);
    }

    public void showOrdenList(Orden orden){
        view.showOrder(orden);
    }
    public void showError(String error){
        view.showError(error);
    }


}
