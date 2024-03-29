package com.ibao.premezcla.ui.mod1.views.fragment.tancada

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.ibao.premezcla.R
import com.ibao.premezcla.models.ProductoPesado
import com.ibao.premezcla.models.Recipe
import com.ibao.premezcla.models.Tancada
import com.ibao.premezcla.ui.mod1.presenters.TancadaFragmentPresenter
import com.ibao.premezcla.utils.PrintQR
import kotlinx.android.synthetic.main.mod1_frag_tancada.*
import kotlinx.android.synthetic.main.mod1_frag_tancada.recyclerView

class TancadaFragment : Fragment() {


    private var presenter : TancadaFragmentPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.mod1_frag_tancada, container, false)
    }
    lateinit var tancada : Tancada
    val recipe : Recipe  by lazy {  arguments!!.getSerializable("recipe") as Recipe }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tancada = arguments!!.getSerializable("tancada") as Tancada
        presenter = TancadaFragmentPresenter(this,tancada.id)
        atancada_swiperefresh!!.setOnRefreshListener {
            requestData()
        }
        requestData()

        btnNext.setOnClickListener {
            if(recipe.ordenDetalleList.size == tancada.productosPesados.size) {
                //Toast.makeText(this,"imprimir",Toast.LENGTH_SHORT).show()
                PrintQR.printQR(tancada,recipe)
            }else{
                requestNextPPesado()
            }
        }
        navController = Navigation.findNavController(view!!)
    }

    override fun onStart() {
        super.onStart()
        requestData()
    }
    private fun requestNextPPesado(){
        presenter!!.requestNewPPesado(tancada.id)
    }
    private fun requestData(){
        presenter!!.requestAllData()
        atancada_swiperefresh!!.isRefreshing = true

        if(recipe.ordenDetalleList.size == tancada.productosPesados.size) {
            btnNext.text = "Imprimir"
        }else{
            btnNext.text = "Siguiente Pesaje"
        }
    }
    fun showTancada(_tancada: Tancada) {
        tancada = _tancada;
        atancada_tViewNPPesadoAll.text= ""+recipe.ordenDetalleList.size
        atancada_tViewNPPesado.text = ""+_tancada.productosPesados.size
        atancada_swiperefresh!!.isRefreshing= false

        val adapter = RViewAdapterListProductoPesado(activity, _tancada.productosPesados)
        adapter.setOnClicListener {


        }
        recyclerView.adapter = adapter

        if(recipe.ordenDetalleList.size == _tancada.productosPesados.size) {
            btnNext.text = "Imprimir"
        }else{
            btnNext.text = "Siguiente Pesaje"
        }
    }

    fun showError(error: String) {
        Toast.makeText(activity,error, Toast.LENGTH_LONG).show()
    }
    var navController : NavController? = null
    fun goToActivityPPesado(ppesado: ProductoPesado, actual: Int, all: Int) {

        val bundle = bundleOf(
                "ppesado" to ppesado,
                "pos" to actual,
                "all" to all)
        navController!!.navigate(R.id.action_tancadaFragment_to_PPesadoFragment,bundle)

    }
}
