package com.example.bullseye

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.bullseye.databinding.ActivityMainBinding
import kotlin.math.abs
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var sliderValue: Int = 1
    private var targetValue: Int = newTargetValue()
    private var totalScore: Int = 0
    private var currentRound: Int = 1


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

    installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        startNewGame()





        binding.hitMeButton.setOnClickListener {
            showResults()

            totalScore += pointsForCurrentRound()
            binding.gameScoreTextView?.text = totalScore.toString()

        }

        binding.startOverButton?.setOnClickListener {
            startNewGame()
        }

        binding.infoButton?.setOnClickListener{
            navigationToAboutPage()
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                sliderValue = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}

        })


    }

    private fun navigationToAboutPage(){
        val intent = Intent(this, AboutActivity::class.java)

        startActivity(intent)
    }
    private fun differenceAmount() = abs(targetValue - sliderValue)
    private fun newTargetValue() = Random.nextInt(1, 100)

    private fun pointsForCurrentRound(): Int {
        val maxScore = 100
        var difference = differenceAmount()


        var bonus = when (difference) {
            0 -> {
                100
            }
            1 -> {
                50
            }
            else -> {
                0
            }
        }

        return maxScore - difference + bonus
    }

    private fun startNewGame() {
        totalScore = 0
        currentRound = 1
        sliderValue = 50
        targetValue = newTargetValue()

        binding.gameScoreTextView?.text = totalScore.toString()
        binding.gameRoundTextView?.text = currentRound.toString()
        binding.targetTextView.text = targetValue.toString()
        binding.seekBar.progress = sliderValue

    }

    private fun showResults() {
        val dialogTitle = alertTitle()
        val dialogMessage =
            getString(R.string.result_dialog_message, sliderValue, pointsForCurrentRound())
        //       val dialogMessage = "The slider's value is $sliderValue"


        val builder = AlertDialog.Builder(this)

        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.setPositiveButton(R.string.hit_me_button_text) { dialog, _ ->


            dialog.dismiss()


            currentRound += 1
            binding.gameRoundTextView?.text = currentRound.toString()

            targetValue = newTargetValue()
            binding.targetTextView.text = targetValue.toString()
        }


        builder.create().show()
    }

    private fun alertTitle(): String {
        val difference = differenceAmount()

        val title: String = when {
            difference == 0 -> {
                getString(R.string.alert_title_1)

            }
            difference < 5 -> {
                getString(R.string.alert_title_2)
            }
            difference < 10 -> {
                getString(R.string.alert_title_3)
            }
            else -> {
                getString(R.string.alert_title_4)
            }
        }

        return title
    }
}