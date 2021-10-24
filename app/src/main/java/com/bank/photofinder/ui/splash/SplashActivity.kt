package com.bank.photofinder.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.bank.photofinder.databinding.ActivitySplashBinding
import com.bank.photofinder.ui.base.BaseActivity
import com.bank.photofinder.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>() {

    private val activityScope = CoroutineScope(Dispatchers.Main)
    override val mViewModel: SplashViewModel by viewModels()
    override fun getViewBinding() = ActivitySplashBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        activityScope.launch {
            delay(500)
            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


}