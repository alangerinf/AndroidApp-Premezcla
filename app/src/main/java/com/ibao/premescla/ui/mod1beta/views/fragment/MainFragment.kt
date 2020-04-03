package com.ibao.premescla.ui.mod1beta.views.fragment

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
import com.ibao.premescla.ui.mod1.main.views.adapters.RViewAdapterListOrdenes
import com.ibao.premescla.ui.mod1beta.presenters.MainMezclaPresenter
import com.ibao.premescla.ui.mod1beta.views.MainMezclaActivity.Companion.filter_proceso
import com.ibao.premescla.ui.mod1beta.views.MainMezclaActivity.Companion.filter_terminada
import com.ibao.premescla.ui.mod1beta.views.MainMezclaActivity.Companion.myfilter
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment() {

    private var presenter: MainMezclaPresenter? = null
    
    val TAG = MainFragment::class.java.simpleName
    
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = MainMezclaPresenter(this)
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
        main_swiperefresh!!.isRefreshing = false
        Toast.makeText(activity,error,Toast.LENGTH_SHORT).show()
    }

    fun showOrderList(ordenList: List<Orden>) {

        main_swiperefresh!!.isRefreshing = false

        var temp = ordenList

        when(myfilter) {
            filter_proceso -> {
                temp  = temp.filter { !it.isComplete }

            }
            filter_terminada -> {
                temp  = temp.filter { it.isComplete }
            }
            else -> { // Note the block

            }
        }

        var adapter = RViewAdapterListOrdenes(activity,temp)

        adapter.setOnClicListener {
            val pos = fmain_rView!!.getChildAdapterPosition(it)
            val orden = adapter.getOrden(pos)
            val bundle = bundleOf("orden" to orden )
            navController!!.navigate(R.id.action_FirstFragment_to_SecondFragment,bundle)
      }

        fmain_rView!!.adapter = adapter

    }
}