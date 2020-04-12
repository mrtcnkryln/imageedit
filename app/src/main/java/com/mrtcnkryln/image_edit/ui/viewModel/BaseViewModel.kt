package com.mrtcnkryln.image_edit.ui.viewModel

import androidx.lifecycle.ViewModel
import com.mrtcnkryln.image_edit.di.ApiComponent
import com.mrtcnkryln.image_edit.di.DaggerApiComponent
import com.mrtcnkryln.image_edit.di.NetworkModule

abstract class BaseViewModel : ViewModel() {
    private val injector: ApiComponent = DaggerApiComponent
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is MainViewModel -> {
                injector.injectMainViewModel(this)
            }
        }
    }
}