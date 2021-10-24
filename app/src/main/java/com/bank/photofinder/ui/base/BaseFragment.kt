package com.bank.photofinder.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BaseFragment<VB: ViewDataBinding>(private val layoutId: Int) : Fragment() {

    protected lateinit var mViewBinding: VB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mViewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return mViewBinding.root
    }


    //EditText 지연 함수
    fun EditText.afterTextChangedCustom(delay: Long, search: (String) -> Unit) {
        var job: Job? = null
        this.addTextChangedListener {
            it ?: return@addTextChangedListener
            job?.cancel()
            job = lifecycleScope.launch {
                delay(delay)
                search(it.toString())
            }
        }
    }

}