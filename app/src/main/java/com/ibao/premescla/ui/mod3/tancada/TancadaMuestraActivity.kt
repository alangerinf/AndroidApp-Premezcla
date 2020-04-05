package com.ibao.premescla.ui.mod3.tancada

        import android.content.Intent
                import android.os.Bundle
                import android.widget.Toast
                import androidx.appcompat.app.AppCompatActivity
                import com.ibao.premescla.R
                import com.ibao.premescla.models.Tancada
                import com.ibao.premescla.ui.mod3.muestra_post.SelectorActivity
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

        button.setOnClickListener {
            presenter.requestUpdateEstado(tancada)
        }
    }

    fun showError(error: String) {
        Toast.makeText(this@TancadaMuestraActivity,error,Toast.LENGTH_SHORT).show()
    }
}
