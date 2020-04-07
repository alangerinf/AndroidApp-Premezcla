package com.ibao.premezcla.ui.mod3.tancada;

import com.ibao.premezcla.models.Tancada;


public class TancadaMuestraPresenter {

    private TancadaMuestraInteractor interactor;
    private TancadaMuestraActivity view;
    private int id;

    public TancadaMuestraPresenter(TancadaMuestraActivity activity, int id){
        this.interactor = new TancadaMuestraInteractor(this);
        this.view = activity;
        this.id = id;
    }

    String TAG = "TancadaMuestraPresenter.tk";
    public void requestAllData(){
        this.interactor.requestAllData(id);
    }

    public void showTancadaData(Tancada tancada){
        view.showTancada(tancada);
    }

    public void showError(String error){
        view.showError(error);
    }

    public void requestUpdateEstado(Tancada tancada){
        interactor.updateEstado(tancada);
    }

    public void respUpdateSuccess() {
        requestAllData();
    }

    public void respUpdateFailed(String fail) {
        showError(fail);
    }
}
