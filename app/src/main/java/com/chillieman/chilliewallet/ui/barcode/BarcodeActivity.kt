package com.chillieman.chilliewallet.ui.barcode

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.chillieman.chilliewallet.R
import com.chillieman.chilliewallet.ui.base.BaseActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.barcode_activity.*


class BarcodeActivity : BaseActivity() {
    var scannedValue = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.barcode_activity)

        btn_scan_qr_code.setOnClickListener {
            IntentIntegrator(this).apply {
                setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                setPrompt("Scan a QR Code")
                setOrientationLocked(false)
//            setCameraId(0);  // Use a specific camera of the device
//            setBeepEnabled(false)
//            setBarcodeImageEnabled(true)
                initiateScan()
            }
        }

        btn_generate_qr_code.setOnClickListener {
            try {
                img_generated_qr_code.setImageBitmap(
                    BarcodeEncoder().encodeBitmap(
                        et_qr_code_generation.text.toString(),
                        BarcodeFormat.QR_CODE,
                        800,
                        800
                    )
                )
            } catch (e: Exception) {
                Log.e("Chillie", "Oh Shit, there's an error when creating QR Code", e)
            }
        }

    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                tv_scanned_qr_value.text = result.contents
                //TODO: Copy this to clipboard
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}