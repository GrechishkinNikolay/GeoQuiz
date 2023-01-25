package com.grechishkin.criminalintent

import java.util.*

class Crime {
    var mId: UUID
        private set
    var mTitle: String? = null
    var mDate: Date? = null
    var mSolved = false
    var mRequiresPolice = false

    init {
        mId = UUID.randomUUID()
        mDate = Date()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Crime

        if (mId != other.mId) return false
        if (mTitle != other.mTitle) return false
        if (mDate != other.mDate) return false
        if (mSolved != other.mSolved) return false
        if (mRequiresPolice != other.mRequiresPolice) return false

        return true
    }

    override fun hashCode(): Int {
        var result = mId.hashCode()
        result = 31 * result + (mTitle?.hashCode() ?: 0)
        result = 31 * result + (mDate?.hashCode() ?: 0)
        result = 31 * result + mSolved.hashCode()
        result = 31 * result + mRequiresPolice.hashCode()
        return result
    }
}
