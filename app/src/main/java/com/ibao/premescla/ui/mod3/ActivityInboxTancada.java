package com.ibao.premescla.ui.mod3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.ibao.premescla.ConectionConfig;
import com.ibao.premescla.R;
import com.ibao.premescla.app.AppController;
import com.ibao.premescla.models.Tancada;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ActivityInboxTancada extends AppCompatActivity {

    static EditText eTextSearch;
    static FloatingActionButton fAButtonClearText;

    static RecyclerView rViewTancada;
    static RViewAdapterListPasajeros rViewAdapterTancadas;
    static List<Tancada> tancadaList;
    static ProgressBar progressBar;

    String TAG = this.getClass().getSimpleName();

    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_tancada);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        eTextSearch = findViewById(R.id.eTextSearch);
        fAButtonClearText = findViewById(R.id.fAButtonClearText);
        rViewTancada = findViewById(R.id.rViewTancada);
        progressBar = findViewById(R.id.progressBar);
        fAButtonClearText.setOnClickListener(v -> {
            eTextSearch.setText("");
            fAButtonClearText.setVisibility(View.INVISIBLE);
        });
        tancadaList = new ArrayList<>();
        consultarTancadas("");
    }



    private void cargarData(){
        progressBar.setVisibility(View.GONE);
        rViewAdapterTancadas = new RViewAdapterListPasajeros(ctx, tancadaList);
        rViewAdapterTancadas.setOnClicListener(view -> {
            Intent i = new Intent(ActivityInboxTancada.this,TancadaMuestraActivity.class);
            rViewAdapterTancadas.getItemId(rViewTancada.getChildAdapterPosition(view));
            startActivity(i);
        });

        rViewTancada.setAdapter(rViewAdapterTancadas);

        eTextSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                AppController.getInstance().cancelAllPendingRequests();
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void afterTextChanged(Editable s) {
                rViewTancada.setAdapter(null);
                String text = eTextSearch.getText().toString();

                consultarTancadas(text);

                if(text.length()>0){
                    fAButtonClearText.setVisibility(View.VISIBLE);
                }else {
                    fAButtonClearText.setVisibility(View.INVISIBLE);
                }
            }

        });
    }

    public void requestAllData(String text){
        StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                ConectionConfig.GET_TANCADA+"?name="+text,
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
        showError(error.toString());
        error.printStackTrace();
    }

    private void showError(String toString) {
        Toast.makeText(this,toString,Toast.LENGTH_SHORT).show();
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
            showTancadaData(tancadaList);
            Log.d(TAG, "done"+data.length());
        } catch (JSONException e) {
            showError(e.getMessage());
        }
    }

    private void showTancadaData(List<Tancada> _tancadaList) {
        tancadaList = _tancadaList;
        cargarData();
    }

    public void consultarTancadas(String text){
        progressBar.setVisibility(View.VISIBLE);
        Log.d(TAG,"consultarTancadas()");
        requestAllData(text);
    }
}

