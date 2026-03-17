package com.study.development.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.study.development.domain.usecase.auth.RegisterUseCase

class RegisterViewModel(private val registerUseCase: RegisterUseCase) : ViewModel() {

    private val _registerSuccess = MutableLiveData<Boolean>()
    val registerSuccess: LiveData<Boolean> = _registerSuccess

    fun register(name: String, email: String, password: String) {
        _registerSuccess.value = registerUseCase(name, email, password)
    }
}
