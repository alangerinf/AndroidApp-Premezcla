package com.ibao.premescla.ui.modselector

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ibao.premescla.R
import com.ibao.premescla.ui.mod1.main.views.MainActivity
import com.ibao.premescla.ui.mod2.main_scanner.CustomScannerActivity
import com.ibao.premescla.ui.mod3.inbox.ActivityInboxTancada
import kotlinx.android.synthetic.main.activity_mod_selector.*

class ModSelectorActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mod_selector)



        btnDosificacion.setOnClickListener {
            val i = Intent(this,MainActivity::class.java);
            startActivity(i)
            it.isEnabled = false
        }

        btnMezcla.setOnClickListener {
            val i = Intent(this,CustomScannerActivity::class.java);
            startActivity(i)
            it.isEnabled = false
        }

        btnAplicacion.setOnClickListener {
            val i = Intent(this,ActivityInboxTancada::class.java);
            startActivity(i)
            it.isEnabled = false
        }
    }

    override fun onResume() {
        super.onResume()
        btnDosificacion.isEnabled = true
        btnMezcla.isEnabled = true
        btnAplicacion.isEnabled = true
    }
}
