package com.ibao.premescla.ui.mod1beta.views.fragment.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ibao.premescla.R
import com.ibao.premescla.models.Orden
import com.ibao.premescla.models.Orden.*
import com.ibao.premescla.ui.mod1beta.presenters.MainDosificacionPresenter
import com.ibao.premescla.ui.mod1beta.views.MainDosificacionActivity.Companion.filter_pendiente
import com.ibao.premescla.ui.mod1beta.views.MainDosificacionActivity.Companion.filter_proceso
import com.ibao.premescla.ui.mod1beta.views.MainDosificacionActivity.Companion.filter_terminada
import com.ibao.premescla.ui.mod1beta.views.MainDosificacionActivity.Companion.myfilter
import kotlinx.android.synthetic.main.mod1_frag_main.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment() {

    private var presenter: MainDosificacionPresenter? = null
    
    val TAG = MainFragment::class.java.simpleName
    
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.mod1_frag_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = MainDosificacionPresenter(this)
        main_swiperefresh.setOnRefreshListener {
            Log.i(TAG, "onRefresh called from SwipeRefreshLayout")
            requestData()
        }

         navController = Navigation.findNavController(view!!)

    }
    var navController : NavController? = null

    override fun onResume() {
        Log.i(TAG,"resume")
        super.onResume()
        requestData()
    }
    private fun requestData(){
        presenter!!.requestAllData()
        main_swiperefresh!!.isRefreshing = true
    }

    fun showError(error: String) {
        main_swiperefresh?.isRefreshing = false
        Toast.makeText(activity,error,Toast.LENGTH_SHORT).show()
    }

    fun showOrderList(ordenList: List<Orden>) {

        main_swiperefresh?.isRefreshing = false

        var temp = ordenList

        when(myfilter) {
            filter_pendiente -> {
                temp  = temp.filter { it.currentProccess == status_pendiente }

            }
            filter_proceso -> {
                temp  = temp.filter { it.currentProccess == status_enproceso  }

            }
            filter_terminada -> {
                temp  = temp.filter { it.currentProccess == status_finalizada }
            }
            else -> { // Note the block

            }
        }

        var adapter = RViewAdapterListOrdenes(activity, temp)

        adapter.setOnClicListener {
            val pos = fmain_rView!!.getChildAdapterPosition(it)
            val orden = adapter.getOrden(pos)
            val bundle = bundleOf("orden" to orden )
            navController!!.navigate(R.id.action_FirstFragment_to_SecondFragment,bundle)
      }

        fmain_rView!!.adapter = adapter

    }
}
