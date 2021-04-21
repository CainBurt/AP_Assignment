package org.example.student.battleshipgame

import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid
import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent
import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent.ShipInfo
import java.lang.IllegalArgumentException
import kotlin.random.Random
/**
 * Suggested starting point for implementing an opponent class. Please note that your constructor
 * should test that it gets correct parameters. These tests should not be done in the test driver,
 * but actually here.
 *
 * T_ODO Create a constructor that creates a game given dimensions and a list of placed ships
 * T_ODO Create a way to generate a random game
 */
open class StudentBattleshipOpponent(
        final override val columns: Int,
        final override val rows: Int, ships: List<StudentShip>) : BattleshipOpponent {

    final override val ships:List<StudentShip>

    constructor(columns: Int = 10, rows: Int = 10, shipSizes: IntArray = BattleshipGrid.DEFAULT_SHIP_SIZES, random: Random = Random) : this(columns, rows, randomGame(columns, rows, shipSizes, random))

    init{
        //checks ships are valid and do not overlap
        val validShips = mutableListOf<StudentShip>()
        for(ship in ships){
            if(ship.right >= columns || ship.bottom >= rows){
                throw IllegalArgumentException("Ship out of bounds")
            }
            if(validShips.any{ it.overlaps(ship) }){
                throw IllegalArgumentException("Ship overlaps")
            }
            validShips.add(ship)
        }

        this.ships = validShips

    }

    /**
     * Determine whether there is a ship at the given coordinate. If so, provide the shipInfo (index+ship)
     * otherwise `null`.
     */
    override fun shipAt(column: Int, row: Int): ShipInfo<StudentShip>? {
        for(index in ships.indices){
            val ship = ships[index]
            if(ship.isCoordinateInShip(column, row)){
                return ShipInfo(index, ship)
            }
        }
        return null
    }

    companion object{

        /**for each ship size in the list generate a random x and y and orientation,
         * put it on the board and make sure it fits and doesnt over lap. Create a ship object and add it to the list.**/
        fun randomGame(columns: Int, rows: Int, shipSizes: IntArray, random: Random): List<StudentShip>{
            val shipList = mutableListOf<StudentShip>()

            for (shipSize in shipSizes){

                var newShip: StudentShip

                do {

                    newShip = if(columns < shipSize || (rows >= shipSize && random.nextBoolean())){ //vertical
                        val shipRow = random.nextInt(rows-shipSize+1)
                        val shipColumn = random.nextInt(columns)
                        StudentShip(shipRow, shipColumn, shipRow + shipSize-1, shipColumn)
                    }else{ //horizontal
                        val shipRow = random.nextInt(rows)
                        val shipColumn = random.nextInt(columns-shipSize+1)
                        StudentShip(shipRow, shipColumn, shipRow, shipColumn + shipSize-1)
                    }


                } while(shipList.any{ it.overlaps(newShip)})
                shipList.add(newShip)


            }//for

            return shipList
        }

    }
}

