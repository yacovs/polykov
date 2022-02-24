package yacov.sapiashvili.polykov.ui.customviews.polygon

import android.graphics.Path
import android.graphics.PathMeasure
import kotlin.math.cos
import kotlin.math.sin


class PolygonGenerator {
    private val TAG = "PolygonGenerator"
    var path: Path = Path()
    var polygonCenterX:Float = 0f
    var polygonCenterY:Float = 0f
    var numOfPt:Int = 3
    var polyRadius:Float = 1f
    init {
         path = Path()
    }

    fun recalculatePathScaling(width : Int,height : Int) : Float{
        var vorticesAxisIndex : Int
        val fullLength = when (width>height){
            true -> {
                vorticesAxisIndex = 1 // y axis point
                height
            }
            false -> {
                vorticesAxisIndex = 0 // y axis point
                width
            }
        }
        //find the closest point to the edges from the center of the polygon
        var minFromEnd : Float = (fullLength / 2).toFloat()
        var minFromStart : Float = (fullLength / 2).toFloat()
        var pm = PathMeasure(path, true)
        //polygon vortices
        val vorticePoint = floatArrayOf(0f, 0f)
        //get coordinates of the middle point
        for (i in 0 ..  numOfPt){
            pm.getPosTan(pm.length * i / numOfPt, vorticePoint, null)
            if(fullLength - vorticePoint[vorticesAxisIndex] > minFromEnd)
                minFromEnd = fullLength - vorticePoint[vorticesAxisIndex]
            if (vorticePoint[vorticesAxisIndex] < minFromStart)
                minFromStart = vorticePoint[vorticesAxisIndex]
        }
        return  (fullLength - minFromEnd + minFromStart + 1) / fullLength;
    }

    fun generat(polygonCenterX: Float, polygonCenterY: Float, radius: Float, numOfPt: Int) {
        //2.0 * Math.PI = 2π, which means one circle(360)
        //The polygon total angles of the sides must equal 360 degree.
        this.numOfPt = numOfPt;
        val section =  2.0 * Math.PI / numOfPt
        polyRadius = radius
        path.rewind()
        path.moveTo(
                ((polygonCenterX + radius * sin(0.0)).toFloat()),
                ((polygonCenterY + radius * cos(0.0)).toFloat())
        )
        for (i in 1 until numOfPt) {
            path.lineTo(
                    (polygonCenterX + radius * sin(section * i)).toFloat(),
                    (polygonCenterY + radius * cos(section * i)).toFloat()
            )
        }
        this.polygonCenterX = polygonCenterX
        this.polygonCenterY = polygonCenterY
        path.close()
    }
}