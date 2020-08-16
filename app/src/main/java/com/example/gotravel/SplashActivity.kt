package com.example.gotravel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startActivity(Intent(this, MainActivity::class.java))
        finish()

       /* Thread(Runnable {
            updateProgress()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }).start()*/
    }

    private fun updateProgress() {
        var progress = 0
        while (progress <= 100) {
            try {
                Thread.sleep(25)
                progress_intro.progress = progress
            } catch (e: Exception) {
                e.printStackTrace()
            }
            progress += 1
        }
    }
}