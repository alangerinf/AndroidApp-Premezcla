package com.ibao.premezcla.ui.mod3.muestra_post;


import com.ibao.premezcla.models.Muestra;

public class SelectorPresenter {

    private SelectorInteractor interactor;
    private SelectorActivity view;

    public SelectorPresenter(SelectorActivity activity){
        this.interactor = new SelectorInteractor(this);
        this.view = activity;

    }


    String TAG = "TancadaMuestraPresenter.tk";
    public void requestPushMuestra(Muestra muestra){
        this.interactor.requestPushMuestra(muestra);
    }

    public void respSuccess(){
        view.showSuccess();
    }
    public void respFailed(String fail){
        view.showError(fail);
    }

    public void showError(String error){
        view.showError(error);
    }

}
