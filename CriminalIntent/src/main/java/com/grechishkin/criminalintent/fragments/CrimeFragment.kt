package com.grechishkin.criminalintent.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.grechishkin.criminalintent.Crime
import com.grechishkin.criminalintent.CrimeStorage
import com.grechishkin.criminalintent.R
import java.util.*


class CrimeFragment : Fragment() {

    companion object {
        private const val ARG_CRIME_ID = "crime_id"
        private const val DIALOG_DATE = "DialogDate"
        private const val REQUEST_CODE_CHANGE_DATE = 0
        private const val REQUEST_CODE_CHANGE_TIME = 1
        private lateinit var viewPager: ViewPager
        private lateinit var crimeStorage: CrimeStorage

        fun newInstance(uuid: UUID, viewPagerPar: ViewPager): CrimeFragment {
            viewPager = viewPagerPar
            val args = Bundle()
            args.putSerializable(ARG_CRIME_ID, uuid)
            val crimeFragment = CrimeFragment()
            crimeFragment.arguments = args
            return crimeFragment
        }
    }

    private var crime: Crime? = null
    private lateinit var mTitleField: EditText
    private lateinit var mDateButton: Button
    private lateinit var mTimeButton: Button
    private lateinit var mFirstCrimeButton: Button
    private lateinit var mLastCrimeButton: Button
    private lateinit var mSolvedCheckBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val crimeId = arguments?.getSerializable(ARG_CRIME_ID) as UUID
        crimeStorage = CrimeStorage.getInstance(requireContext())
        crime = crimeStorage.findCrimeByUUID(crimeId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v: View = inflater.inflate(R.layout.fragment_crime, container, false)
        mTitleField = v.findViewById(R.id.crime_title)
        mTitleField.setText(crime?.mTitle)
        mDateButton = v.findViewById(R.id.crime_date)
        mTimeButton = v.findViewById(R.id.crime_time)
        mFirstCrimeButton = v.findViewById(R.id.first_crime)
        mLastCrimeButton = v.findViewById(R.id.last_crime)
        mSolvedCheckBox = v.findViewById(R.id.crime_solved)
        mSolvedCheckBox.isChecked = crime?.mSolved == true

        if (crime == crimeStorage.mCrimes.first()) {
            mFirstCrimeButton.isEnabled = false
        } else if (crime == crimeStorage.mCrimes.last()) {
            mLastCrimeButton.isEnabled = false
        }

        mSolvedCheckBox.setOnCheckedChangeListener { _, isChecked ->
            crime?.mSolved = isChecked
        }

        mFirstCrimeButton.setOnClickListener {
            viewPager.currentItem = 0
        }

        mLastCrimeButton.setOnClickListener {
            viewPager.currentItem = crimeStorage.mCrimes.size - 1
        }

        mTitleField.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                crime?.mTitle = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

        updateDate()

        mDateButton.setOnClickListener {
            val datePickerFragment = DatePickerFragment.newInstance(crime?.mDate)
            datePickerFragment.setTargetFragment(this@CrimeFragment, REQUEST_CODE_CHANGE_DATE)
            datePickerFragment.show(parentFragmentManager, DIALOG_DATE)
        }

        mTimeButton.setOnClickListener {
            val timePickerFragment = TimePickerFragment.newInstance(crime?.mDate)
            timePickerFragment.setTargetFragment(this@CrimeFragment, REQUEST_CODE_CHANGE_TIME)
            timePickerFragment.show(parentFragmentManager, DIALOG_DATE)
        }

        return v
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) return
        if (requestCode == REQUEST_CODE_CHANGE_DATE) {
            val date = data?.getSerializableExtra(DatePickerFragment.EXTRA_DATE) as Date
            crime?.mDate = date
            updateDate()
        }
        if (requestCode == REQUEST_CODE_CHANGE_TIME) {
            val date = data?.getSerializableExtra(TimePickerFragment.EXTRA_TIME) as Date
            crime?.mDate = date
            updateDate()
        }
    }

    private fun updateDate() {
        mDateButton.text = crime?.mDate.toString()
    }
}