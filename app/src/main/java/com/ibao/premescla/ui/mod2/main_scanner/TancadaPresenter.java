package com.ibao.premescla.ui.mod2.main_scanner;

import com.ibao.premescla.models.ProductoPesado;
import com.ibao.premescla.models.Tancada;
import com.ibao.premescla.ui.mod2.main_scanner.CustomScannerActivity;


public class TancadaPresenter {

    private TancadaInteractor interactor;
    private CustomScannerActivity view;
    private int id;

    public TancadaPresenter(CustomScannerActivity activity, int id){
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
