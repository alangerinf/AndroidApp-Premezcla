package com.ibao.premezcla.ui.mod2.edit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ibao.premezcla.R
import com.ibao.premezcla.models.Tancada
import com.ibao.premezcla.ui.mod2.select.SelectAplicatorsActivity
import kotlinx.android.synthetic.main.mod2_act_edit.*


class EditSensorsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mod2_act_edit)

        val TANCADA = intent.extras?.getSerializable("tancada") as Tancada

            btnEditar.setOnClickListener {
                var phIsCorrect = true
                var condIsCorrect = true

                var valuePH = 0f
                var valueConductividad = 0f

                var phString = pH.text.toString()
                var condString = conductividad.text.toString()

                if (!phString.isEmpty()) {
                    try {
                        valuePH = phString.toFloat()
                    } catch (e: Exception) {
                        try {
                            valuePH = phString.replace(".", ",").toFloat()
                        } catch (e1: java.lang.Exception) {
                            phIsCorrect = false
                            fieldPH.error = "valor incorrecto"
                        }
                    }
                }

                if (!condString.isEmpty()) {
                    try {
                        valueConductividad = condString.toFloat()
                    } catch (e: Exception) {
                        try {
                            valueConductividad = condString.replace(".", ",").toFloat()
                        } catch (e1: java.lang.Exception) {
                            condIsCorrect = false
                            fieldConductividad.error = "valor incorrecto"
                        }
                    }
                }

            if (phIsCorrect && condIsCorrect) {
                val intent = Intent(this@EditSensorsActivity, SelectAplicatorsActivity::class.java)
                Log.i("MEDICIONES","valuePH: "+valuePH)
                val i = Log.i("MEDICIONES", "valueConductividad: " + valueConductividad)
                TANCADA.ph=valuePH.toString()
                TANCADA.conductividad = valueConductividad.toString()
                intent.putExtra("tancada", TANCADA)

                startActivity(intent)

            } else {
                Toast.makeText(this@EditSensorsActivity, "revise los valores", Toast.LENGTH_LONG).show()
            }


        }


    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    fun showError(error: String) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
    }



}
