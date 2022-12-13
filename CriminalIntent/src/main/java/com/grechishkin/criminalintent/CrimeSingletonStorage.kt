package com.grechishkin.criminalintent

import android.content.Context
import java.util.*

class CrimeSingletonStorage private constructor(private val context: Context) {

    val mCrimes: MutableList<Crime> = mutableListOf()

    init {
        for (i in 0..99) {
            val crime = Crime()
            crime.mTitle = "Crime #$i"
            crime.mSolved = i % 2 == 0 // Для каждого второго объекта
            crime.mRequiresPolice = Math.random() < 0.4 // Для случайного объекта
            mCrimes += crime
        }
    }

    fun findCrimeByUUID(uuid: UUID): Crime? {
        return mCrimes.find { it.mId == uuid }
    }

    companion object {
        @Volatile
        private var INSTANCE: CrimeSingletonStorage? = null

        @Synchronized
        fun getInstance(context: Context): CrimeSingletonStorage =
            INSTANCE ?: CrimeSingletonStorage(context).also { INSTANCE = it }
    }
}