package com.ibao.premescla.ui.mod2.select;

import com.ibao.premescla.models.Conductor;
import com.ibao.premescla.models.Tancada;
import com.ibao.premescla.models.Tractor;

import java.util.List;


public class SelectorPresenter {

    private SelectorInteractor interactor;
    private SelectAplicatorsActivity view;

    public SelectorPresenter(SelectAplicatorsActivity activity){
        this.interactor = new SelectorInteractor(this);
        this.view = activity;

    }

    String TAG = "TancadaPresenter.tk";
    public void requestAllData(int id){
        this.interactor.requestAllData(id);
    }

    public void save(Tancada tancada){

        this.interactor.save(tancada);
    }


    public void showConductores(List<Conductor> conductores){

        view.showConductores(conductores);
    }

    public void showError(String error){
        view.showError(error);
    }

    public void saveOk() {
        view.saveOk();
    }
}
