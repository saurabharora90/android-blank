package com.saurabharora.android_blank

import android.app.Activity
import android.os.Bundle

/**
 * Loads [MainFragment].
 */
class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}