package com.ibao.premezcla.ui.mod1.presenters;

import android.util.Log;

import com.ibao.premezcla.models.Orden;
import com.ibao.premezcla.ui.mod1.interactor.OrdenFragmentInteractor;
import com.ibao.premezcla.ui.mod1.views.fragment.orden.OrdenFragment;

public class OrdenFragmentPresenter {

    private OrdenFragmentInteractor interactor;
    private OrdenFragment view;
    private int id;
    public OrdenFragmentPresenter(OrdenFragment _view, int id){
        this.interactor = new OrdenFragmentInteractor(this);
        this.view = _view;
        this.id = id;
    }

    String TAG = "MainPresenter.tk";
    public void requestAllData(){
        Log.d(TAG,"requestAllData()");
        this.interactor.requestAllData(id);
    }

    public void showOrdenList(Orden orden){
        view.showOrder(orden);
    }
    public void showError(String error){
        view.showError(error);
    }


}
