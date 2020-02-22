package com.ibao.premescla.ui.tancada;

import android.os.Handler;

import com.ibao.premescla.models.ProductoPesado;
import com.ibao.premescla.models.Tancada;


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

    public void showTancadaData(Tancada tancada){
        view.showTancada(tancada);
    }
    public void goToAddPPesado(ProductoPesado ppesado,int actual, int all){
        view.goToActivityPPesado(ppesado,actual,all);
    }
    public void showError(String error){
        view.showError(error);
    }
    public void requestNewPPesado(int id){ this.interactor.requestNewPPesado(id);}

}
