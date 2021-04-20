package uk.ac.bournemouth.ap.battleships

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class GameView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_view)
    }
}