package br.com.keep_informed.interactors.signup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.keep_informed.domain.BaseViewModel
import br.com.keep_informed.services.authentication.repository.AuthRepository
import br.com.keep_informed.services.authentication.results.AuthResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SignUpViewModel(private val authRepository: AuthRepository) : BaseViewModel() {


    private val _authResultData = MutableLiveData<AuthResult>()

    val authResultData : LiveData<AuthResult> = _authResultData


    fun signUp(username : String, password: String, confirmPassword: String){
        disposables.add(
            authRepository.signUp(username,password, confirmPassword)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    _authResultData.postValue(AuthResult.resultLoading())
                }
                .subscribe({
                    _authResultData.postValue(AuthResult.resultSuccess(it))
                },
                {
                    _authResultData.postValue(AuthResult.resultError(it))
                })
        )
    }
}