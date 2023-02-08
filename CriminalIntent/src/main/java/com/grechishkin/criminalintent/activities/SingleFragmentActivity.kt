package com.grechishkin.criminalintent.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.grechishkin.criminalintent.R

abstract class SingleFragmentActivity : AppCompatActivity() {

    protected abstract fun createFragment(): Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        val fragmentManager = supportFragmentManager
        var fragment = fragmentManager.findFragmentById(R.id.frame_layout_fragment_container)

        if (fragment == null) {
            fragment = createFragment()
            fragmentManager.beginTransaction()
                .add(R.id.frame_layout_fragment_container, fragment)
                .commit()
        }
    }
}