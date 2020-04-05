package com.ibao.premescla.ui.mod2.main;

import com.ibao.premescla.models.Tancada;


public class TancadaPresenter {

    private TancadaInteractor interactor;
    private MainMezclaActivity view;
    private int id;

    public TancadaPresenter(MainMezclaActivity activity, int id){
        this.interactor = new TancadaInteractor(this);
        this.view = activity;
        this.id = id;

    }

    String TAG = "TancadaPresenter.tk";
    public void requestAllData(){
        this.interactor.requestAllData(id);
    }

    public void showTancadaData(Tancada tancada){
        view.showTancada(tancada);
    }
    public void showError(String error){
        view.showError(error);
    }
}
