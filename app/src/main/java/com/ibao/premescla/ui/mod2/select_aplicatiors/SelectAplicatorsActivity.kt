package com.ibao.premescla.ui.mod2.select_aplicatiors

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ibao.premescla.R
import com.ibao.premescla.models.Conductor
import com.ibao.premescla.models.Tancada
import com.ibao.premescla.models.Tractor
import com.ibao.premescla.ui.mod2.main_scanner.MainMezclaActivity
import kotlinx.android.synthetic.main.activity_select_aplicators.*
import java.util.*

class SelectAplicatorsActivity : AppCompatActivity() {


    val presenter= SelectorPresenter(this@SelectAplicatorsActivity)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_aplicators)
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

            presenter.save(TANCADA)
        }
    }


    var  conductorList :List<Conductor> = ArrayList();
    fun showConductores(conductores: List<Conductor>) {
        conductorList = conductores;
        val adapter = ArrayAdapter<Any?>(baseContext, android.R.layout.simple_spinner_item, conductorList.map { it.name })
        spnConductor.adapter = adapter
    }

    var  tractorList :List<Tractor> = ArrayList();
    fun showTractores(tractores: List<Tractor>) {
        tractorList = tractores
        val adapter = ArrayAdapter<Any?>(baseContext, android.R.layout.simple_spinner_item, tractorList.map { it.placa })
        spnTractor.adapter = adapter
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
