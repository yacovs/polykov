package yacov.sapiashvili.polykov.ui.customviews.polygon

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import yacov.sapiashvili.polykov.R

class PolygonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    var paint : Paint = Paint().apply {
        style = Paint.Style.FILL
        color = ResourcesCompat.getColor(resources, R.color.purple_500,null);
    }
    var debugpaint : Paint = Paint().apply {
        style = Paint.Style.STROKE
        color = ResourcesCompat.getColor(resources, R.color.purple_700,null);
        strokeWidth = 5f
    }
    private var polyRadius = 1f
    private var rotationAmountPerFrame: Float = 0f
    private lateinit var callback: PolygonAnimationProgress
    private val FPS_RATE_PER_SECOUND = 60
    var mAnimateOnDisplay: Boolean = false
    var numberOfPoint = 8 //default
    private val polygonGenerator: PolygonGenerator = PolygonGenerator()
    var targetDegreeOfRotation = rotation;

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.d("bla", "onDraw: ")
        polygonGenerator.let { canvas.drawPath(polygonGenerator.path, paint) }
        canvas.drawCircle(polygonGenerator.x,polygonGenerator.y,polyRadius,debugpaint)
        checkForRotationAnimationStopCondition()
        when(mAnimateOnDisplay){
            true -> {invalidate()}
            false -> {callback.animationComplete()}
        }
    }

    private fun checkForRotationAnimationStopCondition() {
        mAnimateOnDisplay = if (rotation < targetDegreeOfRotation) {
            rotation +=rotationAmountPerFrame
            true
        }else{
            false
        }
    }

    fun startRotation(angel: Float) {
        mAnimateOnDisplay = true
        targetDegreeOfRotation = rotation + angel
        rotationAmountPerFrame = angel / FPS_RATE_PER_SECOUND
        if (width == 0 || height == 0) {
            return
        }
        // get the center point of the polygon
        val polyCenterX = width / 2.0f
        val polyCenterY = height / 2.0f
        polyRadius = if (width > height) {
            height - polyCenterY
        } else {
            width - polyCenterX
        }
        polygonGenerator.setPolygon(polyCenterX, polyCenterY, polyRadius, numberOfPoint)
        invalidate();
    }

    fun setAnimationCallback(callback: PolygonAnimationProgress) {
        this.callback = callback;
    }
}