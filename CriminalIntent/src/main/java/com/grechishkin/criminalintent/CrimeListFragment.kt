package com.grechishkin.criminalintent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    ) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_crime, parent, false))

    private class CrimeAdapter(private val mCrimes: MutableList<Crime>) :
        RecyclerView.Adapter<CrimeHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return CrimeHolder(layoutInflater, parent)
        }

        override fun getItemCount() = mCrimes.size

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {}
    }
}