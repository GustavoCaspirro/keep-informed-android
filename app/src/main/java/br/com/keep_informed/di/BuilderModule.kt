package br.com.keep_informed.di

import br.com.keep_informed.interactors.bookmark.module.BookmarkModule
import br.com.keep_informed.interactors.bookmark.ui.BookMarkFragment
import br.com.keep_informed.interactors.home.module.HomeModule
import br.com.keep_informed.interactors.home.ui.HomeFragment
import br.com.keep_informed.interactors.signin.module.SignInModule
import br.com.keep_informed.interactors.signin.ui.SignInActivity
import br.com.keep_informed.interactors.signin.viewmodel.SignInViewModel
import br.com.keep_informed.interactors.signup.module.SignUpModule
import br.com.keep_informed.interactors.signup.ui.SignUpActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class BuilderModule {

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindNewsFragment() : HomeFragment

    @ContributesAndroidInjector(modules = [BookmarkModule::class])
    abstract fun bindBookmarkFragment() : BookMarkFragment

    @ContributesAndroidInjector(modules = [SignInModule::class])
    abstract fun bindSignInActivity() : SignInActivity

    @ContributesAndroidInjector(modules = [SignUpModule::class])
    abstract fun bindSignUpActivity() : SignUpActivity

}