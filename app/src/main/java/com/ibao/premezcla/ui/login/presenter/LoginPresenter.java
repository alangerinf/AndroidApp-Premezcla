package com.ibao.premezcla.ui.login.presenter;

public interface LoginPresenter {
    void signIn(String user, String password);//Interactor
    void loginSuccess();
    void loginError(String error);


}
