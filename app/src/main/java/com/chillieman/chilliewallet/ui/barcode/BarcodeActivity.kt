package com.chillieman.chilliewallet.ui.barcode

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.chillieman.chilliewallet.databinding.ActivityBarcodeBinding
import com.chillieman.chilliewallet.ui.base.BaseActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.BarcodeEncoder


class BarcodeActivity : BaseActivity() {
    private lateinit var binding: ActivityBarcodeBinding
    var scannedValue = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBarcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnScanQrCode.setOnClickListener {
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

        binding.btnGenerateQrCode.setOnClickListener {
            try {
                binding.imgGeneratedQrCode.setImageBitmap(
                    BarcodeEncoder().encodeBitmap(
                        binding.etQrCodeGeneration.text.toString(),
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

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                binding.tvScannedQrValue.text = result.contents
                //TODO: Copy this to clipboard
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}