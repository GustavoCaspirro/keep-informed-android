package br.com.keep_informed.interactors.signin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.keep_informed.services.authentication.repository.AuthRepository

class SignInViewModelFactory(private val repository: AuthRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignInViewModel(repository) as T
    }
}