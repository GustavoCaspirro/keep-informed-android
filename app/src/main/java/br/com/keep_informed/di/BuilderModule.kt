package br.com.keep_informed.di

import br.com.keep_informed.interactors.home.module.HomeModule
import br.com.keep_informed.interactors.home.ui.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class BuilderModule {

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindNewsFragment() : HomeFragment

}