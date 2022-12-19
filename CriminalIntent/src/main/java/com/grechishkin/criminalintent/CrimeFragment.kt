package com.grechishkin.criminalintent

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
import java.util.*


class CrimeFragment : Fragment() {

    companion object {
        private const val ARG_CRIME_ID = "crime_id"

        fun newInstance(uuid: UUID): CrimeFragment {
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
    private lateinit var mSolvedCheckBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val crimeId = arguments?.getSerializable(ARG_CRIME_ID) as UUID
        crime = CrimeStorage.getInstance(requireContext()).findCrimeByUUID(crimeId)
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
        mSolvedCheckBox = v.findViewById(R.id.crime_solved)
        mSolvedCheckBox.isChecked = crime?.mSolved == true


        mSolvedCheckBox.setOnCheckedChangeListener { _, isChecked ->
            crime?.mSolved = isChecked
        }

        mTitleField.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                crime?.mTitle = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })


        mDateButton.text = crime?.mDate.toString()
        mDateButton.isEnabled = false

        return v
    }
}