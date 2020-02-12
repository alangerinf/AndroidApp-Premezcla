package com.ibao.premescla.ui.main.interactor;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.ibao.premescla.ConectionConfig;
import com.ibao.premescla.app.AppController;
import com.ibao.premescla.ui.main.presenters.MainPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainInteractor {

    private String TAG = MainInteractor.class.getSimpleName();

    private MainPresenter presenter;

    public MainInteractor(MainPresenter presenter) {
        this.presenter = presenter;
    }

    public void requestAllData(){
        Log.d(TAG,"requestAllData()"+ConectionConfig.GET_ORDER);
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                ConectionConfig.GET_ORDER,
                /**
                 * {"success":1,"login":[{"id":2,"user":"21825288","idSupervisor":"66","idFundo":"2,3","name":"RAMOS AGUILAR, ANGEL
                 * JOSE"}]}
                 */
                /*
                    try {

                        JSONObject data = new JSONObject(response);

                        int success = data.getInt("success");
                        if (success == 1) {
                            Log.d(TAG,"success1");
                            Log.d(TAG,user);
                            Log.d(TAG,password);

                            JSONArray main = data.getJSONArray("data");
                            if(main.length()>0){
                                JSONObject usuario = main.getJSONObject(0);//traerse el primer usuario
                                User userTemp = new User();
                                userTemp.setId(Integer.parseInt(usuario.getString("id")));
                                userTemp.setUser(user);
                                userTemp.setPassword(password);
                                userTemp.setName(usuario.getString("name"));
                                userTemp.setFundos(usuario.getString("idFundo"));
                                userTemp.setIdSupervisor(usuario.getInt("idSupervisor"));
                                userTemp.setPermisoManual(usuario.getInt("permisoManual")>0);
                                presenter.showOrdenList(userTemp);
                            }else {
                                presenter.showError("Lista de usuarios vacia");
                            }


                        }else {
                            presenter.showError("Verfique sus Credenciales");
                        }

                    } catch (JSONException e) {
                        presenter.showError(e.toString());
                        e.printStackTrace();
                    }

                     */
                this::onResponse, error -> {

            Log.e(TAG,error.toString());
            presenter.showError(error.toString());
            error.printStackTrace();
        }

        ){

            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
               // params.put(POST_USER, user);
               // params.put(POST_PASS, password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };


        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }

    private void onResponse(String response) {
        Log.d(TAG, "resp:" + response);
        presenter.showError(response);
    }
}
