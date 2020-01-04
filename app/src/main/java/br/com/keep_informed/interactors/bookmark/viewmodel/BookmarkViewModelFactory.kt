package br.com.keep_informed.interactors.bookmark.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.keep_informed.services.news.repository.NewsRepository

class BookmarkViewModelFactory(
    private val newsRepository: NewsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BookmarkViewModel(newsRepository) as T
    }


}