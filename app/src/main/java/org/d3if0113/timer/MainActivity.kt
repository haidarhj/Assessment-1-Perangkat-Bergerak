package org.d3if0113.timer


import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.d3if0113.timer.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var countDownTimer: CountDownTimer
    private var timeLeftInMillis = 0L // inisialisasi waktu yang tersisa ke nol
    private var timerRunning = false // status timer
    private lateinit var mediaPlayer: MediaPlayer // media Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tombolStart.setOnClickListener {
            if (!timerRunning) {
                val input = binding.editTextTimer.text.toString()
                if (input.isNotEmpty()) {
                    val timeInMillis = input.toLong() * 1000
                    startTimer(timeInMillis)
                } else {
                    Toast.makeText(this, "Masukkan waktu terlebih dahulu", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        //Untuk Mengganti Lagu MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.lagu)
        //Tombol Untuk Menstop Musik
        binding.tombolStopMusik.setOnClickListener {
            stopMusic()
        }

        binding.tombolStop.setOnClickListener {
            if (timerRunning) {
                stopTimer()

            }

        }
    }

   //Untuk Dapat menggunakan Mode Landscape
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("timeLeftInMillis", timeLeftInMillis)
        outState.putBoolean("timerRunning", timerRunning)
        if (timerRunning) {
            countDownTimer.cancel()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        timeLeftInMillis = savedInstanceState.getLong("timeLeftInMillis")
        timerRunning = savedInstanceState.getBoolean("timerRunning")
        updateTimer()
        if (timerRunning) {
            startTimer(timeLeftInMillis)
        }
    }

    private fun startTimer(timeInMillis: Long) {
        timeLeftInMillis = timeInMillis
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {


            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimer()
            }

            override fun onFinish() {
                timerRunning = false
                updateTimer()
                mediaPlayer.start()
            }
        }.start()

        timerRunning = true
    }

    private fun stopTimer() {
        countDownTimer.cancel()
        timerRunning = false
        timeLeftInMillis = 0
        updateTimer()
    }

   //Fungsi Stop Music
    private fun stopMusic() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.prepare()
        }

    }

    private fun updateTimer() {
        val minutes = (timeLeftInMillis / 1000 / 60).toInt()
        val seconds = (timeLeftInMillis / 1000 % 60).toInt()
        val timeLeftFormatted = String.format("%02d:%02d", minutes, seconds)
        binding.textTimer.text = timeLeftFormatted
    }
}
