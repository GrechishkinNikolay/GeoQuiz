package com.grechishkin.criminalintent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CrimeListFragment : Fragment() {

    private lateinit var mCrimeRecyclerView: RecyclerView
    private lateinit var mAdapter: CrimeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_crime_list, container, false)

        mCrimeRecyclerView = view.findViewById(R.id.crime_recycler_view)
        mCrimeRecyclerView.layoutManager = LinearLayoutManager(this.activity)

        updateUI()
        return view
    }

    private fun updateUI() {
        val crimeLab = CrimeLab.getInstance(requireContext())
        val crimes = crimeLab.mCrimes
        mAdapter = CrimeAdapter(crimes)
        mCrimeRecyclerView.adapter = mAdapter
    }

    private class CrimeHolder(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ) : RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_crime, parent, false)) {

        private val mTitleTextView: TextView =
            itemView.findViewById<View>(R.id.crime_title) as TextView
        private val mDateTextView: TextView =
            itemView.findViewById<View>(R.id.crime_date) as TextView
        private lateinit var mCrime: Crime

        init {
            itemView.setOnClickListener {
                Toast.makeText(it?.context, "${mCrime.mTitle} clicked!", Toast.LENGTH_LONG).show()
            }
        }

        fun bind(crime: Crime) {
            mCrime = crime
            mTitleTextView.text = mCrime.mTitle
            mDateTextView.text = mCrime.mDate.toString()
        }
    }

    private class CrimeAdapter(private val mCrimes: MutableList<Crime>) :
        RecyclerView.Adapter<CrimeHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return CrimeHolder(layoutInflater, parent)
        }

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            holder.bind(mCrimes[position])
        }

        override fun getItemCount() = mCrimes.size
    }
}