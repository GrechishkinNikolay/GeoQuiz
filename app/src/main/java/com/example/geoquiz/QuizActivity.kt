package com.example.geoquiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible


class QuizActivity : AppCompatActivity() {

    private companion object {
        private const val TAG = "QuizActivity345"
        private const val KEY_INDEX = "questionCurrentIndex"
        private const val KEY_POINTS = "questionsPoints"
        private const val KEY_ANSWER_IS_SHOWN = "answerIsShown"
        private const val REQUEST_CODE_CHEAT = 0
        private val mQuestionBank = arrayOf(
//            Question(R.string.question_australia, true),
//            Question(R.string.question_oceans, true),
//            Question(R.string.question_mideast, false),
//            Question(R.string.question_africa, false),
//            Question(R.string.question_americas, true),
            Question(R.string.question_asia, true),
            Question(R.string.question_uk, true),
            Question(R.string.question_russia, true)
        )
    }

    private val mQuestionTextView: TextView by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.question_text_view) }
    private val mTrueButton: Button by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.true_button) }
    private val mFalseButton: Button by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.false_button) }
    private val mCheatButton: Button by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.cheat_button) }
    private val mNextButton: ImageButton by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.next_button) }
    private val mPrevButton: ImageButton by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.previous_button) }

    private var mCurrentIndex = 0
    private var questionsPoints = 0
    private var mIsCheater = false

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex)
        savedInstanceState.putInt(KEY_POINTS, questionsPoints)
        savedInstanceState.putBoolean(KEY_ANSWER_IS_SHOWN, mIsCheater)
        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        savedInstanceState?.let {
            questionsPoints = savedInstanceState.getInt(KEY_POINTS)
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX)
            mIsCheater = savedInstanceState.getBoolean(KEY_ANSWER_IS_SHOWN)
        }

        Log.d(TAG, "onCreate(savedInstanceState: Bundle?")
        updateQuestion()

        mCheatButton.setOnClickListener {
            val answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue
            val intent = CheatActivity.newIntent(this@QuizActivity, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }

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
                questionsPoints = 0
            }
            updateQuestion()
            mIsCheater = false
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

    @Deprecated("")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return
            }
            mIsCheater = CheatActivity.wasAnswerShown(data)
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

        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == isAnswerTrue) {
                messageResId = R.string.correct_toast
                questionsPoints++
            } else {
                messageResId = R.string.incorrect_toast
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}