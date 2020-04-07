package com.ibao.premezcla.ui.mod1.interactor;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.ibao.premezcla.ConectionConfig;
import com.ibao.premezcla.SharedPreferencesManager;
import com.ibao.premezcla.app.AppController;
import com.ibao.premezcla.models.ProductoPesado;
import com.ibao.premezcla.ui.mod1.presenters.ProductoPesadoFragmentPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoPesadoFragmentInteractor {

    private String TAG = ProductoPesadoFragmentInteractor.class.getSimpleName();

    private ProductoPesadoFragmentPresenter presenter;

    public ProductoPesadoFragmentInteractor(ProductoPesadoFragmentPresenter presenter) {
        this.presenter = presenter;
    }

    public void requestPostProductoPesado(ProductoPesado productoPesado){
        Log.d(TAG,"requestPostProductoPesado("+productoPesado.getFechaPesada()+")");
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                ConectionConfig.POST_PPESADO,
                this::onResponsePostPPesado, error -> onError(error)
        ){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                productoPesado.setUsuario(SharedPreferencesManager.getUser(AppController.getInstance().getBaseContext()).getId());
                String data = new Gson().toJson(productoPesado);
                Log.d(TAG,"data = "+data);

                 params.put("data",data);
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

    public void requestNewPPesado(int id){
        Log.d(TAG,"requestNewPPesado("+id+")");
        StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                ConectionConfig.GET_NEXTPPESADO+"?id="+id,
                this::onResponseGetNextPPesado, error -> onError(error)
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

    private void onResponsePostPPesado(String response) {
        Log.d(TAG, "resp:" + response);
        try {
            JSONObject main = new JSONObject(response);
            boolean success = main.getInt("success")>0;

            if(success){
                presenter.saveOk();
            }else {
                presenter.showError("No se pudo guardar");
            }

        } catch (JSONException e) {
            Log.e(TAG,"jsonError: "+e.getMessage());
            presenter.showError("jsonError: "+e.getMessage());
        }
    }

    private void onResponseGetNextPPesado(String response) {
        Log.d(TAG, "onResponseGetNextPPesado resp:" + response);
        try {
            JSONObject main = new JSONObject(response);
            JSONObject jsonPPesado = main.getJSONObject("data");
            String pos = main.getString("pos");
            boolean success = main.getInt("success")>0;
            List<ProductoPesado> ppesadoList = new ArrayList<>();
            if(success){
                    Log.i(TAG,"**");
                    Log.i(TAG,jsonPPesado.toString());
                    ProductoPesado ppesado = new Gson().fromJson(jsonPPesado.toString(),ProductoPesado.class);
                    String json =  new Gson().toJson(ppesado);
                    Log.i(TAG,json);
                    Log.i(TAG,"**");
                    ppesadoList.add(ppesado);

                String[] posArray = pos.split("-");
                presenter.goToAddPPesado(ppesadoList.get(0), Integer.parseInt(posArray[0]),Integer.parseInt(posArray[1]));
                Log.d(TAG, "done"+jsonPPesado.length());
            }else{
                presenter.showError("La tancada ya esta completa");
            }

        } catch (JSONException e) {
            Log.e(TAG,"jsonError: "+e.getMessage());
            presenter.showError("jsonError: "+e.getMessage());
        }
    }


}
