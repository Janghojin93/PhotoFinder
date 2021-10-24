package com.bank.photofinder.ui.splash

import androidx.lifecycle.ViewModel
import com.bank.photofinder.data.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val photoRepository: PhotoRepository) :
    ViewModel() {

}