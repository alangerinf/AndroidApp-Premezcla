package com.ibao.premescla.ui.mod2.select_aplicatiors;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.ibao.premescla.ConectionConfig;
import com.ibao.premescla.app.AppController;
import com.ibao.premescla.models.Conductor;
import com.ibao.premescla.models.Tancada;
import com.ibao.premescla.models.Tractor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectorInteractor {

    private String TAG = SelectorInteractor.class.getSimpleName();

    private SelectorPresenter presenter;

    public SelectorInteractor(SelectorPresenter presenter) {
        this.presenter = presenter;
    }

    public void requestAllData(int id){
        String url = ConectionConfig.GET_TRACTOR_CONDUCTOR+"?id="+id;
        Log.d(TAG,"URL:"+url);
        StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                url,
                this::onResponseAllData, this::onError
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



    public void save(Tancada tancada){
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                ConectionConfig.POST_TANCADA+"?action=mezcla",
                this::saveData, error -> onError(error)
        ){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("data",new Gson().toJson(tancada));
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

    private void saveData(String response){
        Log.d(TAG,"resp:"+response);

        try {
            boolean succcess = new JSONObject(response).getInt("succcess")>0;
            if(succcess){
                presenter.saveOk();
            }else {
                presenter.showError("no se pudo guardar");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void onError(VolleyError error){
        Log.e(TAG,error.toString());
        presenter.showError(error.toString());
        error.printStackTrace();
    }

    private void onResponseAllData(String response) {
        Log.d(TAG, "resp:" + response);
        try {
            JSONObject main = new JSONObject(response).getJSONObject("data");
            JSONArray jsonTractores = main.getJSONArray("tractores");
            JSONArray jsonConductores = main.getJSONArray("conductores");
            Log.d(TAG, "jsonTractores:" + jsonTractores.toString());
            Log.d(TAG, "jsonConductores:" + jsonConductores.toString());
            List<Tractor> tractorList = new ArrayList<>();
            List<Conductor> conductorList = new ArrayList<>();
                for(int i=0;i< jsonTractores.length();i++){
                    JSONObject jsonTractor = jsonTractores.getJSONObject(i);
                    Log.i(TAG,"**");
                    Log.i(TAG,"trac:"+jsonTractor.toString());
                    Tractor tractor = new Gson().fromJson(jsonTractor.toString(),Tractor.class);
                    String json =  new Gson().toJson(tractor);
                    Log.i(TAG,json);
                    Log.i(TAG,"**");
                    tractorList.add(tractor);
            }
            presenter.showTractores(tractorList);

            for(int i=0;i< jsonConductores.length();i++){
                JSONObject jsonConductor = jsonConductores.getJSONObject(i);
                Log.i(TAG,"**");
                Log.i(TAG,jsonConductor.toString());
                Conductor conductor = new Gson().fromJson(jsonConductor.toString(),Conductor.class);
                String json =  new Gson().toJson(conductor);
                Log.i(TAG,json);
                Log.i(TAG,"**");
                conductorList.add(conductor);
            }
            presenter.showConductores(conductorList);
        } catch (JSONException e) {
            Log.e(TAG,e.toString());
            presenter.showError(e.getMessage());
        }
    }
}
