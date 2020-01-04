package br.com.keep_informed.interactors.signup.module

import br.com.keep_informed.interactors.signup.viewmodel.SignUpViewModelFactory
import br.com.keep_informed.services.authentication.repository.AuthRepository
import br.com.keep_informed.services.authentication.repository.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides

@Module
class SignUpModule {

    @Provides
    fun provideAuthRepository() : AuthRepository {
        return AuthRepositoryImpl(FirebaseAuth.getInstance())
    }

    @Provides
    fun provideViewModelFactory(repository : AuthRepository): SignUpViewModelFactory {
        return SignUpViewModelFactory(repository)
    }
}