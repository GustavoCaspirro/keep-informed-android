package br.com.keep_informed.interactors.home.module

import br.com.keep_informed.interactors.home.repository.HomeRepository
import br.com.keep_informed.interactors.home.repository.HomeRepositoryImpl
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
    fun provideHomeRepository(newsRepository: NewsRepository) : HomeRepository{
        return HomeRepositoryImpl(newsRepository)
    }

    @Provides
    fun provideHomeViewModelFactory(homeRepository: HomeRepository) : HomeViewModelFactory{
        return HomeViewModelFactory(homeRepository)

    }

}