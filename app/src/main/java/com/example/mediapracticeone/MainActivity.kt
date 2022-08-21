package com.example.mediapracticeone

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import com.example.mediapracticeone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MediaPlayer.OnPreparedListener {

    lateinit var binding: ActivityMainBinding
    var mediaPlayer: MediaPlayer? = null

    var httpUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"

    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = Handler(mainLooper)

        binding.playBtn.setOnClickListener {

            if (mediaPlayer == null) {

                //Url orqali
                /* mediaPlayer = MediaPlayer()
                 mediaPlayer!!.setDataSource(httpUrl)
                 mediaPlayer!!.setOnPreparedListener(this)
                 mediaPlayer!!.prepareAsync()*/

                //raw orqali
                mediaPlayer = MediaPlayer.create(this, R.raw.aa)
                mediaPlayer?.start()

                binding.seekBar.max = mediaPlayer?.duration!!
                handler.postDelayed(runnable, 100)

            }

        }

        binding.resumeBtn.setOnClickListener {

            if (!mediaPlayer!!.isPlaying) {
                mediaPlayer!!.start()
            }

        }

        binding.pauseBtn.setOnClickListener {

            if (mediaPlayer!!.isPlaying) {
                mediaPlayer!!.pause()
            }

        }

        binding.stopBtn.setOnClickListener {

            mediaPlayer!!.stop()

        }

        binding.backBtn.setOnClickListener {

            mediaPlayer?.seekTo(mediaPlayer?.currentPosition?.minus(3000)!!)

        }

        binding.forBtn.setOnClickListener {

            mediaPlayer?.seekTo(mediaPlayer?.currentPosition?.plus(3000)!!)

        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

                if (p2) {

                    mediaPlayer?.seekTo(p1)

                }

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {


            }

            override fun onStopTrackingTouch(p0: SeekBar?) {


            }

        })

    }

    private var runnable = object : Runnable {
        override fun run() {

            binding.seekBar.progress = mediaPlayer?.currentPosition!!
            handler.postDelayed(this, 100)

        }
    }

    override fun onPrepared(p0: MediaPlayer?) {

        p0?.start()

    }

    private fun realeaseMP() {

        if (mediaPlayer != null) {

            try {
                mediaPlayer?.release()
                mediaPlayer = null
            } catch (e: Exception) {
                e.printStackTrace()
            }


        }

    }

    override fun onDestroy() {
        super.onDestroy()
        realeaseMP()
    }


}

