package com.ibao.premezcla.ui.mod1.presenters;

import com.ibao.premezcla.models.ProductoPesado;
import com.ibao.premezcla.ui.mod1.interactor.ProductoPesadoFragmentInteractor;
import com.ibao.premezcla.ui.mod1.views.fragment.ppesado.PPesadoFragment;


public class ProductoPesadoFragmentPresenter {

    private ProductoPesadoFragmentInteractor interactor;
    private PPesadoFragment view;

    public ProductoPesadoFragmentPresenter(PPesadoFragment _view){
        this.interactor = new ProductoPesadoFragmentInteractor(this);
        this.view = _view;
    }

    String TAG = ProductoPesadoFragmentPresenter.class.getSimpleName();

    public void goToAddPPesado(ProductoPesado ppesado,int actual, int all){
        view.goToNext(ppesado,actual,all);
    }
    public void showError(String error){
        view.showError(error);
    }
    public void requestNewPPesado(int id){ this.interactor.requestNewPPesado(id);}

    public void requestPosPPesado(ProductoPesado productoPesado){
        interactor.requestPostProductoPesado(productoPesado);
    }

    public void saveOk(){
        view.saveOk();
    }

}
