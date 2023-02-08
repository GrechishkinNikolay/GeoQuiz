package com.grechishkin.criminalintent.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.grechishkin.criminalintent.R
import java.util.*

class TimePickerFragment : DialogFragment() {

    private lateinit var timePicker: TimePicker

    companion object {
        private val ARG_TIME = "time"
        const val EXTRA_TIME = "com.grechishkin.criminalintent.fragments.TimePickerFragment.time"

        fun newInstance(date: Date?): TimePickerFragment {
            val bundle = Bundle()
            bundle.putSerializable(ARG_TIME, date)

            val timePickerFragment = TimePickerFragment()
            timePickerFragment.arguments = bundle
            return timePickerFragment
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val oldDate = arguments?.getSerializable(ARG_TIME) as Date

        val calendar = Calendar.getInstance()
        calendar.time = oldDate
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val min = calendar.get(Calendar.MINUTE)

        val timePickerView = LayoutInflater.from(activity).inflate(R.layout.dialog_time, null)

        timePicker = timePickerView.findViewById(R.id.dialog_time_picker)
        timePicker.hour = hourOfDay
        timePicker.minute = min

        return AlertDialog.Builder(requireContext())
            .setView(timePickerView)
            .setTitle(R.string.time_picker_title)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val date = GregorianCalendar(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    timePicker.hour,
                    timePicker.minute
                ).time
                sendPickedTime(Activity.RESULT_OK, date)
            }
            .create()
    }

    private fun sendPickedTime(resultCode: Int, date: Date) {
        if (targetFragment == null) {
            return
        }
        val intent = Intent()
        intent.putExtra(EXTRA_TIME, date)
        targetFragment!!.onActivityResult(targetRequestCode, resultCode, intent)
    }
}