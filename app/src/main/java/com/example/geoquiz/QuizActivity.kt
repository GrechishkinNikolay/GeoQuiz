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

        updateQuestion()

        mTrueButton.setOnClickListener {
            checkAnswer(true)
        }

        mFalseButton.setOnClickListener {
            checkAnswer(false)
        }

        mNextButton.setOnClickListener {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
            updateQuestion()
        }
    }

    private fun updateQuestion() {
        val question = mQuestionBank[mCurrentIndex].textResId
        mQuestionTextView.setText(question)
    }

    private fun checkAnswer(userPressedTrue: Boolean) {
        val isAnswerTrue = mQuestionBank[mCurrentIndex].isAnswerTrue
        var messageResId = -1

        if (userPressedTrue == isAnswerTrue) {
            messageResId = R.string.correct_toast
        } else {
            messageResId = R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}