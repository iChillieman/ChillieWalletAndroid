package com.chillieman.chilliewallet.ui.barcode

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
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

        binding.etQrCodeGeneration.setOnEditorActionListener { view, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                generateQRCode(view.text.toString())
            }
            false
        }
        binding.btnScanQrCode.setOnClickListener {
            IntentIntegrator(this).apply {
                setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                setPrompt("Scan a QR Code")
                setOrientationLocked(false)
                setBeepEnabled(false)
//            setCameraId(0);  // Use a specific camera of the device
//            setBarcodeImageEnabled(true)
                initiateScan()
            }
        }

        binding.btnGenerateQrCode.setOnClickListener {
            generateQRCode(binding.etQrCodeGeneration.text.toString())
        }
    }

    private fun generateQRCode(payload: String) {
        try {
            binding.imgGeneratedQrCode.setImageBitmap(
                BarcodeEncoder().encodeBitmap(
                    payload,
                    BarcodeFormat.QR_CODE,
                    800,
                    800
                )
            )
        } catch (e: Exception) {
            Log.e("Chillie", "Oh Shit, there's an error when creating QR Code", e)
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
