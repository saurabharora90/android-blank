package com.saurabharora.android_blank

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.transition.Fade
import androidx.transition.Slide
import com.saurabharora.android_blank.databinding.ActivityMainBinding

const val TAG_EXTRA_CONTENT = "tag_extra_content"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val fragment = TestFragment().apply {
                enterTransition = Slide()
                returnTransition = Fade()
            }

            supportFragmentManager.commit {
                val currentlyShowingFragment =
                    supportFragmentManager.findFragmentByTag(TAG_EXTRA_CONTENT)
                add(binding.container.id, fragment, TAG_EXTRA_CONTENT)
                show(fragment)
                if (currentlyShowingFragment != null)
                    hide(currentlyShowingFragment)
                setReorderingAllowed(true)

                //replace(binding.container.id, fragment, TAG_EXTRA_CONTENT)
                addToBackStack(TAG_EXTRA_CONTENT)
            }
        }
    }
}
