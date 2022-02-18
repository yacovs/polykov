package yacov.sapiashvili.polykov.ui.customviews.polygon

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import androidx.core.content.res.ResourcesCompat
import yacov.sapiashvili.polykov.R

class PolygonGenerator (){
    var path: Path = Path()
    var x:Float = 0f
    var y:Float = 0f
    init {
         path = Path()
    }
    fun setRectangle (x: Int, y: Int){
        path.reset()
        path.addRect(x.toFloat(), 0f,0f,y.toFloat(),Path.Direction.CW)
    }
    fun setPolygon(x: Float, y: Float, radius: Float, numOfPt: Int) {
        //2.0 * Math.PI = 2π, which means one circle(360)
        //The polygon total angles of the sides must equal 360 degree.
        val section =  2.0 * Math.PI / numOfPt
        path.reset()
        path.moveTo(
            ((x  + radius * Math.cos(0.0)).toFloat()) ,
            ((y + radius * Math.sin(0.0)).toFloat())
        )
        for (i in 1 until numOfPt) {
            path.lineTo(
                (x + radius * Math.cos(section * i)).toFloat(),
                (y + radius * Math.sin(section * i)).toFloat()
            )
        }
        this.x = x
        this.y = y
        path.close()
    }
}