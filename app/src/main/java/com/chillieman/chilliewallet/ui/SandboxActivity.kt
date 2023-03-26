package com.chillieman.chilliewallet.ui

import android.R
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.text.TextPaint
import android.widget.TextView
import com.chillieman.chilliewallet.databinding.ActivitySandboxBinding
import com.chillieman.chilliewallet.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class SandboxActivity : BaseActivity() {
    private lateinit var binding: ActivitySandboxBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySandboxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        makeItGradiant(binding.tvGradiant)
        makeItGradiant(binding.tvGradiant2)


    }

    private fun makeItGradiant(tv: TextView) {
        val paint: TextPaint = tv.paint
        val width = paint.measureText("Tianjin, China")

        val textShader: Shader = LinearGradient(
            0f, 0f, width, tv.textSize, intArrayOf(
                Color.parseColor("#F97C3C"),
                Color.parseColor("#FDB54E"),
                Color.parseColor("#64B678"),
                Color.parseColor("#478AEA"),
                Color.parseColor("#8446CC")
            ), null, Shader.TileMode.CLAMP
        )
        tv.paint.shader = textShader
    }
}
