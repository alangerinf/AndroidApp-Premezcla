package com.ibao.premezcla.ui.modselector

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.ibao.premezcla.R
import com.ibao.premezcla.SharedPreferencesManager
import com.ibao.premezcla.ui.mod1.views.MainDosificacionActivity
import com.ibao.premezcla.ui.mod2.main.MainMezclaActivity
import com.ibao.premezcla.ui.mod3.main.MainAplicacionActivity
import kotlinx.android.synthetic.main.activity_mod_selector.*

class ModSelectorActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mod_selector)

        btnDosificacion.setOnClickListener {
            SharedPreferencesManager.changeMode(baseContext,1);
            val i = Intent(this, MainDosificacionActivity::class.java);
            startActivity(i)
            it.isEnabled = false
        }

        btnMezcla.setOnClickListener {
            SharedPreferencesManager.changeMode(baseContext,2)
            val i = Intent(this, MainMezclaActivity::class.java);
            startActivity(i)
            it.isEnabled = false
        }

        btnAplicacion.setOnClickListener {
            SharedPreferencesManager.changeMode(baseContext,3)
            val i = Intent(this,MainAplicacionActivity::class.java);
            startActivity(i)
            it.isEnabled = false
        }
    }

    override fun onResume() {
        super.onResume()
        disableButton(btnDosificacion)
        disableButton(btnMezcla)
        disableButton(btnAplicacion)
        val user = SharedPreferencesManager.getUser(baseContext)
        for(module in user.modulesList){
            when(module){
                1 -> enableButton(btnDosificacion)
                2 -> enableButton(btnMezcla)
                3 -> enableButton(btnAplicacion)
            }
        }
    }
    override fun onBackPressed() {

    }
    fun disableButton(btn : Button){
        btn.isEnabled = false
        btn.alpha= 0.5f
    }
    fun enableButton(btn : Button){
        btn.isEnabled = true
        btn.alpha= 1f
    }
}
