package com.ibao.premescla.ui.mod1beta.views.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ibao.premescla.R
import com.ibao.premescla.models.Orden
import com.ibao.premescla.models.Recipe
import com.ibao.premescla.ui.mod1.orden.OrdenPresenter
import com.ibao.premescla.ui.mod1.orden.adapters.RViewAdapterListTancadas
import com.ibao.premescla.ui.mod1.tancada.ActivityTancada
import com.ibao.premescla.ui.mod1beta.presenters.OrdenFragmentPresenter
import kotlinx.android.synthetic.main.activity_order.*

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
        aorden_swiperefresh!!.isRefreshing= false

        aorden_tViewFundo!!.text = "" + orden.getCultivoName() + "\n" + orden.getVariedadName()
        aorden_tViewEmpresa!!.text = "" + orden.getFundoName()
        aorden_tViewDateTime!!.text = "" + orden.getAplicacionDate()
        aorden_tViewnNOrden!!.text = "" + orden.getOrdenCode()
        aorden_tViewNTankAll!!.text = "" + orden.getTancadasProgramadas()
        aorden_tViewNTankComplete!!.text = ""+orden.getCantComplete()



        if(orden.isComplete){
            fmain_item_finish.visibility= View.VISIBLE
            fmain_item_onprocess.visibility= View.GONE
        }else{
            fmain_item_finish.visibility= View.GONE
            fmain_item_onprocess.visibility= View.VISIBLE
        }

        val adapter = RViewAdapterListTancadas(activity,orden.tancadas, Recipe(orden.ordenesDetalle))
        adapter.setOnClicListener {
            val pos = recyclerView!!.getChildAdapterPosition(it)
            val tancada = adapter.getTancada(pos)
            val recipe = Recipe( orden.ordenesDetalle)
            val bundle = bundleOf("tancada" to tancada,"recipe" to recipe )
            navController!!.navigate(R.id.action_FirstFragment_to_SecondFragment,bundle)
        }

        recyclerView!!.adapter = adapter
    }
    var navController : NavController? = null
    fun showError(error: String) {
        Toast.makeText(activity,error, Toast.LENGTH_LONG).show()
    }
}
