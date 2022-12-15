package com.grechishkin.criminalintent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat

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
        val crimeSingletonStorage = CrimeSingletonStorage.getInstance(requireContext())
        val crimes = crimeSingletonStorage.mCrimes
        mAdapter = CrimeAdapter(crimes)
        mCrimeRecyclerView.adapter = mAdapter
    }

    private class CrimeHolder(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        resource: Int
    ) : RecyclerView.ViewHolder(inflater.inflate(resource, parent, false)) {

        private val mTitleTextView = itemView.findViewById<TextView>(R.id.crime_title)
        private val mDateTextView = itemView.findViewById<TextView>(R.id.crime_date)
        private val mSolvedImageView = itemView.findViewById<ImageView>(R.id.crime_solved)
        private lateinit var mCrime: Crime

        init {
            itemView.setOnClickListener {
                Toast.makeText(it?.context, "${mCrime.mTitle} clicked!", Toast.LENGTH_LONG).show()
            }
        }

        fun bind(crime: Crime) {
            mCrime = crime
            mTitleTextView.text = mCrime.mTitle
            mDateTextView.text = SimpleDateFormat("EEEE, MMM d, y").format(mCrime.mDate)
            mSolvedImageView.visibility = if (crime.mSolved) View.VISIBLE else View.GONE
        }
    }

    private class CrimeAdapter(private val mCrimes: MutableList<Crime>) :
        RecyclerView.Adapter<CrimeHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return when (viewType) {
                0 -> CrimeHolder(layoutInflater, parent, R.layout.list_item_crime)
                1 -> CrimeHolder(layoutInflater, parent, R.layout.list_item_serious_crime)
                else -> CrimeHolder(layoutInflater, parent, R.layout.list_item_crime)
            }
        }

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            holder.bind(mCrimes[position])
        }

        override fun getItemViewType(position: Int): Int {
            return if (mCrimes[position].mRequiresPolice) 1 else 0
        }

        override fun getItemCount() = mCrimes.size
    }
}