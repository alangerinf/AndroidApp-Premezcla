package com.ibao.premescla.ui.main.views.fragments

import com.ibao.premescla.models.Orden

interface FragmentMainInterface {
    fun showOrdenList(ordenList: List<Orden?>?)
}