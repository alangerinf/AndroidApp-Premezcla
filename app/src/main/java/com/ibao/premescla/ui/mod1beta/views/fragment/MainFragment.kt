package com.ibao.premescla.ui.mod1beta.views.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ibao.premescla.R
import com.ibao.premescla.models.Orden
import com.ibao.premescla.ui.mod1.main.views.adapters.RViewAdapterListOrdenes
import com.ibao.premescla.ui.mod1beta.presenters.MainMezclaPresenter
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
    }

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
/*
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
*/
        var adapter = RViewAdapterListOrdenes(activity,temp)
      /*
        adapter.setOnClicListener {
            val pos = myRView!!.getChildAdapterPosition(it)
            val intent = Intent(this@MainActivity, ActivityOrden::class.java)
            val orden = adapter.getOrden(pos)
            intent.putExtra("orden", orden)
            Log.d(TAG,"pos="+orden.ordenCode)
            startActivity(intent)
        }
*/
        fmain_rView!!.adapter = adapter

    }
}
