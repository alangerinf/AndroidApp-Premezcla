package com.ibao.premescla.ui.login.interactor;


import com.ibao.premescla.models.User;

public interface LoginInteractor {

    void signIn(String user, String password);

    void signSuccess(User userTemp);

    void signError(String mensajeRespuesta);
}
