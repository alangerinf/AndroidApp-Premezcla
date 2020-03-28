package com.ibao.premescla.ui.mod3.tancada_muestra;

import com.ibao.premescla.models.ProductoPesado;
import com.ibao.premescla.models.Tancada;
import com.ibao.premescla.ui.mod1.tancada.ActivityTancada;
import com.ibao.premescla.ui.mod1.tancada.TancadaInteractor;


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

}
