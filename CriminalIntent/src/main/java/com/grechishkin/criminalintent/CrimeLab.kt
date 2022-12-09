package com.grechishkin.criminalintent

import android.content.Context
import java.util.*

class CrimeLab private constructor(private val context: Context) {

    val mCrimes: MutableList<Crime> = mutableListOf()

    init {
        for (i in 0..99) {
            val crime = Crime()
            crime.mTitle = "Crime #$i"
            crime.mSolved = i % 2 == 0 // Для каждого второго объекта
            mCrimes += crime
        }
    }

    fun getCrime(uuid: UUID): Crime? {
        return mCrimes.find { it.mId == uuid }
    }

    companion object {
        @Volatile
        private var INSTANCE: CrimeLab? = null

        @Synchronized
        fun get(context: Context): CrimeLab = INSTANCE ?: CrimeLab(context).also { INSTANCE = it }

    }
}