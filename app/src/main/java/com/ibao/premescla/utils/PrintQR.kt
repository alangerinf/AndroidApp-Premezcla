package com.ibao.premescla.utils

import android.util.Log
import com.ibao.premescla.models.Recipe
import com.ibao.premescla.models.Tancada
import kotlinx.coroutines.yield

object PrintQR {
    val TAG = PrintQR.javaClass.simpleName
    private val print_num = 0
    fun printQR(tancada: Tancada,recipe: Recipe) {
        Log.d(TAG,tancada.parseToQR());
        val print_size : Int =18
        val error_level: Int=3
        val nextLines=10
        //val bitmap: Bitmap = BitmapUtil.generateBitmap(mensagge, 9, 700, 700)
        /*
        if (bitmap != null) {
            mImageView.setImageDrawable(BitmapDrawable(bitmap))
        }
        */
        BluetoothUtil.sendData(ESCUtil.alignCenter())
        SunmiPrintHelper.getInstance().setAlign(1)

        if (!BluetoothUtil.isBlueToothPrinter) {
            SunmiPrintHelper.getInstance().printQr(tancada.parseToQR(), print_size, error_level)
            SunmiPrintHelper.getInstance().setAlign(0)
            SunmiPrintHelper.getInstance().printText(  "Cod. Orden:   "+tancada.codeOrden,30f,true,false)
            SunmiPrintHelper.getInstance().printText("\nCod. Lote:    "+tancada.codeLote,30f,true,false)
            SunmiPrintHelper.getInstance().printText("\nNro. Tancada: "+tancada.nroTancada,30f,true,false)
            SunmiPrintHelper.getInstance().printText("\nMojamiento :  "+tancada.mojamiento,30f,true,false)
            SunmiPrintHelper.getInstance().printText("\nProducto/Dosis:",30f,true,false)
            for (r in recipe.ordenDetalleList){
                SunmiPrintHelper.getInstance().printText("\n${r.productName} \t ${r.dosis} ${r.units} ",30f, false,false)
            }

            SunmiPrintHelper.getInstance().feedPaper()
            SunmiPrintHelper.getInstance().feedPaper()
            SunmiPrintHelper.getInstance().cutpaper()
        } else {
            if (print_num == 0) {
                BluetoothUtil.sendData(ESCUtil.getPrintQRCode(tancada.parseToQR(), print_size))
                BluetoothUtil.sendData(ESCUtil.nextLine(nextLines))
            } else {
                BluetoothUtil.sendData(ESCUtil.getPrintDoubleQRCode(tancada.parseToQR(), tancada.parseToQR(), print_size, error_level))
                BluetoothUtil.sendData(ESCUtil.nextLine(nextLines))
            }
        }
    }

}