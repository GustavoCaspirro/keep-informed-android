package br.com.keep_informed.interactors.search.module

import br.com.keep_informed.interactors.search.viewmodel.SearchViewModelFactory
import br.com.keep_informed.services.news.module.NewsModule
import br.com.keep_informed.services.news.repository.NewsRepository
import dagger.Module
import dagger.Provides

@Module(includes = [
    NewsModule::class
])
class SearchModule {

    @Provides
    fun provideViewModelFactory(repository: NewsRepository) : SearchViewModelFactory {
        return SearchViewModelFactory(repository)

    }
}