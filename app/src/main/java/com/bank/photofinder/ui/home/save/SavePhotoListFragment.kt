package com.bank.photofinder.ui.home.save

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bank.photofinder.R
import com.bank.photofinder.databinding.FragmentSavePhotoListBinding
import com.bank.photofinder.databinding.FragmentSearchPhotoBinding
import com.bank.photofinder.ui.base.BaseFragment
import com.bank.photofinder.ui.home.HomeViewModel
import com.bank.photofinder.ui.home.search.SearchPhotoFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavePhotoListFragment :
    BaseFragment<FragmentSavePhotoListBinding>(R.layout.fragment_save_photo_list) {


    private val mSavePhotoListViewModel: HomeViewModel by activityViewModels()

    companion object {
        @JvmStatic
        fun newInstance() = SavePhotoListFragment()
    }


}