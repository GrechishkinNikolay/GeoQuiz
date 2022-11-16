package com.example.geoquiz

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

class QuizActivity : AppCompatActivity() {

    private val TAG = "QuizActivity345"
    private val KEY_INDEX = "questionCurrentIndex"
    private val KEY_POINTS = "questionsPoints"

    private val mQuestionTextView: TextView by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.question_text_view) }
    private val mTrueButton: Button by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.true_button) }
    private val mFalseButton: Button by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.false_button) }
    private val mNextButton: ImageButton by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.next_button) }
    private val mPrevButton: ImageButton by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.previous_button) }

    private val mQuestionBank = arrayOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var mCurrentIndex = 0
    private var questionsPoints = 0

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex)
        savedInstanceState.putInt(KEY_POINTS, questionsPoints)
        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        questionsPoints = savedInstanceState?.getInt(KEY_POINTS) ?: 0
        mCurrentIndex = savedInstanceState?.getInt(KEY_INDEX) ?: 0

        Log.d(TAG, "onCreate(savedInstanceState: Bundle?")
        updateQuestion()

        mTrueButton.setOnClickListener {
            checkAnswer(true)
        }

        mFalseButton.setOnClickListener {
            checkAnswer(false)
        }

        mNextButton.setOnClickListener {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
            if (mCurrentIndex == 0) {
                Toast.makeText(
                    this,
                    "Your points is:${questionsPoints}/${mQuestionBank.size}",
                    Toast.LENGTH_LONG
                ).show()
            }
            updateQuestion()
            mTrueButton.isVisible = true
            mFalseButton.isVisible = true
        }

        mPrevButton.setOnClickListener {
            mCurrentIndex =
                if ((mCurrentIndex - 1) >= 0) (mCurrentIndex - 1) % mQuestionBank.size else mQuestionBank.size - 1
            updateQuestion()
        }

        mQuestionTextView.setOnClickListener {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
            updateQuestion()
        }
    }

/*    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }*/

    private fun updateQuestion() {
        val question = mQuestionBank[mCurrentIndex].textResId
        mQuestionTextView.setText(question)
    }

    private fun checkAnswer(userPressedTrue: Boolean) {
        val isAnswerTrue = mQuestionBank[mCurrentIndex].isAnswerTrue
        var messageResId = -1

        mTrueButton.isVisible = false
        mFalseButton.isVisible = false

        if (userPressedTrue == isAnswerTrue) {
            messageResId = R.string.correct_toast
            questionsPoints++
        } else {
            messageResId = R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}