package com.ibao.premescla.ui.mod1beta.views

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
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.harrysoft.androidbluetoothserial.BluetoothManager
import com.ibao.premescla.BuildConfig
import com.ibao.premescla.R
import com.ibao.premescla.models.Orden
import com.ibao.premescla.ui.mod1.main.views.MainActivityViewModel
import com.ibao.premescla.utils.appContext
import kotlinx.android.synthetic.main.content_main_mezcla.*
import java.util.*

class MainMezclaActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    private var viewModel: MainActivityViewModel? = null

    lateinit var MENU: Menu
    
    private val mBroadcastReceiver1: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action: String? = intent!!.getAction()
            if (action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                val state: Int = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                when (state) {
                    BluetoothAdapter.STATE_OFF -> {
                        MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@MainMezclaActivity, R.drawable.ic_bluetooth_disabled_black_24dp));
                    }
                    BluetoothAdapter.STATE_TURNING_OFF -> {
                        MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@MainMezclaActivity, R.drawable.ic_settings_bluetooth_black_24dp));
                    }
                    BluetoothAdapter.STATE_ON -> {
                        MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@MainMezclaActivity, R.drawable.ic_bluetooth_black_24dp));
                    }
                    BluetoothAdapter.STATE_TURNING_ON -> {
                        MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@MainMezclaActivity, R.drawable.ic_settings_bluetooth_black_24dp));
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
                    MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@MainMezclaActivity, R.drawable.ic_bluetooth_connected_black_24dp));
                }
                BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
                    // Toast.makeText(ctx,"disconected",Toast.LENGTH_SHORT).show()
                    MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@MainMezclaActivity, R.drawable.ic_bluetooth_black_24dp));
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
        setContentView(R.layout.activity_main_mezcla)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = getString(R.string.tittle_all_orders)


        // Setup our ViewModel
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

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
        menuInflater.inflate(R.menu.main, menu)
        MENU = menu


        var  mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        when {
            mBluetoothAdapter == null -> {
                //Toast.makeText(ctx,"null", Toast.LENGTH_SHORT).show()
                MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@MainMezclaActivity, R.drawable.ic_warning_black_24dp))
            }
            mBluetoothAdapter.isEnabled -> {

                if(!isConnected(BluetoothManager.getInstance())){

               //     Toast.makeText(ctx,"disconected", Toast.LENGTH_SHORT).show()
                    MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@MainMezclaActivity, R.drawable.ic_bluetooth_black_24dp));

                }else{
              //      Toast.makeText(ctx,"Conectado ", Toast.LENGTH_SHORT).show()
                    MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@MainMezclaActivity, R.drawable.ic_bluetooth_connected_black_24dp));
                }

            }
            else -> {
                MENU.getItem(0).setIcon(ContextCompat.getDrawable(this@MainMezclaActivity, R.drawable.ic_bluetooth_disabled_black_24dp))
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
    private fun showDialog() {
        val dialog = Dialog(this@MainMezclaActivity)

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
        viewModel!!.pairedDeviceList.observe(this@MainMezclaActivity, Observer(adapter::updateList))
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
        if (id == R.id.action_version) {
            try {
                Toast.makeText(baseContext, "Versión " + BuildConfig.VERSION_NAME + " code." + BuildConfig.VERSION_CODE /*+" db."+ ConexionSQLiteHelper.VERSION_DB*/, Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(baseContext, e.toString(), Toast.LENGTH_LONG).show()
            }
            return true
        }
        return if (id == R.id.action_logout) { /*
            if(new TareoDAO(ctx).listAll_UPLOAD().size()>0) {

                Snackbar snackbar= Snackbar.make(myFragment.getView(),"Falta finalizar todas sus labores y sincronizar",Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.red_pastel));
                snackbar.setActionTextColor(ContextCompat.getColor(this,R.color.white));
                snackbar.setAction("Sincronizar", v1 -> startActivity(new Intent(this, ActivityUpload.class)));
                snackbar.show();

            }else{
                SharedPreferencesManager.deleteUser(ctx);
                startActivity(new Intent(this, ActivityPreloader.class));
            }

             */
            true
        } else super.onOptionsItemSelected(item)
    }


    companion object{

        val filter_none = 0
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
                val dialog= AlertDialog.Builder(this).setMessage("Hello").setPositiveButton("Ok", DialogInterface.OnClickListener { dialogInterface, i ->
                    finish()
                }).show()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }
    val TAG = "MainActivity.tk"
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
        //todo: quita
        /*
        var adapter = RViewAdapterListOrdenes(this@MainMezclaActivity,temp)
        adapter.setOnClicListener {
            val pos = myRView!!.getChildAdapterPosition(it)
            val intent = Intent(this@MainMezclaActivity, ActivityOrden::class.java)
            val orden = adapter.getOrden(pos)
            intent.putExtra("orden", orden)
            Log.d(TAG,"pos="+orden.ordenCode)
            startActivity(intent)
        }

        myRView!!.adapter = adapter
*/
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
                //todo: quita
                //viewModel!!.refreshPairedDevices()
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
