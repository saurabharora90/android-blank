package com.saurabharora.android_blank

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_test.*

class TestFragment : Fragment(R.layout.fragment_test) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        when (requireArguments().getInt("index")) {
            0 -> parent.setBackgroundColor(Color.RED)
            1 -> parent.setBackgroundColor(Color.GREEN)
            2 -> parent.setBackgroundColor(Color.BLUE)
        }
    }

    companion object {

        fun getInstance(index: Int): TestFragment {
            val fragment = TestFragment()
            fragment.arguments = Bundle().apply {
                putInt("index", index)
            }

            return fragment
        }
    }
}