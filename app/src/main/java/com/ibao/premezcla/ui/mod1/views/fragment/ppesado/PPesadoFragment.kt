package com.ibao.premezcla.ui.mod1.views.fragment.ppesado

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.ibao.premezcla.R
import com.ibao.premezcla.models.ProductoPesado
import com.ibao.premezcla.models.Recipe
import com.ibao.premezcla.models.Tancada
import com.ibao.premezcla.ui.mod1.presenters.ProductoPesadoFragmentPresenter
import com.ibao.premezcla.utils.CommunicateViewModel
import com.ibao.premezcla.utils.Utilities
import kotlinx.android.synthetic.main.mod1_frag_ppesado.*
import kotlinx.android.synthetic.main.mod1_frag_tancada.btnNext
import java.lang.Exception
import kotlin.math.absoluteValue

class PPesadoFragment : Fragment() {


    private var presenter : ProductoPesadoFragmentPresenter? = null
    lateinit var  ppesado:ProductoPesado
    var PPESADO_SAVE: ProductoPesado = ProductoPesado()
    var pos:Int=0;//   by lazy{ bundle!!.getInt("pos")  }
    var all:Int=0;//   by lazy{ bundle!!.getInt("all") }

    private var viewModelComunicate: CommunicateViewModel? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.mod1_frag_ppesado, container, false)
    }
    lateinit var tancada : Tancada
    val recipe : Recipe  by lazy {  arguments!!.getSerializable("recipe") as Recipe }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ProductoPesadoFragmentPresenter(this)
        navController = Navigation.findNavController(view!!)
        ppesado= arguments!!.getSerializable("ppesado") as ProductoPesado
        pos= arguments!!.getInt("pos")
        all=arguments!!.getInt("all")

        viewModelComunicate = activity?.run {
            ViewModelProviders.of(this)[CommunicateViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        viewModelComunicate!!.messages.observe(viewLifecycleOwner, Observer { message: String? ->
            /*
            if (TextUtils.isEmpty(message)) {
                viewModelComunicate!!.setMessages("No hay Mensajes")
            }
             */

            val peso = convertPesoToGr(ppesado.dosis,ppesado.units)
            var pesoReal = 0
            try {
                if(message!!.length>1 ){
                    pesoReal= message!!.substring(0,message!!.length-1).toInt()
                }
            }catch (ex : Exception){

            }

            val dif = (peso*1.0)/ppesado.toleranceRate.absoluteValue

            if( (peso - pesoReal).absoluteValue <= dif ){

                btnNext.isEnabled = true
                btnNext.alpha = 1f
            }else{
                btnNext.isEnabled = false
                btnNext.alpha = 0.5f
            }
            //quiere decir q aun no se guarda y debe actualizar
            if(PPESADO_SAVE.cantidadPesada==0f){
                Log.d(TAG,"actualiza p real")
                tViewPesoReal!!.text = message
            }else// si ya se guardo el peso debe dejar de actualizar
            {
                Log.d(TAG,"actualiza p real")
                btnNext.isEnabled = true
                btnNext.alpha = 1f
                if(all == pos){
                    navController!!.popBackStack()
                }
                btnNext.text= "Ok"
            }
        })
        btnNext.setOnClickListener {
            if(btnNext.text=="Lecturar"){
                PPESADO_SAVE = ppesado
                PPESADO_SAVE.fechaPesada = Utilities.getDateTime()
                PPESADO_SAVE.cantidadPesada = tViewPesoReal.text.toString().substring(0,tViewPesoReal.text.toString().length-1).toFloat()
                presenter!!.requestPosPPesado(PPESADO_SAVE) //subiendo el resultado
            }else
            {//siguiente
                presenter!!.requestNewPPesado(ppesado.idTancada)
            }
        }
        btnBack.setOnClickListener {
            refresh()
        }
        refresh()
    }
    var navController : NavController? = null
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
    fun convertPesoToGr( peso: Float, units: String): Int {
        Log.i(TAG,"convertPesoToGr( $peso, $units)")
        var pesoResponse = peso
        Log.d(TAG, "units=$units")
        when(units){
            "KGS"  -> pesoResponse *= 1000
            "LTS"  -> pesoResponse *= 1000
        }
        return pesoResponse.toInt()
    }

    fun showError(error: String) {
        Toast.makeText(activity,error,Toast.LENGTH_LONG).show()
        refresh()
    }

    fun saveOk() {
        try{
            Toast.makeText(activity,"Guardado",Toast.LENGTH_LONG).show()
            btnBack.alpha=0.5f
            btnBack.isEnabled=false
        }catch (e : Exception){

        }

    }

    fun goToNext(_ppesado: ProductoPesado, _actual: Int, _all: Int) {

        Log.d(TAG,"goToNext($_ppesado: ProductoPesado, $_actual: Int, $_all: Int)")
        ppesado = _ppesado
        pos = _actual
        all = _all
        refresh()
    }
}
