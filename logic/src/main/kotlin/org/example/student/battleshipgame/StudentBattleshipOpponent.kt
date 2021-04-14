package org.example.student.battleshipgame

import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent
import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent.ShipInfo
import kotlin.random.Random
/**
 * Suggested starting point for implementing an opponent class. Please note that your constructor
 * should test that it gets correct parameters. These tests should not be done in the test driver,
 * but actually here.
 *
 * T_ODO Create a constructor that creates a game given dimensions and a list of placed ships
 * T_ODO Create a way to generate a random game
 */
open class StudentBattleshipOpponent(//T_ODO("Determine the rows for the grid in which the ships are hidden")
        override val rows: Int, //T_ODO("Determine the columns for the grid in which the ships are hidden")
        override val columns: Int, ships:List<StudentShip>) : BattleshipOpponent { //rows: Int, columns: Int, shipSize: IntArray, random: Random

    override val ships:List<StudentShip> = ships //T_ODO("Record the ships that are placed for this opponent")

    constructor(rows: Int, columns: Int, shipSizes: IntArray, random: Random) : this(rows, columns, randomGame(rows, columns, shipSizes, random))


    companion object{

        /**for each ship size in the list generate a random x and y and orientation,
         * put it on the board and make sure it fits and doesnt over lap. Create a ship object and add it to the list.**/
        fun randomGame(rows: Int, columns: Int, shipSizes: IntArray, random: Random): List<StudentShip>{
            val shipList = mutableListOf<StudentShip>()

            for (shipSize in shipSizes){

                var newShip: StudentShip

                do {

                    if(columns < shipSize || (rows >= shipSize && random.nextBoolean())){ //vertical
                        val shipRow = random.nextInt(rows-shipSize+1)
                        val shipColumn = random.nextInt(columns)
                        newShip = StudentShip(shipRow, shipColumn, shipRow + shipSize-1, shipColumn)
                    }else{ //horizontal
                        val shipRow = random.nextInt(rows)
                        val shipColumn = random.nextInt(columns-shipSize+1)
                        newShip = StudentShip(shipRow, shipColumn, shipRow, shipColumn + shipSize-1)
                    }


                } while(shipList.any{ it.overlaps(newShip)})
                shipList.add(newShip)


            }//for

            return shipList
        }

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
}

