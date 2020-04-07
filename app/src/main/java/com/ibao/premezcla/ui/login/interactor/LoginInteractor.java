package com.ibao.premezcla.ui.login.interactor;


import com.ibao.premezcla.models.User;

public interface LoginInteractor {

    void signIn(String user, String password);

    void signSuccess(User userTemp);

    void signError(String mensajeRespuesta);
}
