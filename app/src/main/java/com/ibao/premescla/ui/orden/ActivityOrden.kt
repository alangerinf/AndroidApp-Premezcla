package com.ibao.premescla.ui.orden

import android.annotation.SuppressLint
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.harrysoft.androidbluetoothserial.BluetoothManager
import com.ibao.premescla.R
import com.ibao.premescla.models.Orden
import com.ibao.premescla.ui.main.views.MainActivityViewModel
import com.ibao.premescla.ui.orden.adapters.RViewAdapterListTancadas
import com.ibao.premescla.ui.tancada.ActivityTancada
import com.ibao.premescla.utils.appContext
import java.util.*

class ActivityOrden : AppCompatActivity(){


    private var viewModel: MainActivityViewModel? = null

    private var ctx: Context? = null
    private var presenter : OrdenPresenter? = null


    private var   mySwipeRefreshLayout: SwipeRefreshLayout?= null
    private var myRView: RecyclerView?= null

    private var tViewnNOrden: TextView? = null
    private var tViewFundo: TextView? = null
    private var tViewEmpresa: TextView? = null
    private var tViewNTankAll: TextView? = null
    private var tViewNTankComplete: TextView? = null
    private var tViewDateTime: TextView? = null
    

    private val mBroadcastReceiver1: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action: String? = intent!!.getAction()
            if (action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                val state: Int = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                when (state) {
                    BluetoothAdapter.STATE_OFF -> {
                        MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@ActivityOrden, R.drawable.ic_bluetooth_disabled_black_24dp));
                    }
                    BluetoothAdapter.STATE_TURNING_OFF -> {
                        MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@ActivityOrden, R.drawable.ic_settings_bluetooth_black_24dp));
                    }
                    BluetoothAdapter.STATE_ON -> {
                        MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@ActivityOrden, R.drawable.ic_bluetooth_black_24dp));
                    }
                    BluetoothAdapter.STATE_TURNING_ON -> {
                        MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@ActivityOrden, R.drawable.ic_settings_bluetooth_black_24dp));
                    }
                }
            }
        }
    }

    private val mBroadcastReceiver3: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent!!.action
            when (action) {
                BluetoothDevice.ACTION_ACL_CONNECTED -> {
                    //Toast.makeText(ctx,"Conectado",Toast.LENGTH_SHORT).show()
                    MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@ActivityOrden, R.drawable.ic_bluetooth_connected_black_24dp));
                }
                BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
                    //Toast.makeText(ctx,"disconected",Toast.LENGTH_SHORT).show()
                    MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@ActivityOrden, R.drawable.ic_bluetooth_black_24dp));
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mBroadcastReceiver1)
        unregisterReceiver(mBroadcastReceiver3)
    }

    lateinit var bundle : Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        title = "Orden"

        // Setup our ViewModel
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        // This method return false if there is an error, so if it does, we should close.
        // This method return false if there is an error, so if it does, we should close.
        if (!viewModel!!.setupViewModel()) {
            finish()
            return
        }
        mySwipeRefreshLayout = findViewById(R.id.aorden_swiperefresh)
        myRView = findViewById(R.id.recyclerView)
        ctx = this

        tViewnNOrden = findViewById(R.id.aorden_tViewnNOrden)
        tViewFundo = findViewById(R.id.aorden_tViewFundo)
        tViewEmpresa = findViewById(R.id.aorden_tViewEmpresa)
        tViewNTankAll = findViewById(R.id.aorden_tViewNTankAll)
        tViewNTankComplete = findViewById(R.id.aorden_tViewNTankComplete)

        tViewDateTime = findViewById(R.id.aorden_tViewDateTime)

        registerFilters()

        bundle = intent.extras!!
        val orden :Orden = bundle!!.getSerializable("orden") as Orden
        presenter = OrdenPresenter(this,orden.id)

        mySwipeRefreshLayout!!.setOnRefreshListener {
            requestData()
        }
        requestData()
    }

    private fun requestData(){
        presenter!!.requestAllData()
        mySwipeRefreshLayout!!.isRefreshing = true
    }

    private fun showDialog() {
        val dialog = Dialog(this@ActivityOrden)

        dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog .setCancelable(true)
        dialog .setContentView(R.layout.dialog_list_devices)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val fab = dialog.findViewById<FloatingActionButton>(R.id.fabRefresgPaired)
        val rViewDevices = dialog.findViewById<RecyclerView>(R.id.rViewPairedDevices)

        val adapter = DeviceAdapter()
        rViewDevices.adapter = (adapter)

        fab.setOnClickListener{
            viewModel!!.refreshPairedDevices()
            // Start observing the data sent to us by the ViewModel

        }
        viewModel!!.pairedDeviceList.observe(this@ActivityOrden, Observer(adapter::updateList))
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
                MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@ActivityOrden, R.drawable.ic_warning_black_24dp))
            }
            mBluetoothAdapter.isEnabled -> {

                if(!isConnected(BluetoothManager.getInstance())){

                    Toast.makeText(ctx,"disconected",Toast.LENGTH_SHORT).show()
                    MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@ActivityOrden, R.drawable.ic_bluetooth_black_24dp));

                }else{
                    Toast.makeText(ctx,"Conectado ",Toast.LENGTH_SHORT).show()
                    MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@ActivityOrden, R.drawable.ic_bluetooth_connected_black_24dp));
                }

            }
            else -> {
                MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@ActivityOrden, R.drawable.ic_bluetooth_disabled_black_24dp))
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

    val TAG : String = ActivityOrden::class.java.simpleName

    @SuppressLint("SetTextI18n")
    fun showOrder(orden: Orden) {
        mySwipeRefreshLayout!!.isRefreshing= false

        tViewFundo!!.text = "" + orden.getCultivoName() + "\n" + orden.getVariedadName()
        tViewEmpresa!!.text = "" + orden.getFundoName()
        tViewDateTime!!.text = "" + orden.getAplicacionDate()
        tViewnNOrden!!.text = "" + orden.getOrdenCode()
        tViewNTankAll!!.text = "" + orden.getTancadasProgramadas()
        tViewNTankComplete!!.text = ""+orden.getCantComplete()

        val adapter = RViewAdapterListTancadas(this,orden.tancadas,orden.ordenesDetalle.size)
        adapter.setOnClicListener {

            val pos = myRView!!.getChildAdapterPosition(it)
            val intent = Intent(this, ActivityTancada::class.java)
            val tancada = adapter.getTancada(pos)
            intent.putExtra("tancada", tancada)
            intent.putExtra("oDetalleSize", orden.ordenesDetalle.size)
            Log.d(TAG,"pos="+tancada.id)
            startActivity(intent)
        }

        myRView!!.adapter = adapter
     }

    fun showError(error: String) {
        Toast.makeText(this,error,Toast.LENGTH_LONG).show()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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
            }else{
                conectado.visibility= View.GONE
            }
            layout.setOnClickListener{
                if(device.name == appContext.deviceSelect && device.address == appContext.macSelect){
                    appContext.deviceSelect = ""
                    appContext.macSelect = ""

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