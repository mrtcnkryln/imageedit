package com.mrtcnkryln.image_edit.di

import com.mrtcnkryln.image_edit.ui.viewModel.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ApiComponent {
    @Component.Builder
    interface Builder {
        fun build(): ApiComponent
        fun networkModule(networkModule: NetworkModule): Builder
    }

    fun injectMainViewModel(mainViewModel: MainViewModel)

}