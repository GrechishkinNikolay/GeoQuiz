package com.grechishkin.criminalintent

import android.content.Context
import java.util.*

class CrimeStorage private constructor(private val context: Context) {

    var mCrimes: MutableList<Crime> = mutableListOf()

    init {
        for (i in 0..99) {
            val crime = Crime()
            crime.mTitle = "Crime #$i"
            crime.mSolved = Math.random() < 0.5
            crime.mRequiresPolice = Math.random() < 0.4
            mCrimes += crime
        }

        mCrimes = mCrimes.sortedBy { it.mId } as MutableList<Crime>
    }

    fun findCrimeByUUID(uuid: UUID?): Crime {
        return mCrimes[mCrimes.binarySearchBy(uuid) { it.mId }]
    }

    companion object {
        @Volatile
        private var INSTANCE: CrimeStorage? = null

        @Synchronized
        fun getInstance(context: Context): CrimeStorage =
            INSTANCE ?: CrimeStorage(context).also { INSTANCE = it }
    }
}