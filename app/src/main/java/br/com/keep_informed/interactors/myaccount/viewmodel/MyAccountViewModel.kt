package br.com.keep_informed.interactors.myaccount.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.keep_informed.domain.BaseViewModel
import com.google.firebase.auth.FirebaseAuth

class MyAccountViewModel : BaseViewModel() {


    private val _userData = MutableLiveData<String>().apply {
        FirebaseAuth.getInstance().currentUser?.let {
            postValue(it.email)
        } ?: postValue("")
    }

    val userData : LiveData<String> = _userData


    fun checkUserLogged(){
        FirebaseAuth.getInstance().currentUser?.let {
            _userData.postValue(it.email)
        } ?: _userData.postValue("")
    }

    fun signOut() {
        FirebaseAuth.getInstance().signOut()
        checkUserLogged()
    }

}