package com.ibao.premezcla.ui.mod2.main

import android.Manifest.permission
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultPoint
import com.google.zxing.client.android.BeepManager
import com.ibao.premezcla.BuildConfig
import com.ibao.premezcla.R
import com.ibao.premezcla.SharedPreferencesManager
import com.ibao.premezcla.models.Tancada
import com.ibao.premezcla.ui.ActivityPreloader
import com.ibao.premezcla.ui.mod2.edit.EditSensorsActivity
import com.ibao.premezcla.ui.mod2.main.MainMezclaActivity
import com.ibao.premezcla.ui.modselector.ModSelectorActivity
import com.journeyapps.barcodescanner.*
import com.journeyapps.barcodescanner.DecoratedBarcodeView.TorchListener
import java.util.*

/**
 * Custom Scannner Activity extending from Activity to display a custom layout form scanner view.
 */
class MainMezclaActivity : AppCompatActivity(), TorchListener {


    private var barcodeScannerView: DecoratedBarcodeView? = null
    private val TAG = "QRScaner"
    private var presenter: TancadaPresenter? = null
    private var progressBar: ProgressBar? = null
    private val capture: CaptureManager? = null
    var ctx: Context? = null
    private var root: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mod2_act_main)
        ctx = this
        progressBar = findViewById(R.id.progressBar)
        statusLight = false
        fAButtonLinterna = findViewById(R.id.fAButtonLinterna)
        root = findViewById(R.id.root)
        validarPermisos()
        fAButtonLinterna?.setOnClickListener {
            if (statusLight) {
                statusLight = false
                //   Toast.makeText(getBaseContext(),"apagando",Toast.LENGTH_SHORT).show();
                barcodeScannerView!!.setTorchOff()
            } else {
                statusLight = true
                // Toast.makeText(getBaseContext(), "encendiendo", Toast.LENGTH_SHORT).show();
                barcodeScannerView!!.setTorchOn()
            }
        }
        barcodeScannerView = findViewById<View>(R.id.zxing_barcode_scanner) as DecoratedBarcodeView
        barcodeScannerView!!.setTorchListener(this)
        val formats: Collection<BarcodeFormat> = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39, BarcodeFormat.CODABAR)
        barcodeScannerView!!.barcodeView.decoderFactory = DefaultDecoderFactory(formats)
        barcodeScannerView!!.initializeFromIntent(intent)
        barcodeScannerView!!.decodeContinuous(callback)
        beepManager = BeepManager(this)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.mod2_main, menu)
        return true
    }
    override fun onBackPressed() {

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.change_module) {
            SharedPreferencesManager.clearMode(baseContext)
            startActivity(Intent(this@MainMezclaActivity,ModSelectorActivity::class.java))
            return true
        }

        if (id == R.id.action_version) {
            try {
                Toast.makeText(baseContext, "Versión " + BuildConfig.VERSION_NAME + " code." + BuildConfig.VERSION_CODE /*+" db."+ ConexionSQLiteHelper.VERSION_DB*/, Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(baseContext, e.toString(), Toast.LENGTH_LONG).show()
            }
            return true
        }
        return if (id == R.id.action_logout) {
            SharedPreferencesManager.deleteUser(baseContext)
            startActivity(Intent(this,ActivityPreloader::class.java))
            Toast.makeText(baseContext,"Cerrando Sesión",Toast.LENGTH_SHORT).show()
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        barcodeScannerView!!.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeScannerView!!.pause()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return barcodeScannerView!!.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }

    /**
     * Check if the device's camera has a Flashlight.
     * @return true if there is Flashlight, otherwise false.
     */
    private fun hasFlash(): Boolean {
        return applicationContext.packageManager
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    fun switchFlashlight(view: View?) {}
    fun changeMaskColor(view: View?) {
        val rnd = Random()
        val color = Color.argb(100, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        //viewfinderView.setMaskColor(color);
    }

    override fun onTorchOn() {
        fAButtonLinterna!!.setImageResource(R.drawable.ic_highlight_black_24dp)
    }

    override fun onTorchOff() {
        fAButtonLinterna!!.setImageResource(R.drawable.ic_light_white_off)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return false
    }

    private var beepManager: BeepManager? = null
    private var isGoToEdit = false
    private val callback: BarcodeCallback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            if (result != null) {
                val QR = result.text
                try {
                    val idTancada = Tancada.getIdparseFromQR(QR)
                    if (idTancada != 0) {
                        presenter = TancadaPresenter(this@MainMezclaActivity, idTancada)
                        presenter!!.requestAllData()
                        progressBar!!.visibility = View.VISIBLE
                        if (!isGoToEdit) {
                            isGoToEdit = true
                            handler.post(runnableFound)
                            //handler.postDelayed(()-> isGoToEdit=false,2000);
                        }
                    }
                } catch (e: Exception) {
                }
            }
        }

        var runnableFound = Runnable {
            beepManager!!.isBeepEnabled = false
            beepManager!!.isVibrateEnabled = true
            beepManager!!.playBeepSoundAndVibrate()
        }

        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
    }

    private fun validarPermisos(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        if (checkSelfPermission(permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        if (shouldShowRequestPermissionRationale(permission.CAMERA)) {
            cargarDialogoRecomendacion()
        } else {
            requestPermissions(arrayOf(permission.CAMERA), 100)
        }
        return false
    }

    private fun cargarDialogoRecomendacion() {
        val dialog = AlertDialog.Builder(this@MainMezclaActivity)
        dialog.setTitle("Permisos Desactivados")
        dialog.setMessage("Debe aceptar todos los permisos para poder tomar fotos")
        dialog.setPositiveButton("Aceptar") { dialog, which -> solicitarPermisosManual() }
        dialog.show()
    }

    private fun solicitarPermisosManual() {
        val opciones = arrayOf<CharSequence>(
                "si",
                "no"
        )
        val alertOpciones = AlertDialog.Builder(this@MainMezclaActivity)
        alertOpciones.setTitle("¿Desea configurar los permisos de Forma Manual?")
        alertOpciones.setItems(
                opciones
        ) { dialog: DialogInterface, i: Int ->
            if (opciones[i] == "si") {
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "Los permisos no fueron aceptados", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
        }
        alertOpciones.show()
    }

    private fun goToEdit(tancada: Tancada) {
        val i = Intent(this@MainMezclaActivity, EditSensorsActivity::class.java)
        i.putExtra("tancada", tancada)
        startActivity(i)
    }

    fun showTancada(tancada: Tancada) {
        goToEdit(tancada)
        handler.postDelayed({
            isGoToEdit = false
            progressBar!!.visibility = View.GONE
        }
                , 1000)
    }

    var handler = Handler()
    fun showError(error: String?) {
        progressBar!!.visibility = View.GONE
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        handler.post { isGoToEdit = false }
    }

    companion object {
        private var fAButtonLinterna: FloatingActionButton? = null
        private var statusLight = false
    }
}