package org.example.student.battleshipgame

import com.sun.xml.internal.bind.v2.TODO
import uk.ac.bournemouth.ap.battleshiplib.Ship
import java.lang.IllegalArgumentException

/** A simple implementation of ship. You could change this if you want to, or add functionality.*/
open class StudentShip(override val top: Int, override val left: Int, override val bottom: Int, override val right: Int ): Ship {
    //override val top: Int = TODO("Store or calculate the top position")
    //override val left: Int = TODO("Store or calculate the left position")
    //override val bottom: Int = TODO("Store or calculate the bottom position")
    //override val right: Int = TODO("Store or calculate the right position")

    init {
        /* TODO Make sure to check that the arguments are valid: left<=right, top<=bottom and the * ship is only 1 wide */
        //if(right<left || bottom<top || (bottom != top && right != left)){
        //    throw IllegalArgumentException("Ship dimensions not possible")
        //}
    }
}