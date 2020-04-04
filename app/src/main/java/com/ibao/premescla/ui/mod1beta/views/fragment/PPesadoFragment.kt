package com.ibao.premescla.ui.mod1beta.views.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.ibao.premescla.R
import com.ibao.premescla.models.ProductoPesado
import com.ibao.premescla.models.Recipe
import com.ibao.premescla.models.Tancada
import com.ibao.premescla.ui.mod1beta.presenters.ProductoPesadoFragmentPresenter
import kotlinx.android.synthetic.main.fragment_ppesado.*
import kotlinx.android.synthetic.main.fragment_tancada.btnNext

class PPesadoFragment : Fragment() {


    private var presenter : ProductoPesadoFragmentPresenter? = null
    lateinit var  ppesado:ProductoPesado
    var PPESADO_SAVE: ProductoPesado = ProductoPesado()
    var pos:Int=0;//   by lazy{ bundle!!.getInt("pos")  }
    var all:Int=0;//   by lazy{ bundle!!.getInt("all") }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ppesado, container, false)
    }
    lateinit var tancada : Tancada
    val recipe : Recipe  by lazy {  arguments!!.getSerializable("recipe") as Recipe }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ProductoPesadoFragmentPresenter(this)

        ppesado= arguments!!.getSerializable("ppesado") as ProductoPesado
        pos= arguments!!.getInt("pos")
        all=arguments!!.getInt("all")

    }

    private fun refresh(){
        PPESADO_SAVE.cantidadPesada= 0f
        Log.d(TAG,"pesada ->"+PPESADO_SAVE.cantidadPesada)
        btnNext.text= "Lecturar"
        tViewStep.text = ""+pos

        tViewTotalStep.text = ""+all
        tViewPesoRequest.text = ""+convertPesoToGr(ppesado.dosis,ppesado.units)+"g"
        tViewProductName.text = ""+ppesado.productName
        tViewProductActive.text = ""+ppesado.productActive
        tViewMessageTolerance.text =  getString(R.string.torelancia_messagge) +" "+ppesado.toleranceRate+"%"

        btnNext.isEnabled = false
        btnNext.alpha = 0.5f
    }
    val TAG = PPesadoFragment::class.java.simpleName
    fun convertPesoToGr(peso: Float, untis: String): Int {

        var pesoResponse = peso
        Log.d(TAG, "units=$untis")
        when(untis){
            "KGS"  -> pesoResponse *= 1000
        }
        return pesoResponse.toInt()
    }

    fun showError(error: String) {
        Toast.makeText(activity,error,Toast.LENGTH_LONG).show()
        refresh()
    }
    fun saveOk() {
        Toast.makeText(activity,"Guardado",Toast.LENGTH_LONG).show()
        btnBack.alpha=0.5f
        btnBack.isEnabled=false
    }
    fun goToNext(_ppesado: ProductoPesado, _actual: Int, _all: Int) {
        //     val intent : Intent = Intent(this,ActivityProductoPesado::class.java)
        ppesado = _ppesado
        pos = _actual
        all = _all
        refresh()
    }
}
