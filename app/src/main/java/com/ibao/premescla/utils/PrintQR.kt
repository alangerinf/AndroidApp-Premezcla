package com.ibao.premescla.utils

import android.util.Log

object PrintQR {
    val TAG = PrintQR.javaClass.simpleName
    private val print_num = 0
    fun print(mensagge: String) {
        Log.d(TAG,mensagge);
        val print_size : Int =2
        val error_level: Int=3
        //val bitmap: Bitmap = BitmapUtil.generateBitmap(mensagge, 9, 700, 700)
        /*
        if (bitmap != null) {
            mImageView.setImageDrawable(BitmapDrawable(bitmap))
        }

         */
        BluetoothUtil.sendData(ESCUtil.alignCenter())
        SunmiPrintHelper.getInstance().setAlign(1)

        if (!BluetoothUtil.isBlueToothPrinter) {
            SunmiPrintHelper.getInstance().printQr(mensagge, print_size, error_level)
            SunmiPrintHelper.getInstance().feedPaper()
        } else {
            if (print_num == 0) {
                BluetoothUtil.sendData(ESCUtil.getPrintQRCode(mensagge, print_size))
                BluetoothUtil.sendData(ESCUtil.nextLine(3))
            } else {
                BluetoothUtil.sendData(ESCUtil.getPrintDoubleQRCode(mensagge, mensagge, print_size, error_level))
                BluetoothUtil.sendData(ESCUtil.nextLine(3))
            }
        }
    }

}