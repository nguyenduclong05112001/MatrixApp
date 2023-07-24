package com.longhrk

import android.app.Application
import com.longhrk.matrix.viewmodel.MatrixViewModel
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MFApplication : Application(){
    @Inject lateinit var matrixViewModel: MatrixViewModel

    override fun onCreate() {
        super.onCreate()
        matrixViewModel.getSession()
    }
}