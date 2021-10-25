package com.bank.photofinder.ui.home.search

import SEARCH_DELAY
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bank.photofinder.R
import com.bank.photofinder.databinding.FragmentSearchPhotoBinding
import com.bank.photofinder.extensions.hideKeyboard
import com.bank.photofinder.model.Photo
import com.bank.photofinder.ui.base.BaseFragment
import com.bank.photofinder.ui.home.HomeViewModel
import com.bank.photofinder.ui.home.search.adapter.SearchPhotoListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class SearchPhotoFragment :
    BaseFragment<FragmentSearchPhotoBinding>(R.layout.fragment_search_photo),
    SearchPhotoListAdapter.OnItemClickListener {

    private val TAG = "SearchPhotoFragment.kt"
    private var queryCheck = ""

    private val mSearchPhotoViewModel: HomeViewModel by activityViewModels()
    private val adapter by lazy {
        SearchPhotoListAdapter(this)
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

    @ExperimentalCoroutinesApi
    private fun setupView() {
        setupRecyclerView()
        mViewBinding.apply {
            searchCustomEditView.apply {
                afterTextChangedCustom(SEARCH_DELAY) { query ->
                    if (query.isNotEmpty() && queryCheck != query) {
                        mSearchPhotoViewModel.searchPhoto(query)
                        photoListRecylerView.scrollToPosition(0)
                    }
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupRecyclerView() {
        mViewBinding.apply {
            photoListRecylerView.layoutManager = GridLayoutManager(activity, 3)
            // StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
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
            mViewBinding.apply {
                photoProgressBar.isVisible = loadState.source.refresh is LoadState.Loading

                photoListRecylerView.isVisible = loadState.source.refresh is LoadState.NotLoading

                errorTextview.isVisible = loadState.source.refresh is LoadState.Error

                //데이터가 없을때
                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                    photoListRecylerView.isVisible = false
                    emptyTextview.isVisible = true
                } else {
                    emptyTextview.isVisible = false
                }
            }
        }


    }

    @ExperimentalCoroutinesApi
    private fun setupObserver() {

        mSearchPhotoViewModel.apply {
            photo.observe(viewLifecycleOwner) {
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            }

            checkQuery.observe(viewLifecycleOwner) {
                queryCheck = it
            }
        }

    }

    //좋아요 버튼 클릭 이벤트 처리
    override fun onItemSaveClick(photo: Photo) {
        Log.d(TAG, "선택::" + photo.thumbnail_url)
        Toast.makeText(activity, R.string.save_success, Toast.LENGTH_SHORT).show();
    }
}