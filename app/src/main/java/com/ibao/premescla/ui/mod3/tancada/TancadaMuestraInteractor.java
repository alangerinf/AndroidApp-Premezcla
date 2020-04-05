package com.ibao.premescla.ui.mod3.tancada;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.ibao.premescla.ConectionConfig;
import com.ibao.premescla.app.AppController;
import com.ibao.premescla.models.Tancada;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TancadaMuestraInteractor {

    private String TAG = TancadaMuestraInteractor.class.getSimpleName();

    private TancadaMuestraPresenter presenter;

    public TancadaMuestraInteractor(TancadaMuestraPresenter presenter) {
        this.presenter = presenter;
    }


    public void updateEstado(Tancada tancada){
        StringRequest jsonObjReq = new StringRequest(Request.Method.DEPRECATED_GET_OR_POST,
                ConectionConfig.POST_TANCADA+"?action="+ConectionConfig.T_ACTION_APLICACION,
                this::onResponseUpdateEstado, error -> onError(error)
        ){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                String data = new Gson().toJson(tancada);
                Log.i(TAG,"data:"+data);
                params.put("data",data);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> headers = new HashMap<>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void onResponseUpdateEstado(String response) {
        Log.i(TAG, "Update resp:" + response);
        try {
            JSONObject main = new JSONObject(response);
            int success = main.getInt("success");
            if(success>0){
                presenter.respUpdateSuccess();
            }else{
                presenter.respUpdateFailed("No se pudo insertar");
            }
        } catch (JSONException e) {
            presenter.showError(e.getMessage());
        }
    }

    public void requestAllData(int id){
        StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                ConectionConfig.GET_TANCADA+"?id="+id,
                this::onResponseAllData, error -> onError(error)
        ){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
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

    private void onResponseAllData(String response) {
        Log.d(TAG, "resp:" + response);
        try {
            JSONObject main = new JSONObject(response);
            JSONArray data = main.getJSONArray("data");
            List<Tancada> tancadaList = new ArrayList<>();
            for(int i=0;i< data.length();i++){
                JSONObject jsonOrden = data.getJSONObject(i);
                Log.i(TAG,"**");
                Log.i(TAG,jsonOrden.toString());
                Tancada tancada = new Gson().fromJson(jsonOrden.toString(),Tancada.class);
                String json =  new Gson().toJson(tancada);
                Log.i(TAG,json);
                Log.i(TAG,"**");
                tancadaList.add(tancada);
            }
                presenter.showTancadaData(tancadaList.get(0));
            Log.d(TAG, "done"+data.length());
        } catch (JSONException e) {
            presenter.showError(e.getMessage());
        }
    }
}