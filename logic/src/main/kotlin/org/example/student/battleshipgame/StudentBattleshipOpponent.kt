package org.example.student.battleshipgame

import uk.ac.bournemouth.ap.battleshiplib.BattleshipGrid
import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent
import uk.ac.bournemouth.ap.battleshiplib.BattleshipOpponent.ShipInfo
import uk.ac.bournemouth.ap.lib.matrix.ArrayMutableIntMatrix
import uk.ac.bournemouth.ap.lib.matrix.MutableBooleanMatrix
import kotlin.random.Random
/**
 * Suggested starting point for implementing an opponent class. Please note that your constructor
 * should test that it gets correct parameters. These tests should not be done in the test driver,
 * but actually here.
 *
 * T_ODO Create a constructor that creates a game given dimensions and a list of placed ships
 * T_ODO Create a way to generate a random game
 */
open class StudentBattleshipOpponent(rows: Int, column: Int, ships:List<StudentShip>) : BattleshipOpponent { //rows: Int, columns: Int, shipSize: IntArray, random: Random

    override val rows:Int get() = BattleshipGrid.DEFAULT_ROWS //T_ODO("Determine the rows for the grid in which the ships are hidden")
    override val columns:Int get() = BattleshipGrid.DEFAULT_COLUMNS //T_ODO("Determine the columns for the grid in which the ships are hidden")
    override val ships:List<StudentShip> = ships //T_ODO("Record the ships that are placed for this opponent")

    constructor(rows: Int, column: Int, shipSizes: IntArray, random: Random) : this(rows, column, randomGame(rows, column, shipSizes, random))


    companion object{

        /**for each ship size in the list generate a random x and y and orientation,
         * put it on the board and make sure it fits and doesnt over lap. Create a ship object and add it to the list.**/
        fun randomGame(rows: Int, columns: Int, shipSizes: IntArray, random: Random): List<StudentShip>{
            var shipList = mutableListOf<StudentShip>()
            var matrix = MutableBooleanMatrix(rows, columns)


            for (shipSize in shipSizes){
                var shipRow = random.nextInt((rows - 0))
                var shipColumn = random.nextInt((columns - 0))
                val shipOrientation = random.nextBoolean()

                if(shipOrientation){ //true == vertical

                    //checks if ship is out of bounds
                    if((shipRow + shipSize) > rows){
                        return randomGame(rows, columns, shipSizes, random)
                    }else {
                        //checks ships dont overlap
                        for(ship in shipList){
                            if((shipRow >= ship.top && shipRow <= ship.bottom) && shipColumn >= ship.left){
                                return randomGame(rows, columns, shipSizes, random)
                            }
                        }

//                            if(matrix.contains(true)){
//                                return randomGame(rows, columns, shipSizes, random)
//                            }else{
//                                var i = 0
//                                do{
//                                    matrix[shipRow + i, shipColumn] = true
//                                    i++
//                                } while(i <= shipSize)
//                            }
//                        print("------------------------- \n")
//                        print(matrix)
                        shipList.add(StudentShip(shipRow, shipColumn, shipRow + (shipSize -1) , shipColumn ))

                    }

                }else{ //false == horizontal

                    //checks if ship is out of bounds
                    if((shipColumn + shipSize) > columns){
                        return randomGame(rows, columns, shipSizes, random)
                    }else{
                        //checks ships dont overlap
                        for(ship in shipList){
                            if((shipColumn >= ship.left && shipColumn <= ship.right) && shipRow >= ship.top){
                                return randomGame(rows, columns, shipSizes, random)
                            }
                        }

//                        if(matrix.elementAt(shipColumn)){
//                            return randomGame(rows, columns, shipSizes, random)
//                        }else{
//                            var i = 0
//                            do{
//                                matrix[shipRow , shipColumn+i] = true
//                                i++
//                            } while(i <= shipSize)
//                        }
//
//                        print("------------------------- \n")
//                        print(matrix)
                        shipList.add(StudentShip(shipRow, shipColumn, shipRow ,shipColumn + (shipSize -1)))
                    }

                }

            }

            return shipList
        }
    }

    /**
     * Determine whether there is a ship at the given coordinate. If so, provide the shipInfo (index+ship)
     * otherwise `null`.
     */
    override fun shipAt(column: Int, row: Int): ShipInfo<StudentShip>? {
        TODO("find which ship is at the coordinate. You can either search through the ships or look it up in a precalculated matrix")

    }
}

