package uk.ac.bournemouth.ap.battleships

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import org.example.student.battleshipgame.StudentBattleshipGrid
import org.example.student.battleshipgame.StudentBattleshipOpponent
import org.example.student.battleshipgame.StudentOpponentAi
import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid
import uk.ac.bournemouth.ap.battleshiplib.GuessCell

class PlayerGridView : GridViewBase {
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

    override val colCount: Int get() = BattleshipGrid.DEFAULT_COLUMNS
    override val rowCount: Int get() = BattleshipGrid.DEFAULT_ROWS

    val playerShipList = PlaceShipView.playerShipList

    var grid: StudentBattleshipGrid = StudentBattleshipGrid(StudentBattleshipOpponent(colCount, rowCount, playerShipList))

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
        drawShips(canvas)
        drawCell(canvas)
    }

    private val shipPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.LTGRAY
    }

    private fun drawShips(canvas: Canvas){
        val gridLeft = offsetLeft+paddingLeft
        val gridTop = offsetTop+paddingTop
        val shipMargins = cellWidth*0.1f

        for (ship in playerShipList){
            val shipLeft = ship.left *cellWidth + gridLeft + shipMargins
            val shipTop = ship.top * cellWidth + gridTop + shipMargins
            val shipRight = (ship.right+1) * cellWidth + gridLeft - shipMargins
            val shipBottom = (ship.bottom+1)* cellWidth + gridTop - shipMargins
            canvas.drawRect(shipLeft, shipTop, shipRight, shipBottom, shipPaint)
        }
        invalidateTextPaintAndMeasurements()
    }

    private fun drawCell(canvas: Canvas){
        if(computerTurn < GridView.turn){

            val shot = StudentOpponentAi().shootAtPlayer(grid)
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
            computerTurn++
        }
    }



    companion object {         // declare a constant (must be in the companion)
        const val LOGTAG = "PlayerGridView"
        var computerTurn = 0
    }

    



}