package com.ibao.premescla.ui.productoPesado

import android.app.Dialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothProfile
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.harrysoft.androidbluetoothserial.BluetoothManager
import com.ibao.premescla.R
import com.ibao.premescla.models.ProductoPesado
import com.ibao.premescla.ui.main.views.MainActivityViewModel
import com.ibao.premescla.utils.CommunicateViewModel
import com.ibao.premescla.utils.Utilities
import com.ibao.premescla.utils.appContext
import java.lang.Exception
import java.util.*
import kotlin.math.absoluteValue

class ActivityProductoPesado : AppCompatActivity(){

    private val  tViewPesoReal by lazy{ findViewById(R.id.tViewPesoReal) as TextView}
    private val  btnConect by lazy{ findViewById(R.id.btnConect) as MaterialButton}
    private val  tViewStep by lazy{ findViewById(R.id.tViewStep) as TextView}
    private val  tViewTotalStep by lazy{ findViewById(R.id.tViewTotalStep) as TextView}
    private val  tViewPesoRequest by lazy{ findViewById(R.id.tViewPesoRequest) as TextView}
    private val tViewProductName  by lazy{ findViewById(R.id.tViewProductName) as TextView}
    private val tViewProductActive  by lazy{ findViewById(R.id.tViewProductActive) as TextView}
    private val tViewMessageTolerance  by lazy{ findViewById(R.id.tViewMessageTolerance) as TextView}
    private val btnNext  by lazy{ findViewById(R.id.btnNext) as MaterialButton}
    private val btnBack  by lazy{ findViewById(R.id.btnBack) as MaterialButton}

    private var viewModel: MainActivityViewModel? = null

    private var viewModelComunicate: CommunicateViewModel? = null

    private var tViewDeviceSelected : TextView? =null
    private var ctx: Context? = null


    private var presenter : ProductoPesadoPresenter? = null
    private val bundle: Bundle  by lazy{ intent!!.extras!! }
    val ppesado   by lazy{ bundle!!.getSerializable("ppesado") as ProductoPesado }
    val pos   by lazy{ bundle!!.getInt("pos")  }
    val all   by lazy{ bundle!!.getInt("all") }


    var PPESADO_SAVE: ProductoPesado = ProductoPesado()

    private val mBroadcastReceiver1: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action: String? = intent!!.getAction()
            if (action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                val state: Int = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                when (state) {
                    BluetoothAdapter.STATE_OFF -> {
                        MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@ActivityProductoPesado, R.drawable.ic_bluetooth_disabled_black_24dp));
                    }
                    BluetoothAdapter.STATE_TURNING_OFF -> {
                        MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@ActivityProductoPesado, R.drawable.ic_settings_bluetooth_black_24dp));
                    }
                    BluetoothAdapter.STATE_ON -> {
                        MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@ActivityProductoPesado, R.drawable.ic_bluetooth_black_24dp));
                    }
                    BluetoothAdapter.STATE_TURNING_ON -> {
                        MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@ActivityProductoPesado, R.drawable.ic_settings_bluetooth_black_24dp));
                    }
                }
            }
        }
    }


    val TAG = ActivityProductoPesado::class.java.simpleName
    fun convertPesoToGr(peso: Float, untis: String): Int {

        var pesoResponse = peso
        Log.d(TAG, "units=$untis")
        when(untis){
             "KGS"  -> pesoResponse *= 1000
        }
        return pesoResponse.toInt()
    }

    private fun refresh(){
        PPESADO_SAVE.cantidadPesada= 0f
        Log.d(TAG,"pesada ->"+PPESADO_SAVE.cantidadPesada)
        btnNext.text= "Lecturar"
        tViewStep.text = ""+pos
        tViewTotalStep.text = ""+all
        tViewPesoRequest.text = ""+convertPesoToGr(ppesado.dosis,ppesado.units)+"g"
        tViewProductName.text = ""+ppesado.productName
        tViewProductActive.text = ""+ppesado.productActive
        tViewMessageTolerance.text =  getString(R.string.torelancia_messagge) +" "+ppesado.toleranceRate+"%"

        btnNext.isEnabled = false
        btnNext.alpha = 0.5f
    }

    private val mBroadcastReceiver3: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent!!.action
            when (action) {
                BluetoothDevice.ACTION_ACL_CONNECTED -> {
                    // Toast.makeText(ctx,"Conectado",Toast.LENGTH_SHORT).show()
                    MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@ActivityProductoPesado, R.drawable.ic_bluetooth_connected_black_24dp));
                }
                BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
                    // Toast.makeText(ctx,"disconected",Toast.LENGTH_SHORT).show()
                    MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@ActivityProductoPesado, R.drawable.ic_bluetooth_black_24dp));
                }
            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mBroadcastReceiver1)
        unregisterReceiver(mBroadcastReceiver3)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto_pesado)
        presenter = ProductoPesadoPresenter(this)
        title="#1: 7D34G22"

        // Setup our ViewModel
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        // Setup our ViewModel
        viewModelComunicate = ViewModelProviders.of(this)[CommunicateViewModel::class.java]

        tViewDeviceSelected = findViewById(R.id.tViewDeviceSelected)
        // This method return false if there is an error, so if it does, we should close.
        // This method return false if there is an error, so if it does, we should close.
        if (!viewModel!!.setupViewModel()) {
            finish()
            return
        }
        // This method return false if there is an error, so if it does, we should close.
        // This method return false if there is an error, so if it does, we should close.
        if (!viewModelComunicate!!.setupViewModel("", "")) {
            finish()
            return
        }

        ctx = this

        viewModelComunicate!!.connectionStatus.observe(this, Observer { connectionStatus: CommunicateViewModel.ConnectionStatus -> onConnectionStatus(connectionStatus) })
        //viewModelComunicate!!.deviceName.observe(this, Observer { name: String? -> title = getString(R.string.device_name_format, name) })
        viewModelComunicate!!.messages.observe(this, Observer { message: String? ->
            /*
            if (TextUtils.isEmpty(message)) {
                viewModelComunicate!!.setMessages("No hay Mensajes")
            }
             */

            val peso = convertPesoToGr(ppesado.dosis,ppesado.units)
            var pesoReal = 0
            try {
                if(message!!.length>1 ){
                    pesoReal= message!!.substring(0,message!!.length-1).toInt()
                }
            }catch (ex : Exception){

            }

            val dif = (peso*1.0)/ppesado.toleranceRate.absoluteValue

            if( (peso - pesoReal).absoluteValue <= dif ){

                btnNext.isEnabled = true
                btnNext.alpha = 1f
            }else{
                btnNext.isEnabled = false
                btnNext.alpha = 0.5f
            }
            //quiere decir q aun no se guarda y debe actualizar
            if(PPESADO_SAVE.cantidadPesada==0f){
                Log.d(TAG,"actualiza p real")
                tViewPesoReal!!.text = message
            }else// si ya se guardo el peso debe dejar de actualizar
            {
                Log.d(TAG,"actualiza p real")
                btnNext.isEnabled = true
                btnNext.alpha = 1f
                btnNext.text= "Ok"
            }
        })

        btnNext.setOnClickListener {
            if(btnNext.text=="Lecturar"){

                PPESADO_SAVE.fechaPesada = Utilities.getDateTime()
                PPESADO_SAVE.cantidadPesada = tViewPesoReal.text.toString().substring(0,tViewPesoReal.text.toString().length-1).toFloat()
                presenter!!.requestPosPPesado(ppesado) //subiendo el resultado
            }else
            {//siguiente
                presenter!!.requestNewPPesado(ppesado.idTancada)
            }

        }

        btnBack.setOnClickListener {

            refresh()
        }
        registerFilters()



        refresh()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    // Called when the ViewModel updates us of our connectivity status
    private fun onConnectionStatus(connectionStatus: CommunicateViewModel.ConnectionStatus) {
        when (connectionStatus) {
            CommunicateViewModel.ConnectionStatus.CONNECTED -> {

                btnConect!!.isEnabled = true
                btnConect!!.setText("Conectado")
                btnConect!!.setOnClickListener { v: View? -> viewModelComunicate!!.disconnect() }

            }
            CommunicateViewModel.ConnectionStatus.CONNECTING -> {

                btnConect!!.isEnabled = false
                btnConect!!.setText("Conectando...")
            }
            CommunicateViewModel.ConnectionStatus.DISCONNECTED -> {

                btnConect!!.isEnabled = true
                btnConect!!.setText("Desconectado")
                btnConect!!.setOnClickListener { v: View? -> viewModelComunicate!!.connect() }
            }
        }
    }
    private fun showDialog() {
        val dialog = Dialog(this@ActivityProductoPesado)

        dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog .setCancelable(true)
        dialog .setContentView(R.layout.dialog_list_devices)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        var  fab = dialog.findViewById<FloatingActionButton>(R.id.fabRefresgPaired)
        var  rViewDevices = dialog.findViewById<RecyclerView>(R.id.rViewPairedDevices)

        val adapter = DeviceAdapter()
        rViewDevices.adapter = (adapter)

        fab.setOnClickListener{
            viewModel!!.refreshPairedDevices()
            // Start observing the data sent to us by the ViewModel

        }
        viewModel!!.pairedDeviceList.observe(this@ActivityProductoPesado, Observer(adapter::updateList))
        // Immediately refresh the paired devices list
        viewModel!!.refreshPairedDevices()

        dialog .show()

    }

    private fun registerFilters() {

        val filter1 = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        registerReceiver(mBroadcastReceiver1,filter1)

        val filter3 = IntentFilter()
        filter3.addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
        filter3.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        registerReceiver(mBroadcastReceiver3, filter3)

    }

    lateinit var MENU: Menu

    override fun onCreateOptionsMenu(menu: Menu): Boolean { // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.order_detail, menu)
        MENU = menu



        var  mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        when {
            mBluetoothAdapter == null -> {
                Toast.makeText(ctx,"null",Toast.LENGTH_SHORT).show()
                MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@ActivityProductoPesado, R.drawable.ic_warning_black_24dp))
            }
            mBluetoothAdapter.isEnabled -> {

                if(!isConnected(BluetoothManager.getInstance())){

                    Toast.makeText(ctx,"disconected",Toast.LENGTH_SHORT).show()
                    MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@ActivityProductoPesado, R.drawable.ic_bluetooth_black_24dp));

                }else{
                    Toast.makeText(ctx,"Conectado ",Toast.LENGTH_SHORT).show()
                    MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@ActivityProductoPesado, R.drawable.ic_bluetooth_connected_black_24dp));
                }

            }
            else -> {
                MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@ActivityProductoPesado, R.drawable.ic_bluetooth_disabled_black_24dp))
                Toast.makeText(ctx,"no habilitado",Toast.LENGTH_SHORT).show()
            }
        }

        return true
    }


    fun isConnected(bluetoothAdapter : BluetoothManager): Boolean {
        var res = false
        val pairedDevices: MutableCollection<BluetoothDevice>? = bluetoothAdapter.pairedDevicesList

        Log.d("debice","compare")
        pairedDevices?.forEach { device ->
            if(device.bondState == BluetoothProfile.STATE_CONNECTED){
                if(!res){
                    res =true
                    Log.d("debice",device.name)
                }
            }
        }
        return  res

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // Handle action bar item clicks here. The action bar will

        val id = item.itemId
        if (id == R.id.action_bluetooth) {
            showDialog()
        }
         return super.onOptionsItemSelected(item)
    }

    fun showError(error: String) {
        Toast.makeText(this,error,Toast.LENGTH_LONG).show()
        refresh()
    }

    fun goToNext(ppesado: ProductoPesado, actual: Int, all: Int) {
        val intent : Intent = Intent(this,ActivityProductoPesado::class.java)
        intent.putExtra("ppesado",ppesado)
        intent.putExtra("pos",actual)
        intent.putExtra("all",all)
        startActivity(intent)
        finish()
    }

    fun saveOk() {
        Toast.makeText(this,"Guardado",Toast.LENGTH_LONG).show()
        btnBack.alpha=0.5f
        btnBack.isEnabled=false
    }


    // A class to hold the data in the RecyclerView
    private inner class DeviceViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        private val layout: RelativeLayout
        private val text1: TextView
        private val text2: TextView
        private val conectado: TextView
        fun setupView(device: BluetoothDevice) {
            text1.text = device.name
            text2.text = device.address
            if(device.name == appContext.deviceSelect && device.address == appContext.macSelect){
                conectado.visibility= View.VISIBLE
                tViewDeviceSelected!!.text=device.name+ " -> " +device.address

                viewModelComunicate!!.setupViewModel(device.name, device.address)

            }else{
                conectado.visibility= View.GONE
            }
            layout.setOnClickListener{
                if(device.name == appContext.deviceSelect && device.address == appContext.macSelect){
                    appContext.deviceSelect = ""
                    appContext.macSelect = ""

                    viewModelComunicate!!.setupViewModel("", "")

                    tViewDeviceSelected!!.text=this@ActivityProductoPesado.getString(R.string.dispositivo_no_seleccionado)
                }else{
                    appContext.deviceSelect = device.name
                    appContext.macSelect = device.address
                }

                viewModel!!.refreshPairedDevices()
            }
        }

        init {
            layout = view.findViewById(R.id.list_item)
            text1 = view.findViewById(R.id.list_item_text1)
            text2 = view.findViewById(R.id.list_item_text2)
            conectado = view.findViewById(R.id.list_item_conectado)
        }
    }

    // A class to adapt our list of devices to the RecyclerView
    private inner class DeviceAdapter : RecyclerView.Adapter<DeviceViewHolder>() {
        private var deviceList: List<BluetoothDevice> = ArrayList()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
            return DeviceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
        }

        override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
            holder.setupView(deviceList[position])
        }

        override fun getItemCount(): Int {
            return deviceList.size
        }

        fun updateList(deviceList: List<BluetoothDevice>) {
            this.deviceList = deviceList
            notifyDataSetChanged()
        }
    }
}