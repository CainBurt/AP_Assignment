package uk.ac.bournemouth.ap.battleships

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class GameView: View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val colCount = 10
    private val rowCount = 10
    private var cellWidth = 0
    private var offsetLeft = 0
    private var offsetTop = 0


    //paints
    private val gridBackgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.CYAN
    }
    private val gridLinePaint: Paint= Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
    }

    val gridHeight get() = rowCount * cellWidth
    val gridWidth get() = colCount * cellWidth

    //ajusts for screen size
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val availableWidth = w - paddingLeft - paddingRight
        val availableHeight = h - paddingTop - paddingBottom
        cellWidth = minOf(availableWidth/ colCount, availableHeight/rowCount)
        offsetLeft = (availableWidth - (colCount * cellWidth))/2
        offsetTop = (availableHeight - gridHeight)/2

    }

    override fun onDraw(canvas: Canvas) {
        val gridTop = (paddingTop + offsetTop).toFloat()
        val gridLeft = (paddingLeft + offsetLeft).toFloat()
        val gridBottom = (paddingTop + offsetTop + gridHeight).toFloat()
        val gridRight = (paddingLeft + offsetLeft + gridWidth).toFloat()


        //draw the game board
        canvas.drawRect(gridLeft, gridTop, gridRight, gridBottom, gridBackgroundPaint)

        //draw vertical lines
        for(col in 0..colCount){
            val lineX = gridLeft+(col*cellWidth).toFloat()
            canvas.drawLine(lineX, gridTop, lineX, gridBottom, gridLinePaint)
        }

        //draw horizontal lines
        for(row in 0..rowCount){
            val lineY = gridTop+(row*cellWidth).toFloat()
            canvas.drawLine(gridLeft, lineY, gridRight, lineY, gridLinePaint)
        }


    }
}


