package com.ibao.premescla.ui.mod1beta.presenters;

import android.util.Log;

import com.ibao.premescla.models.Orden;
import com.ibao.premescla.ui.mod1.orden.ActivityOrden;
import com.ibao.premescla.ui.mod1.orden.OrdenInteractor;
import com.ibao.premescla.ui.mod1beta.interactor.OrdenFragmentInteractor;
import com.ibao.premescla.ui.mod1beta.views.fragment.OrdenFragment;

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
