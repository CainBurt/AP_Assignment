package org.example.student.battleshipgame

import uk.ac.bournemouth.ap.battleshiplib.GuessCell
import uk.ac.bournemouth.ap.battleshiplib.GuessResult
import uk.ac.bournemouth.ap.battleshiplib.coordinates
import uk.ac.bournemouth.ap.lib.matrix.Matrix
import uk.ac.bournemouth.ap.lib.matrix.MutableMatrix
import kotlin.random.Random


class StudentOpponentAi {

    fun shootAtPlayer(grid: StudentBattleshipGrid): GuessResult {
        val random = Random
        var columnRandom = random.nextInt(0, grid.columns)
        var rowRandom = random.nextInt(0, grid.rows)


        do{
            columnRandom = random.nextInt(0, grid.columns)
            rowRandom = random.nextInt(0, grid.rows)

        }while(grid[columnRandom, rowRandom] != GuessCell.UNSET)

        return grid.shootAt(columnRandom, rowRandom)
    }

}