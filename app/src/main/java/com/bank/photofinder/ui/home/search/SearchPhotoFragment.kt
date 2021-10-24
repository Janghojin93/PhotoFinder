package com.bank.photofinder.ui.home.search

import SEARCH_DELAY
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bank.photofinder.R
import com.bank.photofinder.databinding.FragmentSearchPhotoBinding
import com.bank.photofinder.extensions.hideKeyboard
import com.bank.photofinder.ui.base.BaseFragment
import com.bank.photofinder.ui.home.HomeViewModel
import com.bank.photofinder.ui.home.search.adapter.SearchPhotoListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchPhotoFragment :
    BaseFragment<FragmentSearchPhotoBinding>(R.layout.fragment_search_photo) {

    private val mSearchPhotoViewModel: HomeViewModel by activityViewModels()
    private val adapter by lazy {
        SearchPhotoListAdapter()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchPhotoFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObserver()
    }

    private fun setupView() {
        setupRecyclerView()
        mViewBinding.apply {
            searchCustomEditView.apply {
                afterTextChangedCustom(SEARCH_DELAY) { query ->
                    Log.d("asd121asd", "::" + query)
                    if (query != "") {
                        mViewBinding.photoListRecylerView.scrollToPosition(0)
                        mSearchPhotoViewModel.searchPhoto(query)
                    }
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupRecyclerView() {
        mViewBinding.apply {
            photoListRecylerView.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
            photoListRecylerView.setHasFixedSize(true)
            photoListRecylerView.itemAnimator = null
            photoListRecylerView.adapter = adapter

            photoListRecylerView.apply {
                setOnTouchListener { _, _ ->
                    mViewBinding.searchCustomEditView.hideKeyboard()
                    false
                }
            }
        }

        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                Toast.makeText(activity, "로딩중", Toast.LENGTH_LONG).show()
            } else {
                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                error?.let {
                    Toast.makeText(activity, it.error.message, Toast.LENGTH_LONG).show()
                }
            }
        }


    }

    private fun setupObserver() {
        mSearchPhotoViewModel.photo.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }
}