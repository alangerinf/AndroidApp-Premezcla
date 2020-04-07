package com.ibao.premezcla.ui.mod3.tancada

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ibao.premezcla.R
import com.ibao.premezcla.models.Tancada
import com.ibao.premezcla.ui.mod3.muestra_post.SelectorActivity
import kotlinx.android.synthetic.main.mod3_act_tancada.*

class TancadaMuestraActivity : AppCompatActivity() {

    private val BUNDLE: Bundle by lazy { intent.extras!! }
    private val ID_TANCADA: Int by lazy { BUNDLE.getInt("extra_id_tancada") }
    private val presenter: TancadaMuestraPresenter by lazy { TancadaMuestraPresenter(this@TancadaMuestraActivity,ID_TANCADA)}
    private lateinit var TANCADA: Tancada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mod3_act_tancada)
        loadData()
    }

    fun loadData(){
        presenter.requestAllData()
    }

    fun showTancada(tancada: Tancada) {
        TANCADA = tancada
        ti_tViewStatus.text = "" + TANCADA.estadoTancada
        ti_tViewLoteCode.text = "" + TANCADA.codeLote
        ti_tViewNroTancada.text = "" + TANCADA.nroTancada
        ti_tViewMojamiento.text = "" + TANCADA.mojamiento
        ti_tViewMuestras.text = "" + TANCADA.muestras.size

        val timeStart = TANCADA.fechaInicioAplicacion
        val timeEnd = TANCADA.fechaFinAplicacion

        if (!timeStart.isEmpty()) {
            ti_tViewTimeStart.text =  timeStart
        }else{
            ti_tViewTimeStart.text = "Sin hora de Inicio"
        }
        if (!timeEnd.isEmpty()) {
            ti_tViewTimeEnd.text =  timeEnd
        }else{
            ti_tViewTimeEnd.text = "Sin hora de Termino"
        }
        val conductor = TANCADA.nombreConductor
        val tractor = TANCADA.nombreTractor

        if (!conductor.isEmpty()) {
            ti_tViewConductor.text =  conductor
        }else{
            ti_tViewConductor.text = "Sin Conductor"
        }
        if (!tractor.isEmpty()) {
            ti_tViewTractor.text =  tractor
        }else{
            ti_tViewTractor.text = "Sin Tractor"
        }
        rViewMuestras.adapter= RViewAdapterMuestrasTancada(this@TancadaMuestraActivity,TANCADA.muestras)

        floatingActionButton.setOnClickListener {
            val i = Intent(this,SelectorActivity::class.java)
            i.putExtra("extra_id_tancada", tancada.id)
            startActivity(i)
        }

        val TAG = TancadaMuestraActivity::class.java.simpleName
        button.setOnClickListener {
            Log.d(TAG,tancada.fechaInicioAplicacion)
            Log.d(TAG,tancada.fechaFinAplicacion)
            if(tancada.fechaInicioAplicacion.isEmpty()){
                Log.d(TAG,"1")
                presenter.requestUpdateEstado(tancada)
            }else{
                if(tancada.muestras.size>0 && tancada.fechaFinAplicacion.isEmpty()){
                    Log.d(TAG,"2")
                    presenter.requestUpdateEstado(tancada)
                }else{
                    showError("Ingrese muestras para finalizar")
                }
            }
        }

        button.isEnabled=true
        if(timeStart.isEmpty()){
            floatingActionButton.hide()
            button.text="INICIAR APLICACIÓN"
            button.setBackgroundColor(resources.getColor(R.color.colorAccent))
        }else{
            if(timeEnd.isEmpty()){
                floatingActionButton.show()
                button.text="FINALIZAR APLICACIÓN"
                button.setBackgroundColor(resources.getColor(R.color.red_pastel))
            }else{
                floatingActionButton.hide()
                button.text="APLICACIÓN FINALIZADA"
                button.setBackgroundColor(resources.getColor(R.color.colorDisable))
                
                button.isEnabled=false
            }

        }
    }

    fun showError(error: String) {
        Toast.makeText(this@TancadaMuestraActivity,error,Toast.LENGTH_SHORT).show()
    }
}
