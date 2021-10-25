package com.bank.photofinder.ui.home.search

import SEARCH_DELAY
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.bank.photofinder.R
import com.bank.photofinder.databinding.FragmentSearchPhotoBinding
import com.bank.photofinder.extensions.hideKeyboard
import com.bank.photofinder.model.Photo
import com.bank.photofinder.ui.base.BaseFragment
import com.bank.photofinder.ui.home.HomeViewModel
import com.bank.photofinder.ui.home.search.adapter.SearchPhotoListAdapter
import com.bank.photofinder.utils.hide
import com.bank.photofinder.utils.show
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
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

    private fun setupView() {
        setupRecyclerView()
        mViewBinding.apply {
            searchCustomEditView.apply {
                afterTextChangedCustom(SEARCH_DELAY) { query ->
                    if (query.isNotEmpty() && queryCheck != query) {
                        mSearchPhotoViewModel.searchPhoto(query)
                        photoListRecyclerView.scrollToPosition(0)
                    }
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupRecyclerView() {
        mViewBinding.apply {
            photoListRecyclerView.layoutManager = GridLayoutManager(activity, 3)
            // StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            photoListRecyclerView.setHasFixedSize(true)
            photoListRecyclerView.adapter = adapter
            photoListRecyclerView.apply {
                setOnTouchListener { _, _ ->
                    mViewBinding.searchCustomEditView.hideKeyboard()
                    false
                }
            }
        }


        adapter.addLoadStateListener { loadState ->
            mViewBinding.apply {

                photoProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
                photoListRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                errorTextview.isVisible = loadState.source.refresh is LoadState.Error

                //데이터가 없을때
                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                    photoListRecyclerView.hide()
                    emptyTextview.show()
                } else {
                    emptyTextview.hide()
                }
            }
        }
    }

    private fun setupObserver() {
        mSearchPhotoViewModel.apply {
            photo.observe(viewLifecycleOwner) {
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            }

            currentQuery.observe(viewLifecycleOwner) {
                queryCheck = it
            }
        }
    }


    //좋아요 버튼 클릭 이벤트 처리
    override fun onItemSaveClick(photo: Photo) {
        mSearchPhotoViewModel.run {
            if (savePhotoList.value.isNullOrEmpty()) {
                onClickSavePhoto(photo)
                Snackbar.make(
                    mViewBinding.contentsLayout,
                    R.string.save_success,
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                if (containsCheck(photo, savePhotoList.value!!)) {
                    Snackbar.make(
                        mViewBinding.contentsLayout,
                        R.string.save_fail,
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    onClickSavePhoto(photo)
                    Snackbar.make(
                        mViewBinding.contentsLayout,
                        R.string.save_success,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    //이미 저장된 이미지인지 아닌지 체크
    private fun containsCheck(photo: Photo, photoList: List<Photo>): Boolean {
        var isContains = false

        for (i in photoList.indices) {
            if (photoList[i].thumbnail_url == photo.thumbnail_url) {
                isContains = true
                break
            }
        }

        return isContains
    }
}