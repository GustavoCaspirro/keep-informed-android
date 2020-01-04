package br.com.keep_informed.interactors.signin.module

import br.com.keep_informed.interactors.signin.viewmodel.SignInViewModelFactory
import br.com.keep_informed.services.authentication.repository.AuthRepository
import br.com.keep_informed.services.authentication.repository.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides

@Module
class SignInModule{

    @Provides
    fun provideAuthRepository() : AuthRepository{
        return AuthRepositoryImpl(FirebaseAuth.getInstance())
    }

    @Provides
    fun provideViewModelFactory(repository : AuthRepository): SignInViewModelFactory{
        return SignInViewModelFactory(repository)
    }
}
