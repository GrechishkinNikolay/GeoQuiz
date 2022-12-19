package com.grechishkin.criminalintent

import android.content.Context
import android.content.Intent
import java.util.*

class CrimeActivity : SingleFragmentActivity() {

    companion object {
        private const val EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id"

        fun newIntent(packageContext: Context?, crimeId: UUID?): Intent {
            val intent = Intent(packageContext, CrimeActivity::class.java)
            intent.putExtra(EXTRA_CRIME_ID, crimeId)
            return intent
        }
    }

    override fun createFragment() = CrimeFragment.newInstance(
        intent.getSerializableExtra(
            EXTRA_CRIME_ID
        ) as UUID
    )
}