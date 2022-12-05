package com.grechishkin.criminalintent

import java.util.*

class Crime() {
    var mId: UUID? = null
        private set
    val mTitle: String? = null
    var mDate: Date? = null
    val mSolved = false

    init {
        mId = UUID.randomUUID()
        mDate = Date()
    }

}
