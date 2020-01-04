package br.com.keep_informed.interactors.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.keep_informed.services.authentication.repository.AuthRepository

class SignUpViewModelFactory(private val repository: AuthRepository): ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignUpViewModel(repository) as T
    }
}