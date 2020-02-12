package com.ibao.premescla.ui.main.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ibao.premescla.R
import com.ibao.premescla.models.Orden

class AllFragment : Fragment() , FragmentMainInterface{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun showOrdenList(ordenList: List<Orden?>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}