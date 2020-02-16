package com.ibao.premescla.ui.tancada;

import android.util.Log;

import com.ibao.premescla.models.Orden;
import com.ibao.premescla.models.Tancada;
import com.ibao.premescla.ui.orden.ActivityOrden;
import com.ibao.premescla.ui.orden.OrdenInteractor;

public class TancadaPresenter {

    private TancadaInteractor interactor;
    private ActivityTancada view;
    private int id;
    public TancadaPresenter(ActivityTancada activity, int id){
        this.interactor = new TancadaInteractor(this);
        this.view = activity;
        this.id = id;
    }

    String TAG = "TancadaPresenter.tk";
    public void requestAllData(){
        this.interactor.requestAllData(id);
    }

    public void showOrdenList(Orden orden){
        view.showOrder(orden);
    }
    public void showError(String error){
        view.showError(error);
    }


}
