package com.example.geoquiz

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class CheatActivity : AppCompatActivity() {

    companion object {
        private const val KEY_ANSWER_IS_TRUE =
            "com.bignerdranch.android.geoquiz.code_answer_is_true"
        private const val EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown"
        private const val EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true"

        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            val intent = Intent(packageContext, CheatActivity::class.java)
            intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            return intent
        }

        fun wasAnswerShown(result: Intent): Boolean {
            return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false)
        }
    }

    private var answerIsTrue = false
    private var answerIsShown = false

    private val mShowAnswerButton: Button by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.show_answer_button) }
    private val mAnswerTextView: TextView by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.answer_text_view) }
    private val mVersionTextView: TextView by lazy(LazyThreadSafetyMode.NONE) { findViewById(R.id.version) }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(KEY_ANSWER_IS_TRUE, answerIsTrue)
        outState.putBoolean(EXTRA_ANSWER_SHOWN, answerIsShown)
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        mVersionTextView.append(" API Level ${Build.VERSION.SDK_INT}")

        answerIsShown = savedInstanceState?.getBoolean(EXTRA_ANSWER_SHOWN) ?: false
        setAnswerShownResult()
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        mShowAnswerButton.setOnClickListener {
            mAnswerTextView.setText(if (answerIsTrue) R.string.true_button else R.string.false_button)
            answerIsShown = true
            setAnswerShownResult()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val cx = mShowAnswerButton.width / 2
                val cy = mShowAnswerButton.height / 2
                val radius = mShowAnswerButton.width.toFloat()
                val anim = ViewAnimationUtils
                    .createCircularReveal(mShowAnswerButton, cx, cy, radius, 0f)
                anim.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        mShowAnswerButton.visibility = View.INVISIBLE
                    }
                })
                anim.start()
            } else {
                mShowAnswerButton.visibility = View.INVISIBLE;
            }
        }

    }

    private fun setAnswerShownResult() {
        intent.putExtra(EXTRA_ANSWER_SHOWN, answerIsShown)
        setResult(RESULT_OK, intent)
    }
}