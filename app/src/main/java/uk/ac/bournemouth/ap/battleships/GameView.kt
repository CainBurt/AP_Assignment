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

    private var squareWidth: Float = 0f
    private var squareSpacing: Float = 0f
    private var squareSpacingRatio: Float = 0.1f

    private val gridPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLUE
    }
    private val noPlayerPaint: Paint= Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.WHITE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val diameterX = w/(colCount + (colCount+1)*squareSpacingRatio)
        val diameterY = h/(rowCount + (rowCount+1)*squareSpacingRatio)

        squareWidth = minOf(diameterX, diameterY)
        squareSpacing = squareWidth*squareSpacingRatio
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val gameWidth: Float = colCount * (squareWidth+squareSpacing) + squareSpacing
        val gameHeight: Float = rowCount * (squareWidth+squareSpacing) + squareSpacing

        //draw the game board
        canvas.drawRect(0f, 0f, gameWidth, gameHeight, gridPaint)

        //val radius = circleDiameter / 2f
        for (col in 0 until colCount) {
            for (row in 0 until rowCount) {
                // We will later on want to use the game data to determine this
                val paint = noPlayerPaint
                // Drawing squares uses the width and spacing
                val cx = squareSpacing + ((squareWidth + squareSpacing) * col)
                val cy = squareSpacing + ((squareWidth + squareSpacing) * row)

                //canvas.drawCircle(cx, cy, radius, paint)
                canvas.drawRect((cy), (cx), (cy + colCount*10) , (cx + rowCount*10) , paint)
                //canvas.drawRect(row.toFloat(), col.toFloat(), rowCount.toFloat(), colCount.toFloat() ,paint)
                //canvas.drawLine((cy), (cx), (cy + colCount) , (cx + rowCount) , paint)

//                /**Vertical Line**/
//                canvas.drawLine(col.toFloat(), 0f, col.toFloat(), canvas.height.toFloat(), paint)
//                /**Horizontal Line**/
//                canvas.drawLine(0f, row.toFloat(), canvas.width.toFloat(), row.toFloat(), paint)
            }
        }


    }
}


