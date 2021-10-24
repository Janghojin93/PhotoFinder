package com.bank.photofinder.ui.home

import ANIMATION_DURATION
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bank.photofinder.R
import com.bank.photofinder.databinding.ActivityHomeBinding
import com.bank.photofinder.ui.base.BaseActivity
import com.bank.photofinder.ui.home.save.SavePhotoListFragment
import com.bank.photofinder.ui.home.search.SearchPhotoFragment
import com.bank.photofinder.utils.NetworkUtils
import com.bank.photofinder.utils.getColorRes
import com.bank.photofinder.utils.hide
import com.bank.photofinder.utils.show
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>() {

    override val mViewModel: HomeViewModel by viewModels()
    override fun getViewBinding(): ActivityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
    private val tabLayoutTextArray = arrayOf("검색", "보관함")

    private val pagerAdapter: ScreenSlidePagerAdapter by lazy {
        ScreenSlidePagerAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        setupView()
        handleNetworkChanges()

    }

    private fun setupView() {
        mViewBinding.apply {
            homeViewModel = mViewModel
            lifecycleOwner = this@HomeActivity
            pager.adapter = pagerAdapter
            pager.isUserInputEnabled = false
        }


        TabLayoutMediator(mViewBinding.tabs, mViewBinding.pager) { tab, position ->
            tab.text = tabLayoutTextArray[position]
        }.attach()


    }


    private fun handleNetworkChanges() {
        NetworkUtils.getNetworkLiveData(applicationContext).observe(this) { isConnected ->
            if (!isConnected) {
                mViewBinding.textViewNetworkStatus.text = getString(R.string.no_text_connectivity)
                mViewBinding.networkStatusLayout.apply {
                    mNetworkCheck = false
                    show()
                    setBackgroundColor(getColorRes(R.color.colorStatusNotConnected))
                    animate().alpha(1f)
                }
            } else {
                if (!mNetworkCheck) {
                    mViewBinding.textViewNetworkStatus.text = getString(R.string.text_connectivity)
                    mViewBinding.networkStatusLayout.apply {
                        setBackgroundColor(getColorRes(R.color.colorStatusConnected))
                        animate()
                            .alpha(0.1f)
                            .setStartDelay(ANIMATION_DURATION)
                            .setDuration(ANIMATION_DURATION)
                            .setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator) {
                                    hide()
                                }
                            })
                    }
                    mNetworkCheck = true
                }
            }
        }
    }


    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 2
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> SearchPhotoFragment.newInstance()
                else -> SavePhotoListFragment.newInstance()
            }
        }
    }

}