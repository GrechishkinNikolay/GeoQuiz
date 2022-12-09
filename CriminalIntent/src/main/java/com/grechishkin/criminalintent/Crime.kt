package com.grechishkin.criminalintent

import java.util.*

class Crime {
    var mId: UUID
        private set
    var mTitle: String? = null
    var mDate: Date? = null
    var mSolved = false

    init {
        mId = UUID.randomUUID()
        mDate = Date()
    }

}
