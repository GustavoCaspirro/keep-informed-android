package br.com.keep_informed.interactors.bookmark.module

import br.com.keep_informed.interactors.bookmark.viewmodel.BookmarkViewModelFactory
import br.com.keep_informed.services.news.module.NewsModule
import br.com.keep_informed.services.news.repository.NewsRepository
import dagger.Module
import dagger.Provides

@Module(includes = [
    NewsModule::class
])
class BookmarkModule {


    @Provides
    fun provideHomeViewModelFactory(repository: NewsRepository) : BookmarkViewModelFactory {
        return BookmarkViewModelFactory(repository)

    }

}