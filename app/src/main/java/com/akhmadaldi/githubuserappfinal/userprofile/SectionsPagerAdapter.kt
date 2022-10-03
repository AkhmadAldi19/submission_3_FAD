package com.akhmadaldi.githubuserappfinal.userprofile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.akhmadaldi.githubuserappfinal.R
import com.akhmadaldi.githubuserappfinal.userprofile.followers.FollowersFragment
import com.akhmadaldi.githubuserappfinal.userprofile.following.FollowingFragment

class SectionsPagerAdapter(private val viewpager: Context, fragmentManager: FragmentManager, data: Bundle) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var fragmentBundle: Bundle

    init {
        fragmentBundle = data
    }

    private val TAB_TITTLE = intArrayOf(R.string.tab1, R.string.tab2)

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle

        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return viewpager.resources.getString(TAB_TITTLE[position])
    }

}