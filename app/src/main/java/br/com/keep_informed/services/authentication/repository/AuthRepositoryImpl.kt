package br.com.keep_informed.services.authentication.repository

import android.util.Patterns
import br.com.keep_informed.services.authentication.model.AuthResponse
import br.com.keep_informed.services.authentication.model.AuthStatusError
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Single
import java.lang.Exception

class AuthRepositoryImpl(
    private val auth: FirebaseAuth
) : AuthRepository {


    override fun signIn(username: String, password: String) : Single<AuthResponse>{
        return Single.create<AuthResponse> {
            if (! isValidUserName(username)){
                it.onSuccess(AuthResponse(false, AuthStatusError.InvalidUserName))
            }else if (! isValidPassword(password)){
                it.onSuccess(AuthResponse(false, AuthStatusError.InvalidPassword))
            }else{
                auth.signInWithEmailAndPassword(username,password).addOnCompleteListener {task ->
                    if (task.isSuccessful){
                        it.onSuccess(AuthResponse(task.isSuccessful,null))
                    }else{
                        it.onSuccess(AuthResponse(task.isSuccessful,AuthStatusError.InvalidCredentials))
                    }
                }
            }

        }
    }


    override fun signUp(
        username: String,
        password: String,
        confirmPassword: String
    ): Single<AuthResponse> {
        return Single.create<AuthResponse> {
            if (! isValidUserName(username)){
                it.onSuccess(AuthResponse(false, AuthStatusError.InvalidUserName))
            }else if (! isValidPassword(password,confirmPassword)){
                it.onSuccess(AuthResponse(false, AuthStatusError.InvalidPassword))
            }else{
                auth.createUserWithEmailAndPassword(username,password).addOnCompleteListener {task ->
                    if (task.isSuccessful){
                        it.onSuccess(AuthResponse(task.isSuccessful,null))
                    }else{
                        it.onError(task.exception ?: Exception())
                    }
                }
            }
        }
    }


    override fun isValidUserName(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }

    override fun isValidPassword(value: String): Boolean {
        return value.isNotEmpty()
    }

    override fun isValidPassword(value1: String, value2: String): Boolean {
        return (isValidPassword(value1) && value1.contentEquals(value2))
    }

}