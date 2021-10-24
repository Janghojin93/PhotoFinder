package com.bank.photofinder.ui.home.search

import androidx.fragment.app.activityViewModels
import com.bank.photofinder.R
import com.bank.photofinder.databinding.FragmentSearchPhotoBinding
import com.bank.photofinder.ui.base.BaseFragment
import com.bank.photofinder.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchPhotoFragment :
    BaseFragment<FragmentSearchPhotoBinding>(R.layout.fragment_search_photo) {

    private val mSearchPhotoViewModel: HomeViewModel by activityViewModels()

    companion object {
        @JvmStatic
        fun newInstance() = SearchPhotoFragment()
    }


}