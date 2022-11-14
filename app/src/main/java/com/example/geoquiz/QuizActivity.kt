package com.example.geoquiz

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {

    private val mQuestionTextView: TextView by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.question_text_view) }
    private val mTrueButton: TextView by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.true_button) }
    private val mFalseButton: TextView by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.false_button) }
    private val mNextButton: TextView by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.next_button) }

    private val mQuestionBank = arrayOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var mCurrentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val question: Int = mQuestionBank[mCurrentIndex].textResId
        mQuestionTextView.setText(question)

        mTrueButton.setOnClickListener {
            Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show()
        }

        mFalseButton.setOnClickListener {
            Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show()
        }

        mNextButton.setOnClickListener {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
            val question = mQuestionBank[mCurrentIndex].textResId
            mQuestionTextView.setText(question)
        }
    }

    private fun updateQuestion() {
        val question = mQuestionBank[mCurrentIndex].textResId
        mQuestionTextView.setText(question)
    }
}