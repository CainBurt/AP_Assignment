package uk.ac.bournemouth.ap.battleships

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.core.view.GestureDetectorCompat
import org.example.student.battleshipgame.StudentShip
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid

/**
 * TODO: document your custom view class.
 */
class PlaceShipView : GridViewBase {

    override val colCount: Int get() = BattleshipGrid.DEFAULT_COLUMNS
    override val rowCount: Int get() = BattleshipGrid.DEFAULT_ROWS

    var row = 0f
    var column = 0f
    var isVertical = true
    lateinit var newPlayerShip : StudentShip
    //gets the ship sizes
    val shipSizes: IntArray = BattleshipGrid.DEFAULT_SHIP_SIZES
    var counter = 0



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

        for(ship in playerShipList){
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

            //check ships dont overlap
            row = ev.x / 100
            column = ev.y / 100
            isVertical = true
            newPlayerShip = StudentShip(column.toInt(), row.toInt(), column.toInt()+ shipSizes[counter] -1, row.toInt())

            //checks ships dont overlap eachother and are within the grid size
            if(!playerShipList.any{it.overlaps(newPlayerShip)} && newPlayerShip.bottom < colCount){
                counter += 1
                _playerShipList.add(newPlayerShip)
            }

            Log.d(LOGTAG, "ST row=$row , column=$column")
            return true
        }

        override fun onDoubleTap(ev: MotionEvent): Boolean {


            row = ev.x / 100
            column = ev.y / 100
            isVertical = false
            val newPlayerShip = StudentShip(column.toInt(), row.toInt(), column.toInt(), row.toInt() + shipSizes[counter] -1)

            //checks ships dont overlap eachother and are within the grid size
            if (!playerShipList.any { it.overlaps(newPlayerShip)} && newPlayerShip.right < rowCount) {
                counter += 1
                _playerShipList.add(newPlayerShip)
            }

            Log.d(LOGTAG, " DT row=$row , column=$column")
            return true
        }



    }      // End of myGestureListener class


    companion object {         // declare a constant (must be in the companion)
        const val LOGTAG = "DetectColumnsandRows"
        val _playerShipList = mutableListOf<StudentShip>()
        val playerShipList : List<StudentShip> get() = _playerShipList

    }

    



}