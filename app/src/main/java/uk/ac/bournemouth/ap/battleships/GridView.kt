package uk.ac.bournemouth.ap.battleships

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.content.withStyledAttributes
import androidx.core.view.GestureDetectorCompat
import org.example.student.battleshipgame.StudentBattleshipOpponent
import org.example.student.battleshipgame.StudentBattleshipGrid
import org.example.student.battleshipgame.StudentShip
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid
import uk.ac.bournemouth.ap.battleshiplib.GuessCell
import uk.ac.bournemouth.ap.battleshiplib.GuessResult
import kotlin.math.floor
import kotlin.random.Random


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

    private var _exampleString: String? = null // TODO: use a default from R.string...
    private var _exampleColor: Int = Color.RED // TODO: use a default from R.color...
    private var _exampleDimension: Float = 0f // TODO: use a default from R.dimen...

    private val textPaint: TextPaint = TextPaint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        textAlign = Paint.Align.LEFT
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
    private var textWidth: Float = 0f
    private var textHeight: Float = 0f

    /**
     * The text to draw
     */
    var exampleString: String?
        get() = _exampleString
        set(value) {
            _exampleString = value
            invalidateTextPaintAndMeasurements()
        }

    /**
     * The font color
     */
    var exampleColor: Int
        get() = _exampleColor
        set(value) {
            _exampleColor = value
            invalidateTextPaintAndMeasurements()
        }

    /**
     * In the example view, this dimension is the font size.
     */
    var exampleDimension: Float
        get() = _exampleDimension
        set(value) {
            _exampleDimension = value
            invalidateTextPaintAndMeasurements()
        }

    /**
     * In the example view, this drawable is drawn above the text.
     */
    var exampleDrawable: Drawable? = null
        set(value){
            field = value
            invalidate()
        }



    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        context.withStyledAttributes(attrs, R.styleable.GridView, defStyle, R.style.Widget_Theme_Battleships_GridView){
            _exampleString = getString(R.styleable.GridView_exampleString)
            _exampleColor = getColor(R.styleable.GridView_exampleColor, exampleColor)
            // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
            // values that should fall on pixel boundaries.
            _exampleDimension = getDimension(
                R.styleable.GridView_exampleDimension,
                exampleDimension)

            if (hasValue(R.styleable.GridView_exampleDrawable)) {
                exampleDrawable = getDrawable(
                    R.styleable.GridView_exampleDrawable)
                exampleDrawable?.callback = this@GridView
            }
        }


        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements()
    }

    private fun invalidateTextPaintAndMeasurements() {
        textPaint.let {
            it.textSize = exampleDimension
            it.color = exampleColor
            textWidth = it.measureText(exampleString ?: "")
            textHeight = it.fontMetrics.bottom
        }
        requestLayout()
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


        override fun onSingleTapConfirmed(ev: MotionEvent): Boolean {

            val column = floor((ev.x - gridLeft) / cellWidth).toInt()
            val row = floor((ev.y - gridTop) / cellWidth).toInt()

            grid.shootAt(column, row)



//            var rowsClicked : MutableList<Int> = mutableListOf()
//            var columnsClicked : MutableList<Int> = mutableListOf()
//
//            Log.d(LOGTAG, "ROWS: $rowsClicked, COLUMNS: $columnsClicked")

//            if((rowsClicked.any(){it.equals(row.toInt())}) && (columnsClicked.any(){ it.equals(column.toInt()) })){
//                Log.d(LOGTAG, "CELL HAS BEEN CLICKED $rowsClicked, $columnsClicked")
//            }else{
//                rowsClicked.add(row.toInt())
//                columnsClicked.add(column.toInt())
//                Log.d(LOGTAG, "CELL FIRST CLICK")
//            }


            Log.d(LOGTAG, " row=$row , column=$column ")
            return true
        }

    }      // End of myGestureListener class

    companion object{
        const val LOGTAG = "GUESS GRID"
    }

}