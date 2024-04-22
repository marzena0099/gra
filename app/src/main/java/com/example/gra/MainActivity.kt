package com.example.gra

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.delay
import java.util.logging.Handler
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
//        android:layout_centerInParent="true"-->
    private lateinit var guessInput: EditText
    private lateinit var guessButton: Button
    private lateinit var replyButton: Button
    private lateinit var onPouseButton: Button
//    private lateinit var cancelGameCountdownButton: Button
    private lateinit var exitButton: Button
    private lateinit var resultText: TextView


    private var randomNumber: Int = 0
    private var attempts: Int = 0
    private var onPouseButtonClicked = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        guessInput = findViewById(R.id.guess_input)
        guessButton = findViewById(R.id.guess_button)
        replyButton = findViewById(R.id.reply_button)
//        cancelGameCountdownButton = findViewById(R.id.cancelGameCountdownButton_button)
        onPouseButton = findViewById(R.id.onPouse_button)
        exitButton = findViewById(R.id.exit_button)
        resultText = findViewById(R.id.result_text)

        randomNumber = Random.nextInt(1, 101)
        displayResult("$randomNumber")

        guessButton.setOnClickListener {
            val userGuess = guessInput.text.toString().toIntOrNull()
            if (userGuess != null) {
                attempts++
                if (userGuess == randomNumber) {
                    displayResult("Gratulacje! Zgadłeś liczbę $randomNumber w $attempts próbach.")
                    showWinnerImgage()
                    endRound()
//

                } else if (userGuess < randomNumber) {
                    displayResult("Za mało! Spróbuj większej liczby.")

                    updateHangmanImage(wrongAttemps = attempts)
                    if (attempts >= 6) {
                        displayResult("Przegrałeś! Prawidłowa liczba to $randomNumber.")
                        endRound()
                    }
                } else {
                    displayResult("Za dużo! Spróbuj mniejszej liczby.")

                    updateHangmanImage(wrongAttemps = attempts)
                    if (attempts >= 6) {
                        displayResult("Przegrałeś! Prawidłowa liczba to $randomNumber.")
                        endRound()
                    }
                }
            } else {
                Toast.makeText(this, "Wprowadź liczbę.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateHangmanImage(wrongAttemps: Int) {
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

    private fun endRound() {
        guessButton.isEnabled = false
        replyButton.visibility = View.VISIBLE
        exitButton.visibility = View.VISIBLE
        exitButton.setOnClickListener { finish() }
        replyButton.setOnClickListener { startGameCountdown() }

    }

    private var countdownTimer: CountDownTimer? = null

    private fun startGameCountdown() {
        val winnerImageView = findViewById<ImageView>(R.id.imageView8)
        winnerImageView.visibility = View.GONE
//        winnerImageView.visibility= View.GONE
        replyButton.visibility = View.GONE
        guessButton.visibility= View.GONE
        val countdownTextView = findViewById<TextView>(R.id.countdown_text_view)
        countdownTextView.visibility = View.VISIBLE // Ustawiamy widoczność TextView na widoczny

        val countDownDuration = 6000L // Czas odliczania w milisekundach (3 sekundy)
        countdownTimer =
            object : CountDownTimer(countDownDuration, 1000) { // Odliczanie co 1 sekundę
                @SuppressLint("SetTextI18n")
                override fun onTick(millisUntilFinished: Long) {
                    val secondsLeft =
                        millisUntilFinished / 1000 // Konwertujemy czas w milisekundach na sekundy
                    countdownTextView.text = "Gra wyświetli się za $secondsLeft sekund(y)"
                }

                override fun onFinish() {
                    countdownTextView.visibility = View.GONE // Ukrywamy TextView po zakończeniu odliczania
                startGame() // Rozpoczynamy grę

                }

            }.start()
        onPouseButton.visibility = View.VISIBLE

        onPouseButton.setOnClickListener { cancelGameCountdown() }


//
    }
     private fun startGame(){
         if (onPouseButtonClicked==false) {
//             android.os.Handler().postDelayed({
                 val intent = Intent(this, MainActivity::class.java)
                 startActivity(intent)
                 finish()

//             }, 6000)
         }
     }

    private fun showWinnerImgage() {

        val winnerImageView = findViewById<ImageView>(R.id.imageView8)
        winnerImageView.visibility = View.VISIBLE
        val mainImage = findViewById<ImageView>(R.id.hangman_image_view)
        mainImage.visibility= View.GONE
        winnerImageView.setImageResource(R.drawable.win)
    }

    private fun cancelGameCountdown() {
        countdownTimer?.cancel()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        onPouseButtonClicked = true

        // Anulujemy odliczanie
    }

//    override fun onPause() {
//        super.onPause()
//        cancelGameCountdown() // Anulujemy odliczanie, gdy aktywność jest pauzowana
//    }


//    private fun handleWin(text: String) {
//
//        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
//
//
//        val winnerImageView = findViewById<ImageView>(R.id.hangman_image_view)
//        winnerImageView.setImageResource(R.drawable.win)
//        android.os.Handler().postDelayed({
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//
//        }, 600)
////
//
//    }
}



