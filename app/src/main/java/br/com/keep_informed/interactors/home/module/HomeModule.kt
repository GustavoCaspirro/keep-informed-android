package br.com.keep_informed.interactors.home.module

import br.com.keep_informed.interactors.home.viewmodel.HomeViewModelFactory
import br.com.keep_informed.services.news.module.NewsModule
import br.com.keep_informed.services.news.repository.NewsRepository
import dagger.Module
import dagger.Provides

@Module(includes = [
    NewsModule::class
])
class HomeModule {


    @Provides
    fun provideHomeViewModelFactory(repository: NewsRepository) : HomeViewModelFactory{
        return HomeViewModelFactory(repository)

    }

}