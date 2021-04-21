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
import org.example.student.battleshipgame.*
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid
import uk.ac.bournemouth.ap.battleshiplib.GuessCell
import java.lang.IllegalStateException
import kotlin.math.floor

class GridView : GridViewBase {

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

    override val colCount: Int get() = grid.columns
    override val rowCount: Int get() = grid.rows


    private val gridListener = BattleshipGrid.BattleshipGridListener { grid, column, row ->
        invalidate()
    }
    var grid: StudentBattleshipGrid = StudentBattleshipGrid()
    set(value) {
        field.removeOnGridChangeListener(gridListener)
        field = value
        value.addOnGridChangeListener(gridListener)
        onSizeChanged(width,height,width,height)
        invalidate()
    }

    init{

        grid.addOnGridChangeListener(gridListener)

    }

    private val hitPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.RED
    }
    private val sunkPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLUE
    }
    private val missPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.LTGRAY
    }

    override fun onDraw(canvas: Canvas) {
        drawGrid(canvas)

        for(column in 0 until colCount){
            for(row in 0 until rowCount){
                val cell = grid[column, row]
                val canvasX = column * cellWidth + gridLeft
                val canvasY = row * cellWidth + gridTop
                when(cell){
                    is GuessCell.HIT -> {
                        canvas.drawCircle(canvasX+0.5f*cellWidth, canvasY+0.5f*cellWidth, 0.4f*cellWidth ,hitPaint)
                    }
                    GuessCell.MISS -> {
                        canvas.drawCircle(canvasX+0.5f*cellWidth, canvasY+0.5f*cellWidth, 0.4f*cellWidth ,missPaint)
                    }
                    is GuessCell.SUNK -> {
                        canvas.drawCircle(canvasX+0.5f*cellWidth, canvasY+0.5f*cellWidth, 0.4f*cellWidth ,sunkPaint)
                    }
                    GuessCell.UNSET -> {}
                }
            }
        }

    }

    private val myGestureDetector = GestureDetectorCompat(context, MyGestureListener())

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return myGestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }

    private inner class MyGestureListener: GestureDetector.SimpleOnGestureListener() {

        override fun onDown(ev: MotionEvent): Boolean {
            return true
        }


        override fun onSingleTapUp(ev: MotionEvent): Boolean {

            val column = floor((ev.x - gridLeft) / cellWidth).toInt()
            val row = floor((ev.y - gridTop) / cellWidth).toInt()

            //checks if that coordinate has already been guessed
            if(grid[column, row] != GuessCell.UNSET){
                Log.d(LOGTAG, "COORDINATE ALREADY GUESSED")
            }else{
                grid.shootAt(column, row)
                turn++
            }

            Log.d(LOGTAG, " row=$row , column=$column")
            return true
        }

    }// End of myGestureListener class

    companion object{
        const val LOGTAG = "GridView"
        var turn = 0
    }

}