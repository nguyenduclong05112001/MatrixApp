package com.longhrk.app.ui.di

import com.longhrk.MFFirebaseMessagingService
import com.longhrk.matrix.viewmodel.MatrixViewModel
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Singleton
//@Component(modules = [MFModule::class])
//interface MFComponent {
//    fun inject(service: MFFirebaseMessagingService)
//}

@Module
@InstallIn(SingletonComponent::class)
class MFModule {

}