package uk.ac.bournemouth.ap.battleships

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.content.withStyledAttributes
import androidx.core.view.GestureDetectorCompat
import org.example.student.battleshipgame.StudentShip

/**
 * TODO: document your custom view class.
 */
class PlaceShipView : GridViewBase {

    override val colCount: Int get() = 10
    override val rowCount: Int get() = 10

    var row = 0f
    var column = 0f

    private val _shipList = mutableListOf<StudentShip>()
    private val shipList : List<StudentShip> get() = _shipList

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {

    }

    private fun invalidateTextPaintAndMeasurements() {
        requestLayout()
    }


    override fun onDraw(canvas: Canvas) {
        drawGrid(canvas)
        drawShips(canvas)

    }

    private val shipPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.LTGRAY
    }

    private fun drawShips(canvas: Canvas){
        val gridLeft = offsetLeft+paddingLeft
        val gridTop = offsetTop+paddingTop
        val shipMargins = cellWidth*0.1f

        for(ship in shipList){
            val shipLeft = ship.left *cellWidth + gridLeft + shipMargins
            val shipTop = ship.top * cellWidth + gridTop + shipMargins
            val shipRight = (ship.right+1) * cellWidth + gridLeft - shipMargins
            val shipBottom = (ship.bottom+1)* cellWidth + gridTop - shipMargins
            canvas.drawRect(shipLeft, shipTop, shipRight, shipBottom, shipPaint)
        }
        invalidate()
    }


    private val myGestureDetector = GestureDetectorCompat(context, MyGestureListener())

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return myGestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }

    private inner class MyGestureListener: GestureDetector.SimpleOnGestureListener() {

        override fun onDown(ev: MotionEvent): Boolean {
            return true
        }

        override fun onSingleTapConfirmed(ev: MotionEvent): Boolean {
            row = ev.x / 100
            column = ev.y / 100
            val newShip = StudentShip(column.toInt(), row.toInt(), column.toInt()+3, row.toInt())
            _shipList.add(newShip)
            Log.d(LOGTAG, "ST row=$row , column=$column")
            return true
        }

        override fun onDoubleTap(ev: MotionEvent): Boolean {
            row = ev.x / 100
            column = ev.y / 100
            val newShip = StudentShip(column.toInt(), row.toInt(), column.toInt(), row.toInt()+3)
            _shipList.add(newShip)
            Log.d(LOGTAG, " DT row=$row , column=$column")
            return true
        }


    }      // End of myGestureListener class


    companion object {         // declare a constant (must be in the companion)
        const val LOGTAG = "DetectColumnsandRows"
    }


}