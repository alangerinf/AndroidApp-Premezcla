package com.ibao.premescla.ui.mod1beta.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ibao.premescla.R
import com.ibao.premescla.models.Orden
import com.ibao.premescla.models.Recipe
import com.ibao.premescla.ui.mod1beta.presenters.OrdenFragmentPresenter
import kotlinx.android.synthetic.main.fragment_orden.*
import kotlinx.android.synthetic.main.fragment_orden.aorden_swiperefresh
import kotlinx.android.synthetic.main.fragment_orden.aorden_tViewDateTime
import kotlinx.android.synthetic.main.fragment_orden.aorden_tViewEmpresa
import kotlinx.android.synthetic.main.fragment_orden.aorden_tViewNTankAll
import kotlinx.android.synthetic.main.fragment_orden.aorden_tViewNTankComplete
import kotlinx.android.synthetic.main.fragment_orden.aorden_tViewnNOrden
import kotlinx.android.synthetic.main.fragment_orden.fmain_item_finish
import kotlinx.android.synthetic.main.fragment_orden.fmain_item_onprocess
import kotlinx.android.synthetic.main.fragment_orden.recyclerView

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class OrdenFragment : Fragment() {

    private var presenter : OrdenFragmentPresenter? = null
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orden, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ORDEN :Orden = arguments!!.getSerializable("orden") as Orden
        presenter = OrdenFragmentPresenter(this,ORDEN.id)

        aorden_swiperefresh!!.setOnRefreshListener {
            requestData()
        }
        requestData()
        navController = Navigation.findNavController(view!!)
    }

    private fun requestData(){
        presenter!!.requestAllData()
        aorden_swiperefresh!!.isRefreshing = true
    }

    fun showOrder(orden: Orden) {
        aorden_swiperefresh?.isRefreshing= false
        aorden_tViewFundo!!.text = "" + orden.getCultivoName() + " / "+ orden.getLoteCode()
        aorden_tViewEmpresa!!.text = "" + orden.getFundoName()
        aorden_tViewDateTime!!.text = "" + orden.getAplicacionDate()
        aorden_tViewnNOrden!!.text = "" + orden.getOrdenCode()
        aorden_tViewNTankAll!!.text = "" + orden.getTancadasProgramadas()
        aorden_tViewNTankComplete!!.text = ""+orden.getCantComplete()

        fmain_item_pendiente.setVisibility(View.GONE)
        fmain_item_finish.setVisibility(View.GONE)
        fmain_item_onprocess.setVisibility(View.GONE)

        when (orden.getCurrentProccess()) {
            Orden.status_pendiente -> {
                fmain_item_pendiente.setVisibility(View.VISIBLE)
                fmain_item_onprocess.setVisibility(View.GONE)
                fmain_item_finish.setVisibility(View.GONE)
            }
            Orden.status_enproceso -> {
                fmain_item_pendiente.setVisibility(View.GONE)
                fmain_item_onprocess.setVisibility(View.VISIBLE)
                fmain_item_finish.setVisibility(View.GONE)
            }
            Orden.status_finalizada -> {
                fmain_item_pendiente.setVisibility(View.GONE)
                fmain_item_onprocess.setVisibility(View.GONE)
                fmain_item_finish.setVisibility(View.VISIBLE)
            }
        }

        val adapter = RViewAdapterListTancadas(activity, orden.tancadas, Recipe(orden.ordenesDetalle))
        adapter.setOnClicListener {
            val pos = recyclerView!!.getChildAdapterPosition(it)
            val tancada = adapter.getTancada(pos)
            val recipe = Recipe( orden.ordenesDetalle)
            val bundle = bundleOf("tancada" to tancada,"recipe" to recipe )
            navController!!.navigate(R.id.action_SecondFragment_to_tancadaFragment,bundle)
        }

        recyclerView!!.adapter = adapter
    }
    var navController : NavController? = null
    fun showError(error: String) {
        Toast.makeText(activity,error, Toast.LENGTH_LONG).show()
    }
}
