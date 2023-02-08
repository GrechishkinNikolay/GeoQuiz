package com.grechishkin.criminalintent.activities

import com.grechishkin.criminalintent.fragments.CrimeListFragment

class CrimeListActivity : SingleFragmentActivity() {
    override fun createFragment() = CrimeListFragment()
}