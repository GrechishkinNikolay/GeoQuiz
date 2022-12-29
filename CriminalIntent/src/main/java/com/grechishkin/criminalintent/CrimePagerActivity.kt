package com.grechishkin.criminalintent

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import java.util.*

class CrimePagerActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var crimes: MutableList<Crime>
    private val crimeStorage = CrimeStorage.getInstance(this)

    companion object {
        private val EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id"

        fun newIntent(packageContext: Context?, crimeId: UUID?): Intent {
            val intent = Intent(packageContext, CrimePagerActivity::class.java)
            intent.putExtra(EXTRA_CRIME_ID, crimeId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crime_pager)

        viewPager = findViewById(R.id.crime_view_pager)
        crimes = crimeStorage.mCrimes

        val crimeId = intent.getSerializableExtra(EXTRA_CRIME_ID) as UUID

        val supportFragmentManager = supportFragmentManager

        viewPager.adapter = object : FragmentStatePagerAdapter(
            supportFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            override fun getItem(position: Int): Fragment {
                val crime: Crime = crimes[position]
                return CrimeFragment.newInstance(crime.mId)
            }

            override fun getCount(): Int {
                return crimes.size
            }
        }
        viewPager.currentItem = crimes.binarySearchBy(crimeId) { it.mId }
    }
}