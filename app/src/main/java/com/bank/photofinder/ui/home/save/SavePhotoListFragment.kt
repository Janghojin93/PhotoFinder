package com.bank.photofinder.ui.home.save

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.bank.photofinder.utils.hide
import com.bank.photofinder.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_save_photo_list.*

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
            photoSaveListRecyclerView.adapter = adapter
        }
    }

    private fun setupObserver() {
        mSavePhotoViewModel.apply {
            savePhotoList.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    mViewBinding.emptyLayout.hide()
                    mViewBinding.photoSaveListRecyclerView.show()
                } else {
                    mViewBinding.emptyLayout.show()
                    mViewBinding.photoSaveListRecyclerView.hide()
                }
                adapter.submitList(it)
            }
        }
    }

    override fun onItemRemoveClick(photo: Photo) {
        deletePhoto(photo)
    }


    private fun deletePhoto(photo: Photo) {
        AlertDialog.Builder(activity).apply {
            setTitle(R.string.delete_photo_title)
            setMessage(R.string.delete_photo_text)
            setPositiveButton(R.string.delete) { _, _ ->
                mSavePhotoViewModel.onClickDeletePhoto(photo)
                Toast.makeText(activity, R.string.delete_photo_success, Toast.LENGTH_SHORT).show()
            }
            setNegativeButton(R.string.cancel, null)
        }.create().show()
    }
}