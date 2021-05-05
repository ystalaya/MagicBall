package com.example.magicball

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.widget.Toast
import com.google.android.material.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*
import study.android.magicball.ShakeDetector
import kotlin.random.Random


class MainActivity : AppCompatActivity(), ShakeDetector.OnShakeListener{
    var alreadyAsked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        magicBall.setOnClickListener{

            answer()
        }
        val shakeDetector = ShakeDetector( this)
        shakeDetector.setOnShakeListener(this)
    }

    fun answer() {
        if(alreadyAsked) {
            val toast = Toast.makeText(this,R.string.oneQuest,Toast.LENGTH_LONG)
            toast.show()
            finish()
            return
        }
        val  messages = resources.getStringArray(R.array.massages)
        val randomIndex = Random.nextInt(messages.size)
        val msg = messages[randomIndex]
        val fadeInAnim = android.view.animation.AnimationUtils.loadAnimation(this,android.R.anim.fade_in)
        message.startAnimation(fadeInAnim)
        val shakeAnimation = android.view.animation.AnimationUtils.loadAnimation(this,R.anim.shake)
        screen.startAnimation(shakeAnimation)
        message.text = msg
        alreadyAsked = true
    }

    override fun onShake() {
        answer()
    }

}