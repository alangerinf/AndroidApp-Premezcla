package com.ibao.premescla.ui.mod2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.ibao.premescla.R
import kotlinx.android.synthetic.main.activity_edit_sensors.*


class EditSensorsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_sensors)
        btnOmitir.setOnClickListener {
            startActivity(Intent(this@EditSensorsActivity,SelectAplicatorsActivity::class.java))
            btnOmitir.isFocusable=false
            btnOmitir.isClickable=false
            finish()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}
