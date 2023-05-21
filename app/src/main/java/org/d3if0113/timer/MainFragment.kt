import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.d3if0113.timer.R
import org.d3if0113.timer.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var countDownTimer: CountDownTimer
    private var timeLeftInMillis = 0L
    private var timerRunning = false
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tombolStart.setOnClickListener {
            if (!timerRunning) {
                val input = binding.editTextTimer.text.toString()
                if (input.isNotEmpty()) {
                    val timeInMillis = input.toLong() * 1000
                    startTimer(timeInMillis)
                } else {
                    Toast.makeText(requireContext(), "Masukkan waktu terlebih dahulu", Toast.LENGTH_SHORT).show()
                }
            }
        }

        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.lagu)
        binding.tombolStopMusik.setOnClickListener {
            stopMusic()
        }

        binding.tombolStop.setOnClickListener {
            if (timerRunning) {
                stopTimer()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("timeLeftInMillis", timeLeftInMillis)
        outState.putBoolean("timerRunning", timerRunning)
        if (timerRunning) {
            countDownTimer.cancel()
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            timeLeftInMillis = savedInstanceState.getLong("timeLeftInMillis")
            timerRunning = savedInstanceState.getBoolean("timerRunning")
            updateTimer()
            if (timerRunning) {
                startTimer(timeLeftInMillis)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
                binding.tombolStopMusik.visibility = View.VISIBLE
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

    private fun stopMusic() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.prepare()
        }
        binding.tombolStopMusik.visibility = View.GONE
    }

    private fun updateTimer() {
        val minutes = (timeLeftInMillis / 1000 / 60).toInt()
        val seconds = (timeLeftInMillis / 1000 % 60).toInt()
        val timeLeftFormatted = String.format("%02d:%02d", minutes, seconds)
        binding.textTimer.text = timeLeftFormatted
    }
}
