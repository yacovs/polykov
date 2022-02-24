package yacov.sapiashvili.polykov.ui.customviews.polygon

import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import yacov.sapiashvili.polykov.R

class PolygonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val ANIMATION_FPS = 60
    private var startingRotation: Float = 0f

    var paint : Paint = Paint().apply {
        style = Paint.Style.FILL
        color = ResourcesCompat.getColor(resources, R.color.purple_500,null);
    }

    var roatateMatrix :Matrix = Matrix()
    private var polyRadius = 1f
    private var rotationAmountPerFrame: Float = 0f
    private lateinit var callback: PolygonAnimationProgress
    var mAnimateOnDisplay: Boolean = false
    var numberOfPoint = 8 //default
    private val polygonGenerator: PolygonGenerator = PolygonGenerator()
    var targetDegreeOfRotation = rotation;

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val scaleFactor = polygonGenerator.recalculatePathScaling(width,height)
        roatateMatrix.reset()
        roatateMatrix.preRotate(
            rotationAmountPerFrame,
            polygonGenerator.polygonCenterX,
            polygonGenerator.polygonCenterY)

        roatateMatrix.postScale(
            1f +scaleFactor,
            1f +scaleFactor,
            polygonGenerator.polygonCenterX,
            polygonGenerator.polygonCenterY)

        polygonGenerator.path.transform(roatateMatrix);
        canvas.drawPath(polygonGenerator.path, paint)
        mAnimateOnDisplay = startingRotation <= targetDegreeOfRotation
        when(mAnimateOnDisplay){
            true -> {
                startingRotation += rotationAmountPerFrame
                invalidate() // animation did not finished, recall onDraw until rotation complete
            }
            false -> {callback.animationComplete()}
        }
    }

    fun startRotation(angel: Float) {
        mAnimateOnDisplay = true
        targetDegreeOfRotation = startingRotation + angel
        rotationAmountPerFrame = angel / ANIMATION_FPS
        if (width == 0 || height == 0) {
            return
        }

        if(polygonGenerator.path.isEmpty)
            generatePoly(numberOfPoint = numberOfPoint)
        invalidate();
    }
    fun generatePoly(polyCenterX: Float = width / 2.0f, polyCenterY: Float = height / 2.0f, numberOfPoint:Int){
        this.numberOfPoint = numberOfPoint

        // get the center point of the polygon
        polyRadius = if (width > height) {
            height - polyCenterY
        } else {
            width - polyCenterX
        }
        polygonGenerator.generat(polyCenterX, polyCenterY, polyRadius, numberOfPoint)
    }
    // callback to the alert the views container for animation completed
    fun setAnimationCallback(callback: PolygonAnimationProgress) {
        this.callback = callback;
    }
}