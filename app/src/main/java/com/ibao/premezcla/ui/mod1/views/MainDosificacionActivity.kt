package com.ibao.premezcla.ui.mod1.views

import android.app.AlertDialog
import android.app.Dialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothProfile
import android.content.*
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.harrysoft.androidbluetoothserial.BluetoothManager
import com.ibao.premezcla.BuildConfig
import com.ibao.premezcla.R
import com.ibao.premezcla.SharedPreferencesManager
import com.ibao.premezcla.models.Orden
import com.ibao.premezcla.ui.ActivityPreloader
import com.ibao.premezcla.ui.modselector.ModSelectorActivity
import com.ibao.premezcla.utils.CommunicateViewModel
import com.ibao.premezcla.utils.appContext
import kotlinx.android.synthetic.main.mod1_content_main.*
import java.util.*
import java.util.Optional.of

class MainDosificacionActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    private var viewModelComunicate: CommunicateViewModel? = null
    private val  btnConect by lazy{ findViewById<MaterialButton>(R.id.btnConect) }
    private var viewModel: MainActivityViewModel? = null
    lateinit var MENU: Menu
    
    private val mBroadcastReceiver1: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action: String? = intent!!.getAction()
            if (action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                val state: Int = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                when (state) {
                    BluetoothAdapter.STATE_OFF -> {
                        MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@MainDosificacionActivity, R.drawable.ic_bluetooth_disabled_black_24dp));
                    }
                    BluetoothAdapter.STATE_TURNING_OFF -> {
                        MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@MainDosificacionActivity, R.drawable.ic_settings_bluetooth_black_24dp));
                    }
                    BluetoothAdapter.STATE_ON -> {
                        MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@MainDosificacionActivity, R.drawable.ic_bluetooth_black_24dp));
                    }
                    BluetoothAdapter.STATE_TURNING_ON -> {
                        MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@MainDosificacionActivity, R.drawable.ic_settings_bluetooth_black_24dp));
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
                    // Toast.makeText(ctx,"Conectado",Toast.LENGTH_SHORT).show()
                    MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@MainDosificacionActivity, R.drawable.ic_bluetooth_connected_black_24dp));
                }
                BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
                    // Toast.makeText(ctx,"disconected",Toast.LENGTH_SHORT).show()
                    MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@MainDosificacionActivity, R.drawable.ic_bluetooth_black_24dp));
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
        setContentView(R.layout.mod1_act_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = getString(R.string.tittle_all_orders)

        // Setup our ViewModel
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        // Setup our ViewModel
        viewModelComunicate = ViewModelProvider(this)[CommunicateViewModel::class.java]
        viewModelComunicate!!.connectionStatus.observe(this, Observer { connectionStatus: CommunicateViewModel.ConnectionStatus -> onConnectionStatus(connectionStatus) })

        // This method return false if there is an error, so if it does, we should close.
        // This method return false if there is an error, so if it does, we should close.
        if (!viewModel!!.setupViewModel()) {
            finish()
            return
        }


        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.menu.getItem(0).isChecked = true

        registerFilters()

    }

    private fun registerFilters() {
        val filter1 = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        registerReceiver(mBroadcastReceiver1,filter1)
        val filter3 = IntentFilter()
        filter3.addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
        filter3.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        registerReceiver(mBroadcastReceiver3, filter3)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean { // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.mod1_main, menu)
        MENU = menu


        var  mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        when {
            mBluetoothAdapter == null -> {
                //Toast.makeText(ctx,"null", Toast.LENGTH_SHORT).show()
                MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@MainDosificacionActivity, R.drawable.ic_warning_black_24dp))
            }
            mBluetoothAdapter.isEnabled -> {

                if(!isConnected(BluetoothManager.getInstance())){

               //     Toast.makeText(ctx,"disconected", Toast.LENGTH_SHORT).show()
                    MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@MainDosificacionActivity, R.drawable.ic_bluetooth_black_24dp));

                }else{
              //      Toast.makeText(ctx,"Conectado ", Toast.LENGTH_SHORT).show()
                    MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@MainDosificacionActivity, R.drawable.ic_bluetooth_connected_black_24dp));
                }

            }
            else -> {
                MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@MainDosificacionActivity, R.drawable.ic_bluetooth_disabled_black_24dp))
             //   Toast.makeText(ctx,"no habilitado", Toast.LENGTH_SHORT).show()
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
        val dialog = Dialog(this@MainDosificacionActivity)

        dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog .setCancelable(true)
        dialog .setContentView(R.layout.mod1_dialog_devices)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        var  fab = dialog.findViewById<FloatingActionButton>(R.id.fabRefresgPaired)
        var  rViewDevices = dialog.findViewById<RecyclerView>(R.id.rViewPairedDevices)

        val adapter = DeviceAdapter()
        rViewDevices.adapter = (adapter)

        fab.setOnClickListener{
            viewModel!!.refreshPairedDevices()
            // Start observing the data sent to us by the ViewModel

        }
        viewModel!!.pairedDeviceList.observe(this@MainDosificacionActivity, Observer(adapter::updateList))
        // Immediately refresh the paired devices list
        viewModel!!.refreshPairedDevices()

        dialog .show()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        if (id == R.id.action_bluetooth) {
            showDialog()
        }



        if (id == R.id.change_module) {
            SharedPreferencesManager.clearMode(baseContext)
            startActivity(Intent(this@MainDosificacionActivity, ModSelectorActivity::class.java))
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
        return if (id == R.id.action_logout) { SharedPreferencesManager.deleteUser(baseContext)
            startActivity(Intent(this, ActivityPreloader::class.java))
            Toast.makeText(baseContext,"Cerrando Sesión",Toast.LENGTH_SHORT).show()
            true
        } else super.onOptionsItemSelected(item)
    }

    companion object{
        val filter_none = -1
        val filter_pendiente = 0
        val filter_proceso = 1
        val filter_terminada = 2
        var myfilter = filter_none
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean { // Handle navigation view item clicks here.
        val id = item.itemId
        when (id) {
            R.id.nav_all -> {
                title = getString(R.string.tittle_all_orders)
                myfilter = filter_none
            }
            R.id.nav_pendiente -> {
                title = getString(R.string.tittle_pendiente_orders)
                myfilter = filter_pendiente
            }
            R.id.nav_proceso -> {
                title = getString(R.string.tittle_process_orders)
                myfilter = filter_proceso
            }
            R.id.nav_terminada -> {
                title = getString(R.string.tittle_finished_orders)
                myfilter = filter_terminada
            }
        }
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()!!.navigate(R.id.action_global_MainFragment)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        when(NavHostFragment.findNavController(nav_host_fragment).currentDestination!!.id) {
            R.id.MainFragment-> {

            }
            else -> {
                super.onBackPressed()
            }
        }
    }
    val TAG = "MainMezclaActivity.tk"
    fun showError(error: String) {
        Log.e(TAG,error)
        Toast.makeText(this,error, Toast.LENGTH_LONG).show()
    }

    fun showOrderList(ordenList: List<Orden>) {
        //todo: quita
        //mySwipeRefreshLayout!!.isRefreshing = false

        var temp = ordenList
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
                    tViewDeviceSelected!!.text=this@MainDosificacionActivity.getString(R.string.dispositivo_no_seleccionado)
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
            return DeviceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.mod1_dialog_devices_item, parent, false))
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