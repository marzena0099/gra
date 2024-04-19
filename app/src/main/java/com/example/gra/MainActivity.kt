package com.example.gra

//import android.os.Bundle
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//
//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
//}
//
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var guessInput: EditText
    private lateinit var guessButton: Button
    private lateinit var resultText: TextView

    private var randomNumber: Int = 0
    private var attempts: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        guessInput = findViewById(R.id.guess_input)
        guessButton = findViewById(R.id.guess_button)
        resultText = findViewById(R.id.result_text)

        randomNumber = Random.nextInt(1, 101)


        guessButton.setOnClickListener {
            val userGuess = guessInput.text.toString().toIntOrNull()
            if (userGuess != null) {
                attempts++
                if (userGuess == randomNumber) {
                    displayResult("Gratulacje! Zgadłeś liczbę $randomNumber w $attempts próbach.")
                    resetGame()
                } else if (userGuess < randomNumber) {
                    displayResult("Za mało! Spróbuj większej liczby.")

                    updateHangmanImage(wrongAttemps = attempts)
                    if (attempts >= 6) {
                        displayResult("Przegrałeś! Prawidłowa liczba to $randomNumber.")
                        endGame()
                    }
                } else {
                    displayResult("Za dużo! Spróbuj mniejszej liczby.")

                    updateHangmanImage(wrongAttemps = attempts)
                    if (attempts >= 6) {
                        displayResult("Przegrałeś! Prawidłowa liczba to $randomNumber.")
                        endGame()
                    }
                }
            } else {
                Toast.makeText(this, "Wprowadź liczbę.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateHangmanImage(wrongAttemps : Int) {
        val hangmanImageView = findViewById<ImageView>(R.id.hangman_image_view)
        val drawableId = when (wrongAttemps) {
            1 -> R.drawable.img_1
            2 -> R.drawable.img_2
            3 -> R.drawable.img_3
            4 -> R.drawable.img_4
            5 -> R.drawable.img_5
            6 -> R.drawable.img_6
            else -> R.drawable.wstep
        }
        hangmanImageView.setImageResource(drawableId)
    }



    private fun displayResult(message: String) {
        resultText.text = message
    }
    private fun endGame() {
        guessButton.isEnabled = false
    }

    private fun resetGame() {
        attempts = 0
        randomNumber = Random.nextInt(1, 101)
        guessInput.text.clear()
        displayResult("")
    }
}



