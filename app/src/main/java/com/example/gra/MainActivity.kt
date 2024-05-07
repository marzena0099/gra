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
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.emptyFlow
import kotlin.random.Random
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var countdownTimer: CountDownTimer
    private lateinit var guessInput: EditText
    private lateinit var guessButton: Button
    private lateinit var replyButton: Button
    private lateinit var onPouseButton: Button
    private lateinit var replyCount: Button
    private lateinit var exitButton: Button
    private lateinit var resultText: TextView


    private var randomNumber: Int = 0
    private var attempts: Int = 0
    private var onPouseButtonClicked = false

    private var remainingTime: Long = 6
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        guessInput = findViewById(R.id.guess_input)
        guessButton = findViewById(R.id.guess_button)
        replyButton = findViewById(R.id.reply_button)
        onPouseButton = findViewById(R.id.onPouse_button)
        exitButton = findViewById(R.id.exit_button)
        resultText = findViewById(R.id.result_text)
//        replyCount = findViewById(R.id.replyCount)


        randomNumber = Random.nextInt(1, 101)
        displayResult("$randomNumber")
        var result = 0

        guessButton.setOnClickListener {
            val invitingText = findViewById<TextView>(R.id.inviting_text)
            invitingText.visibility = View.GONE
            val userGuess = guessInput.text.toString().toIntOrNull()
            if (userGuess != null) {
                attempts++
                if (userGuess == randomNumber) {
                    displayResult("Gratulacje! Zgadłeś liczbę $randomNumber w $attempts próbach.")
                    result++
                    endRound(result)


                } else if (userGuess < randomNumber) {
                    displayResult("Za mało! Spróbuj większej liczby.")

                    updateHangmanImage(wrongAttemps = attempts)
                    if (attempts >= 6) {
                        displayResult("Przegrałeś! Prawidłowa liczba to $randomNumber.")
                        endRound(result)
                    }
                } else {
                    displayResult("Za dużo! Spróbuj mniejszej liczby.")

                    updateHangmanImage(wrongAttemps = attempts)
                    if (attempts >= 6) {
                        displayResult("Przegrałeś! Prawidłowa liczba to $randomNumber.")
                        endRound(result)
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

    private fun endRound(result: Int) {
        if (result >= 1) {
            showWinnerImgage()
        }
        guessButton.isEnabled = false

//        replyCount.setOnClickListener { reply() }
        exitButton.visibility = View.VISIBLE
        exitButton.setOnClickListener { finish() }
        replyButton.visibility = View.VISIBLE
        replyButton.setOnClickListener {

            startGameCountdown(result)
        }

    }

    //    private var countdownTimer: CountDownTimer? = null
//
//
    private fun startGameCountdown(result: Int) {

        if (result >= 1) {
            val winnerImageView = findViewById<ImageView>(R.id.imageView8)
            winnerImageView.visibility = View.GONE
        } else {
            val loserImageView = findViewById<ImageView>(R.id.hangman_image_view)
            loserImageView.visibility = View.GONE
        }
        onPouseButton.visibility = View.VISIBLE
        onPouseButton.setOnClickListener { cancelGameCountdown() }
        exitButton.visibility = View.GONE

        replyButton.visibility = View.GONE
        guessButton.visibility = View.GONE
        val countdownTextView = findViewById<TextView>(R.id.countdown_text_view)
        countdownTextView.visibility = View.VISIBLE


        val countDownDuration = 6000L // Czas odliczania w milisekundach
        var remainingTime: Long = countDownDuration

        countdownTimer =
            object : CountDownTimer(remainingTime, 1000) { // Odliczanie co 1 sekundę
                @SuppressLint("SetTextI18n")
                override fun onTick(millisUntilFinished: Long) {
                    val secondsLeft =
                        millisUntilFinished / 1000 // Konwertujemy czas w milisekundach na sekundy
                    countdownTextView.text = "Gra wyświetli się za $secondsLeft sekund(y)"

                }


                override fun onFinish() {

                    countdownTextView.visibility = View.GONE
                    onPouseButton.visibility =View.GONE
                    startGame()

                }
            }
                .start()

    }


    private fun startGame() {
//
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }


//private fun resetGame(){
//    attempts = 0
//    randomNumber = Random.nextInt(1, 101)
//    guessInput.text.clear()
//
//    displayResult("")
//    displayResult("$randomNumber")
//    updateHangmanImage(0)
//    replyButton.visibility = View.GONE
//    guessButton.isEnabled = true
//    guessButton.visibility= View.VISIBLE
//    exitButton.visibility = View.GONE
//    onPouseButton.visibility = View.GONE
////    countdownTimer = null
//}

    private fun showWinnerImgage() {

        val winnerImageView = findViewById<ImageView>(R.id.imageView8)
        winnerImageView.visibility = View.VISIBLE
        val mainImage = findViewById<ImageView>(R.id.hangman_image_view)
        mainImage.visibility = View.GONE
        winnerImageView.setImageResource(R.drawable.win)
    }

    private fun cancelGameCountdown() {

//            countdownTimer?.cancel()
        countdownTimer?.cancel()
        onPouseButton.visibility = View.GONE
//            replyCount.visibility=View.VISIBLE
        countdownTimer.onFinish()
//            startGame()

    }
//private fun reply(){
//    countdownTimer?.start()


}






