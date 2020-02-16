package com.ibao.premescla.ui.orden;

import android.util.Log;

import com.ibao.premescla.models.Orden;
import com.ibao.premescla.ui.main.interactor.MainInteractor;
import com.ibao.premescla.ui.main.views.MainActivity;

import java.util.List;

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
