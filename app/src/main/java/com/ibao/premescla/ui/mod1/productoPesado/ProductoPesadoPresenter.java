package com.ibao.premescla.ui.mod1.productoPesado;

import com.ibao.premescla.models.ProductoPesado;


public class ProductoPesadoPresenter {

    private ProductoPesadoInteractor interactor;
    private ActivityProductoPesado view;

    public ProductoPesadoPresenter(ActivityProductoPesado activity){
        this.interactor = new ProductoPesadoInteractor(this);
        this.view = activity;

    }


    String TAG = ProductoPesadoPresenter.class.getSimpleName();


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
