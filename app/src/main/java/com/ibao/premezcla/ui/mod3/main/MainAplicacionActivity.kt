package com.ibao.premezcla.ui.mod3.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.ibao.premezcla.BuildConfig
import com.ibao.premezcla.ConectionConfig
import com.ibao.premezcla.R
import com.ibao.premezcla.SharedPreferencesManager
import com.ibao.premezcla.app.AppController
import com.ibao.premezcla.models.Tancada
import com.ibao.premezcla.ui.ActivityPreloader
import com.ibao.premezcla.ui.mod3.tancada.TancadaMuestraActivity
import com.ibao.premezcla.ui.modselector.ModSelectorActivity
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class MainAplicacionActivity : AppCompatActivity() {
    var TAG = this.javaClass.simpleName
    var ctx: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mod3_act_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        eTextSearch = findViewById(R.id.eTextSearch)
        fAButtonClearText = findViewById(R.id.fAButtonClearText)
        rViewTancada = findViewById(R.id.rViewTancada)
        progressBar = findViewById(R.id.progressBar)
        fAButtonClearText?.setOnClickListener(View.OnClickListener { v: View? ->
            eTextSearch?.setText("")
            fAButtonClearText?.setVisibility(View.INVISIBLE)
        })
        tancadaList = ArrayList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean { // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.mod3_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean { // Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.change_module) {
            SharedPreferencesManager.clearMode(baseContext)
            startActivity(Intent(this@MainAplicacionActivity, ModSelectorActivity::class.java))
            return true
        }

        if (id == R.id.action_version) {
            try {
                Toast.makeText(baseContext, "Versión " + BuildConfig.VERSION_NAME + " code." + BuildConfig.VERSION_CODE /*+" db."+ ConexionSQLiteHelper.VERSION_DB*/, Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(baseContext, e.toString(), Toast.LENGTH_LONG).show()
            }
            return true
        }
        return if (id == R.id.action_logout) {
             SharedPreferencesManager.deleteUser(baseContext)
            startActivity(Intent(this, ActivityPreloader::class.java))
            Toast.makeText(baseContext,"Cerrando Sesión",Toast.LENGTH_SHORT).show()
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        val text = eTextSearch!!.text.toString()
        consultarTancadas(text)
    }

    private fun cargarData() {
        progressBar!!.visibility = View.GONE
        rViewAdapterTancadas = RViewAdapterListPasajeros(ctx, tancadaList)
        rViewAdapterTancadas!!.setOnClicListener { view: View? ->
            val tancada = rViewAdapterTancadas!!.getItem(rViewTancada!!.getChildAdapterPosition(view!!))
            val i = Intent(this@MainAplicacionActivity, TancadaMuestraActivity::class.java)
            i.putExtra("extra_id_tancada", tancada.id)
            startActivity(i)
        }
        rViewTancada!!.adapter = rViewAdapterTancadas
        eTextSearch!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                AppController.getInstance().cancelAllPendingRequests()
            }

            @SuppressLint("RestrictedApi")
            override fun afterTextChanged(s: Editable) {
                rViewTancada!!.adapter = null
                val text = eTextSearch!!.text.toString()
                consultarTancadas(text)
                if (text.length > 0) {
                    fAButtonClearText!!.visibility = View.VISIBLE
                } else {
                    fAButtonClearText!!.visibility = View.INVISIBLE
                }
            }
        })
    }

    fun requestAllData(text: String) {
        val jsonObjReq: StringRequest = object : StringRequest(Method.GET,
                ConectionConfig.GET_TANCADA + "?name=" + text, Response.Listener { response: String -> onResponseAllData(response) }, Response.ErrorListener { error: VolleyError -> onError(error) }
        ) {
            override fun getParams(): Map<String, String> {
                return HashMap()
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers: MutableMap<String, String> = HashMap()
                headers["Content-Type"] = "application/x-www-form-urlencoded"
                return headers
            }
        }
        AppController.getInstance().addToRequestQueue(jsonObjReq)
    }

    private fun onError(error: VolleyError) {
        Log.e(TAG, error.toString())
        showError(error.toString())
        error.printStackTrace()
    }

    private fun showError(toString: String?) {
        Toast.makeText(this, toString, Toast.LENGTH_SHORT).show()
    }

    private fun onResponseAllData(response: String) {
        Log.d(TAG, "resp:$response")
        try {
            val main = JSONObject(response)
            val data = main.getJSONArray("data")
            val tancadaList: MutableList<Tancada> = ArrayList()
            for (i in 0 until data.length()) {
                val jsonOrden = data.getJSONObject(i)
                Log.i(TAG, "**")
                Log.i(TAG, jsonOrden.toString())
                val tancada = Gson().fromJson(jsonOrden.toString(), Tancada::class.java)
                val json = Gson().toJson(tancada)
                Log.i(TAG, json)
                Log.i(TAG, "**")
                tancadaList.add(tancada)
            }
            showTancadaData(tancadaList)
            Log.d(TAG, "done" + data.length())
        } catch (e: JSONException) {
            showError(e.message)
        }
    }

    private fun showTancadaData(_tancadaList: List<Tancada>) {
        tancadaList = _tancadaList
        cargarData()
    }

    fun consultarTancadas(text: String) {
        progressBar!!.visibility = View.VISIBLE
        Log.d(TAG, "consultarTancadas()")
        requestAllData(text)
    }

    override fun onBackPressed() {

    }

    companion object {
        var eTextSearch: EditText? = null
        var fAButtonClearText: FloatingActionButton? = null
        var rViewTancada: RecyclerView? = null
        var rViewAdapterTancadas: RViewAdapterListPasajeros? = null
        var tancadaList: List<Tancada>? = null
        var progressBar: ProgressBar? = null
    }
}