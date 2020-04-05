package com.ibao.premescla.ui.mod2.select

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ibao.premescla.R
import com.ibao.premescla.models.Conductor
import com.ibao.premescla.models.Fumigadora
import com.ibao.premescla.models.Tancada
import com.ibao.premescla.models.Tractor
import com.ibao.premescla.ui.mod2.main.MainMezclaActivity
import kotlinx.android.synthetic.main.mod2_act_select.*
import java.util.*

class SelectAplicatorsActivity : AppCompatActivity() {


    val presenter= SelectorPresenter(this@SelectAplicatorsActivity)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mod2_act_select)
        val TANCADA = intent.extras?.getSerializable("tancada") as Tancada

        asa_tViewNTancada.text = "${TANCADA.nroTancada}";
        asa_tViewLote.text= "${TANCADA.nroTancada}"
        asa_tViewConductividad.text = "" + if (TANCADA.conductividad.toFloat() == 0f) "sin editar" else TANCADA.conductividad
        asa_tViewPh.text = "" + if (TANCADA.ph.toFloat() == 0f) "sin editar" else TANCADA.ph
        presenter.requestAllData(TANCADA.id)

        btnSave.setOnClickListener {

            val tracPos = spnTractor.selectedItemPosition
            TANCADA.idTractor= tractorList[tracPos].id

            val condPos = spnConductor.selectedItemPosition
            TANCADA.idConductor= conductorList[condPos].id

            val fumPos = spnFumigadora.selectedItemPosition
            TANCADA.idFumigadora= conductorList[fumPos].id

            presenter.save(TANCADA)
        }
    }

    val TAG = SelectAplicatorsActivity::class.java.simpleName

    var  conductorList :List<Conductor> = ArrayList();
    var  tractorList :List<Tractor> = ArrayList();
    var  fumigadoraList :List<Fumigadora> = ArrayList();
    fun showConductores(conductores: List<Conductor>) {
        conductorList = conductores;
        Log.i(TAG,"tamaño condyctoires ${conductores.size}")
        val adapter = ArrayAdapter<Any?>(baseContext, android.R.layout.simple_spinner_item, conductorList.map { it.nombreConductor })
        spnConductor.adapter = adapter
        spnTractor.adapter = null
        spnFumigadora.adapter = null
        spnConductor?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val conductorSelected = conductorList[position]
                showTractores(conductorSelected.tractores)
                showFumigadoras(conductorSelected.fumigadoras)
                Log.i(TAG,"tractores size: ${conductorSelected.tractores}")
                Log.i(TAG,"fumigadoras size: ${conductorSelected.fumigadoras}")
            }
        }
        val conductorSelected = conductorList[spnConductor.selectedItemPosition]
        showTractores(conductorSelected.tractores)
        showFumigadoras(conductorSelected.fumigadoras)
    }


    fun showTractores(tractores: List<Tractor>) {
        tractorList = tractores
        val adapter = ArrayAdapter<Any?>(baseContext, android.R.layout.simple_spinner_item, tractorList.map { it.nombreTractor })
        spnTractor.adapter = adapter
    }


    fun showFumigadoras(fumigadoras: List<Fumigadora>) {
        fumigadoraList = fumigadoras
        val adapter = ArrayAdapter<Any?>(baseContext, android.R.layout.simple_spinner_item, fumigadoraList.map { it.nombreFumigadora })
        spnFumigadora.adapter = adapter
    }

    fun showError(error: String) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
    }

    fun saveOk() {
        val i = Intent(this, MainMezclaActivity::class.java)
        startActivity(i)
        this.finish()
    }

}
