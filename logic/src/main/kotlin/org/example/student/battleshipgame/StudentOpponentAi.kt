package org.example.student.battleshipgame

import uk.ac.bournemouth.ap.battleshiplib.GuessCell
import uk.ac.bournemouth.ap.battleshiplib.GuessResult
import kotlin.random.Random


class StudentOpponentAi {

    private val random = Random

    fun shootAtPlayer(grid: StudentBattleshipGrid): GuessResult {
        val column = random.nextInt(0,grid.columns)
        val row = random.nextInt(0,grid.rows)

        return grid.shootAt(column, row)
    }

}