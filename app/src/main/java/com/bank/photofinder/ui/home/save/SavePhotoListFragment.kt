package com.bank.photofinder.ui.home.save

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.bank.photofinder.R
import com.bank.photofinder.databinding.FragmentSavePhotoListBinding
import com.bank.photofinder.databinding.FragmentSearchPhotoBinding
import com.bank.photofinder.extensions.hideKeyboard
import com.bank.photofinder.model.Photo
import com.bank.photofinder.ui.base.BaseFragment
import com.bank.photofinder.ui.home.HomeViewModel
import com.bank.photofinder.ui.home.save.adapter.SavePhotoListAdapter
import com.bank.photofinder.ui.home.search.SearchPhotoFragment
import com.bank.photofinder.ui.home.search.adapter.SearchPhotoListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavePhotoListFragment :
    BaseFragment<FragmentSavePhotoListBinding>(R.layout.fragment_save_photo_list),
    SavePhotoListAdapter.OnItemClickListener {


    private val mSavePhotoViewModel: HomeViewModel by activityViewModels()

    private val adapter by lazy {
        SavePhotoListAdapter(this)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SavePhotoListFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObserver()
    }

    private fun setupView() {
        setupRecyclerView()
    }


    private fun setupRecyclerView() {
        mViewBinding.apply {
            photoSaveListRecyclerView.layoutManager = GridLayoutManager(activity, 2)
            photoSaveListRecyclerView.setHasFixedSize(true)
            photoSaveListRecyclerView.itemAnimator = null
            photoSaveListRecyclerView.adapter = adapter
        }
    }

    private fun setupObserver() {

        mSavePhotoViewModel.apply {
            savePhotoList.observe(viewLifecycleOwner) {
                //Log.d("")
                adapter.submitList(it)
            }
        }
    }

    override fun onItemRemoveClick(photo: Photo) {

    }
}