package br.com.keep_informed.interactors.signin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.keep_informed.domain.BaseViewModel
import br.com.keep_informed.services.authentication.repository.AuthRepository
import br.com.keep_informed.services.authentication.results.AuthResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SignInViewModel  (
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _authResultData = MutableLiveData<AuthResult>()

    val authResultData : LiveData<AuthResult> = _authResultData


    fun signIn(username : String, password: String){
        disposables.add(
            authRepository.signIn(username,password)
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