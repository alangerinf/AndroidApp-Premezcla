package com.ibao.premescla.utils

import android.util.Log
import com.ibao.premescla.models.Recipe

object PrintQR {
    val TAG = PrintQR.javaClass.simpleName
    private val print_num = 0
    fun printQR(qrContent: String, codLote: String,codTancada: String,recipe: Recipe) {
        Log.d(TAG,qrContent);
        val print_size : Int =15
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
            SunmiPrintHelper.getInstance().printQr(qrContent, print_size, error_level)
            SunmiPrintHelper.getInstance().printText("Lote: "+codLote,50f,true,false)
            SunmiPrintHelper.getInstance().printText("\nTancada: "+codLote,50f,true,false)
            for (r in recipe.ordenDetalleList){
                SunmiPrintHelper.getInstance().printText("\n"+r.dosis+""+r.units+" "+r.productName,30f, false,false)
            }

            SunmiPrintHelper.getInstance().feedPaper()
            SunmiPrintHelper.getInstance().feedPaper()
            SunmiPrintHelper.getInstance().cutpaper()
        } else {
            if (print_num == 0) {
                BluetoothUtil.sendData(ESCUtil.getPrintQRCode(qrContent, print_size))
                BluetoothUtil.sendData(ESCUtil.nextLine(nextLines))
            } else {
                BluetoothUtil.sendData(ESCUtil.getPrintDoubleQRCode(qrContent, qrContent, print_size, error_level))
                BluetoothUtil.sendData(ESCUtil.nextLine(nextLines))
            }
        }
    }

}