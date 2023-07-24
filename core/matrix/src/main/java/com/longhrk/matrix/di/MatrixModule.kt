package com.longhrk.matrix.di

import android.content.Context
import com.longhrk.matrix.util.RoomDisplayNameFallbackProviderImpl
import com.longhrk.matrix.viewmodel.MatrixViewModel
import com.longhrk.matrix.viewmodel.repo.MatrixRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.matrix.android.sdk.api.Matrix
import org.matrix.android.sdk.api.MatrixConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MatrixModule {
    @Provides
    @Singleton
    fun providesMatrix(
        @ApplicationContext context: Context
    ) = Matrix(
        context = context, matrixConfiguration = MatrixConfiguration(
            roomDisplayNameFallbackProvider = RoomDisplayNameFallbackProviderImpl()
        )
    )

    @Provides
    @Singleton
    fun providesRepoMatrix(matrix: Matrix) = MatrixRepo(matrix)

    @Provides
    @Singleton
    fun providesMatrixViewModel(rpo: MatrixRepo) = MatrixViewModel(rpo)
}