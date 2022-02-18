package yacov.sapiashvili.polykov.ui.customviews.rectangle


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import yacov.sapiashvili.polykov.R

class RectangleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    var paint : Paint = Paint().apply {
        style = Paint.Style.FILL
        color = ResourcesCompat.getColor(resources, R.color.purple_200,null);
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(width.toFloat(),0f,0f,height.toFloat(),paint)
    }
}