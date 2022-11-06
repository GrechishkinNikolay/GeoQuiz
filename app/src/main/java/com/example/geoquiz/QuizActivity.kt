package com.example.geoquiz

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val mTrueButton = findViewById<TextView>(R.id.true_button)
        val mFalseButton = findViewById<TextView>(R.id.false_button)

        mTrueButton.setOnClickListener { TODO("Not yet implemented") }
    }
}