package br.com.keep_informed.services.authentication.repository

import br.com.keep_informed.services.authentication.model.AuthResponse
import io.reactivex.Single

interface AuthRepository  {
    fun isValidUserName(value: String) : Boolean
    fun isValidPassword(value : String) : Boolean
    fun isValidPassword(value1 : String, value2: String) : Boolean
    fun signIn(username: String, password: String): Single<AuthResponse>
    fun signUp(username:String, password: String, confirmPassword : String) : Single<AuthResponse>
}