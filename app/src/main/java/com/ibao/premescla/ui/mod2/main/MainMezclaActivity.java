package com.ibao.premescla.ui.mod2.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.ibao.premescla.R;
import com.ibao.premescla.models.Tancada;
import com.ibao.premescla.ui.mod2.edit.EditSensorsActivity;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static android.Manifest.permission.CAMERA;

/**
 * Custom Scannner Activity extending from Activity to display a custom layout form scanner view.
 */
public class MainMezclaActivity extends AppCompatActivity implements
        DecoratedBarcodeView.TorchListener {

    private DecoratedBarcodeView barcodeScannerView;

    private String TAG = "QRScaner";

    private static FloatingActionButton fAButtonLinterna;
    private static boolean statusLight;

    private TancadaPresenter presenter;
    private ProgressBar progressBar;

    private CaptureManager capture;

    Context ctx;

    private View root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mod2_act_main);
        ctx= this;
        progressBar = findViewById(R.id.progressBar);
        statusLight=false;
        fAButtonLinterna = findViewById(R.id.fAButtonLinterna);

        root = findViewById(R.id.root);
        validarPermisos();

        fAButtonLinterna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(statusLight){
                    statusLight = false;
                 //   Toast.makeText(getBaseContext(),"apagando",Toast.LENGTH_SHORT).show();
                    barcodeScannerView.setTorchOff();

                }else {
                    statusLight = true;
                   // Toast.makeText(getBaseContext(), "encendiendo", Toast.LENGTH_SHORT).show();
                    barcodeScannerView.setTorchOn();
                }
            }
        });

        barcodeScannerView = (DecoratedBarcodeView)findViewById(R.id.zxing_barcode_scanner);
        barcodeScannerView.setTorchListener(this);

        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39, BarcodeFormat.CODABAR);
        barcodeScannerView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(formats));
        barcodeScannerView.initializeFromIntent(getIntent());
        barcodeScannerView.decodeContinuous(callback);

        beepManager = new BeepManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        barcodeScannerView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        barcodeScannerView.pause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    /**
     * Check if the device's camera has a Flashlight.
     * @return true if there is Flashlight, otherwise false.
     */
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    public void switchFlashlight(View view) {

    }

    public void changeMaskColor(View view) {
        Random rnd = new Random();
        int color = Color.argb(100, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        //viewfinderView.setMaskColor(color);
    }

    @Override
    public void onTorchOn() {
        fAButtonLinterna.setImageResource(R.drawable.ic_highlight_black_24dp);
    }

    @Override
    public void onTorchOff() {
        fAButtonLinterna.setImageResource(R.drawable.ic_light_white_off);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    private BeepManager beepManager;

    private boolean isGoToEdit = false;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {

            if(result!=null){
                String QR = result.getText();
                try {
                    int idTancada = Tancada.getIdparseFromQR(QR);
                    if(idTancada!=0){
                        presenter = new TancadaPresenter(MainMezclaActivity.this,idTancada);
                        presenter.requestAllData();
                        progressBar.setVisibility(View.VISIBLE);
                        if(!isGoToEdit){
                            isGoToEdit =true;
                            handler.post(runnableFound);
                            //handler.postDelayed(()-> isGoToEdit=false,2000);
                        }
                    }
                }catch (Exception e){

                }
            }


        }





        Runnable runnableFound = new Runnable() {
            @Override
            public void run() {
                beepManager.setBeepEnabled(false);
                beepManager.setVibrateEnabled(true);
                beepManager.playBeepSoundAndVibrate();
            }
        };


        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };


    private boolean validarPermisos(){
        if(Build.VERSION.SDK_INT< Build.VERSION_CODES.M){
            return true;
        }

        if((checkSelfPermission(CAMERA)== PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        if((shouldShowRequestPermissionRationale(CAMERA))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{CAMERA},100);
        }
        return false;

    }
    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainMezclaActivity.this);
        dialog.setTitle("Permisos Desactivados");
        dialog.setMessage("Debe aceptar todos los permisos para poder tomar fotos");
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                solicitarPermisosManual();
            }
        });
        dialog.show();
    }
    private void solicitarPermisosManual() {
        final CharSequence[] opciones = {
                "si",
                "no"
        };
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(MainMezclaActivity.this);
        alertOpciones.setTitle("¿Desea configurar los permisos de Forma Manual?");
        alertOpciones.setItems(
                opciones,
                (dialog, i) -> {
                    if(opciones[i].equals("si")){
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package",getPackageName(),null);
                        intent.setData(uri);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"Los permisos no fueron aceptados", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }

                });
        alertOpciones.show();
    }
    private void goToEdit(Tancada tancada) {
        Intent  i = new Intent(MainMezclaActivity.this, EditSensorsActivity.class);
        i.putExtra("tancada", tancada);
        startActivity(i);
    }
    public void showTancada(Tancada tancada) {
        goToEdit(tancada);
        handler.postDelayed(()->{
            isGoToEdit=false;
            progressBar.setVisibility(View.GONE);
        }
        ,1000);
    }
    Handler handler = new Handler();
    public void showError(String error) {

        progressBar.setVisibility(View.GONE);
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
        handler.post(()-> {
            isGoToEdit=false;

        });
    }
}
