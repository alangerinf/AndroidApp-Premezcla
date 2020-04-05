package com.ibao.premescla.ui.mod1beta.presenters;

import com.ibao.premescla.models.ProductoPesado;
import com.ibao.premescla.models.Tancada;
import com.ibao.premescla.ui.mod1beta.interactor.TancadaFragmentInteractor;
import com.ibao.premescla.ui.mod1beta.views.fragment.tancada.TancadaFragment;


public class TancadaFragmentPresenter {

    private TancadaFragmentInteractor interactor;
    private TancadaFragment view;
    private int id;



    public TancadaFragmentPresenter(TancadaFragment view_, int id){
        this.interactor = new TancadaFragmentInteractor(this);
        this.view = view_;
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
