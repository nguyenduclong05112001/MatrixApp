package com.longhrk.matrix.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.longhrk.data.preference.AppSharedPreference
import com.longhrk.matrix.viewmodel.repo.MatrixRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.matrix.android.sdk.api.session.Session
import javax.inject.Inject

@HiltViewModel
class MatrixViewModel @Inject constructor(
    private val repo: MatrixRepo,
    private val sharedPreference: AppSharedPreference
) : ViewModel() {

    private var _currentSession = MutableStateFlow<Session?>(null)
    val currentSession = _currentSession.asStateFlow()

    private var _isShowProgressBar = MutableStateFlow(false)
    val isShowProgressBar = _isShowProgressBar.asStateFlow()

    private var _titleCurrent = MutableStateFlow("Login")
    val titleCurrent = _titleCurrent.asStateFlow()

    fun getToken() = sharedPreference.getToken()
    fun setToken(token: String) = sharedPreference.setToken(token)

    fun getStatusSession() = sharedPreference.getStatusSession()
    fun setStatusSession(status: Boolean) = sharedPreference.setStatusSession(status)

    fun setTitleCurrent(title: String) {
        _titleCurrent.value = title
    }

    fun updateSession(session: Session) {
        _currentSession.value = session
    }

    fun getSession() {
        _currentSession.value = repo.getCurrentSession()
    }

    suspend fun loginInApp(
        username: String,
        password: String,
        homeServer: String,
        context: Context
    ) {
        _isShowProgressBar.value = true
        _currentSession.value = repo.launchAuthProcess(
            username = username,
            password = password,
            homeServer = homeServer,
            context = context
        )
        _isShowProgressBar.value = false
    }
}