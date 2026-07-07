package com.example.ana_anlume.Home.pertemuan_13.generate

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

object QRGenerator {
    fun generateQRCode(text: String, size: Int = 500): Bitmap? {
        val multiFormatWriter = MultiFormatWriter()
        return try {
            val bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, size, size)
            val barcodeEncoder = BarcodeEncoder()
            barcodeEncoder.createBitmap(bitMatrix)
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }
}
