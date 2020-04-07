package com.ibao.premezcla.ui.mod1.presenters;

import com.ibao.premezcla.models.ProductoPesado;
import com.ibao.premezcla.models.Tancada;
import com.ibao.premezcla.ui.mod1.interactor.TancadaFragmentInteractor;
import com.ibao.premezcla.ui.mod1.views.fragment.tancada.TancadaFragment;


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
