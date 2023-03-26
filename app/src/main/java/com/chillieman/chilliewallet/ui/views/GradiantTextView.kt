package com.chillieman.chilliewallet.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.transform
import com.chillieman.chilliewallet.R


@SuppressLint("AppCompatCustomView")
class GradiantTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {
//    private val gradient: Shader
    private var topColor = Color.WHITE
    private var bottomColor = Color.BLACK

    init {
        context.withStyledAttributes(attrs, R.styleable.GradiantTextView) {
            topColor = getColor(R.styleable.GradiantTextView_topColor, Color.WHITE)
            bottomColor = getColor(R.styleable.GradiantTextView_bottomColor, Color.BLACK)
        }


//        gradient = LinearGradient(
//            0f, 0f, 0f, 32f, intArrayOf(
//                topColor,
//                bottomColor,
//            ), null, Shader.TileMode.CLAMP
//        )

//        val textShader: Shader = LinearGradient(
//            0f, 0f, width.toFloat(), height.toFloat(), intArrayOf(
//                Color.parseColor("#F97C3C"),
//                Color.parseColor("#FDB54E"),
//                Color.parseColor("#64B678"),
//                Color.parseColor("#478AEA"),
//                Color.parseColor("#8446CC")
//            ), null, Shader.TileMode.CLAMP
//        )
//        paint.shader = gradient
    }

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            paint.shader  = LinearGradient(
                0f,
                0f,
                0f,
                height.toFloat(),
                topColor,
                bottomColor,
                Shader.TileMode.CLAMP
            )
//            paint.shader.transform {
//                this.postRotate(90f)
//            }
        }
    }

}