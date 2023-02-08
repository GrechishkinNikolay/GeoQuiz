package com.grechishkin.criminalintent.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.grechishkin.criminalintent.R
import java.util.*

class DatePickerFragment : DialogFragment() {

    private lateinit var datePicker: DatePicker

    companion object {
        private val ARG_DATE = "date"
        const val EXTRA_DATE = "com.grechishkin.criminalintent.fragments.DatePickerFragment.date"

        fun newInstance(date: Date?): DatePickerFragment {
            val bundle = Bundle()
            bundle.putSerializable(ARG_DATE, date)

            val datePickerFragment = DatePickerFragment()
            datePickerFragment.arguments = bundle
            return datePickerFragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val oldDate = arguments?.getSerializable(ARG_DATE) as Date

        val calendar = Calendar.getInstance()
        calendar.time = oldDate
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerView = LayoutInflater.from(activity).inflate(R.layout.dialog_date, null)

        datePicker = datePickerView.findViewById(R.id.dialog_date_picker)
        datePicker.init(year, month, day, null)

        return AlertDialog.Builder(requireContext())
            .setView(datePickerView)
            .setTitle(R.string.date_picker_title)
            .setPositiveButton(android.R.string.ok) { dialog, which ->
                val year: Int = datePicker.year
                val month: Int = datePicker.month
                val day: Int = datePicker.dayOfMonth
                val date = GregorianCalendar(year, month, day).time
                sendPickedDate(Activity.RESULT_OK, date)
            }
            .create()
    }

    private fun sendPickedDate(resultCode: Int, date: Date) {
        if (targetFragment == null) {
            return
        }
        val intent = Intent()
        intent.putExtra(EXTRA_DATE, date)
        targetFragment!!.onActivityResult(targetRequestCode, resultCode, intent)
    }
}