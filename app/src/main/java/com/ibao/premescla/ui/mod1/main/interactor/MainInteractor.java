package com.ibao.premescla.ui.mod1.main.interactor;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.ibao.premescla.ConectionConfig;
import com.ibao.premescla.app.AppController;
import com.ibao.premescla.models.Orden;
import com.ibao.premescla.ui.mod1.main.presenters.MainPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainInteractor {

    private String TAG = MainInteractor.class.getSimpleName();

    private MainPresenter presenter;

    public MainInteractor(MainPresenter presenter) {
        this.presenter = presenter;
    }

    public void requestAllData(){
        Log.d(TAG,"requestAllData()"+ConectionConfig.GET_ORDER);
        StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                ConectionConfig.GET_ORDER,

                this::onResponse, error -> onError(error)

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

    private void onError(VolleyError error){
        Log.e(TAG,error.toString());
        presenter.showError(error.toString());
        error.printStackTrace();
    }

    private void onResponse(String response) {
        Log.d(TAG, "resp:" + response);


        try {
            JSONObject main = new JSONObject(response);
            JSONArray data = main.getJSONArray("data");
            List<Orden> ordenList = new ArrayList<>();
            for(int i=0;i< data.length();i++){
                JSONObject jsonOrden = data.getJSONObject(i);
                Log.i(TAG,"**");
                Log.i(TAG,jsonOrden.toString());
                Orden orden = new Gson().fromJson(jsonOrden.toString(),Orden.class);
                String json =  new Gson().toJson(orden);
                Log.i(TAG,json);
                Log.i(TAG,"**");
                ordenList.add(orden);
            }
                presenter.showOrdenList(ordenList);
            Log.d(TAG, "done"+data.length());
        } catch (JSONException e) {
            presenter.showError(e.getMessage());
        }

    }
}
