package br.com.keep_informed.interactors.bookmark.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.fiap.mob18.newsapilibrary.model.Article
import br.com.keep_informed.domain.BaseViewModel
import br.com.keep_informed.domain.SingleLiveEvent
import br.com.keep_informed.services.news.repository.NewsRepository
import br.com.keep_informed.services.news.results.ArticleResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BookmarkViewModel(
    private val repository: NewsRepository
): BaseViewModel() {


    private val _newsData = MutableLiveData<ArticleResult>().apply {
        disposables.add(
            repository.fetchFavorites()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { postValue(ArticleResult.resultLoading()) }
                .subscribe(
                    {
                        postValue(ArticleResult.resultSuccess(it.articles))
                    },
                    {
                        postValue(ArticleResult.resultError(it))
                    })
        )

    }



    val newsData : LiveData<ArticleResult> = _newsData

}