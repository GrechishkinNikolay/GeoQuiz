package com.grechishkin.criminalintent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class CrimeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crime)

        val fm = supportFragmentManager
        var fragment = fm.findFragmentById(R.id.frame_layout_fragment_container)

        if (fragment == null) {
            fragment = CrimeFragment()
            fm.beginTransaction()
                .add(R.id.frame_layout_fragment_container, fragment)
                .commit()
        }
    }
}