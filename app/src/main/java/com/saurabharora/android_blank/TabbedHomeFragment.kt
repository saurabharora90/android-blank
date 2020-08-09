package com.saurabharora.android_blank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AnimRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.tabs.TabLayout
import com.saurabharora.android_blank.databinding.FragmentTabbedBinding

class TabbedHomeFragment : Fragment(),
    TabLayout.OnTabSelectedListener {

    private lateinit var fragmentBinding: FragmentTabbedBinding
    private val tabs = listOf("Red", "Green", "Blue")
    private val homeFragments: List<TestFragment> by lazy {
        tabs.mapIndexed { index, _ -> TestFragment.getInstance(index) }
    }
    private var currentTabPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FragmentTabbedBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentBinding.tabLayout.addOnTabSelectedListener(this)

        for (tab in tabs) {
            fragmentBinding.tabLayout.addTab(
                fragmentBinding.tabLayout.newTab()
                    .setText(tab)
            )
        }
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        val fragmentToShow = homeFragments[requireNotNull(tab).position]
        renderFragment(fragmentToShow)
    }

    private fun renderFragment(fragment: TestFragment) {
        childFragmentManager.commit {
            homeFragments.forEach {
                if (it.isAdded) {
                    setCustomAnimations(-1, R.anim.fade_out)
                    hide(it)
                }
            }

            val selectedTabPosition = fragmentBinding.tabLayout.selectedTabPosition
            @AnimRes val anim =
                if (selectedTabPosition < currentTabPosition)
                    R.anim.transition_slide_right_show
                else
                    R.anim.transition_slide_left_show

            setCustomAnimations(anim, -1)
            if (!fragment.isAdded)
                add(
                    fragmentBinding.container.id,
                    fragment,
                    tabs[selectedTabPosition]
                )

            show(fragment)
            currentTabPosition = selectedTabPosition
        }
    }
}