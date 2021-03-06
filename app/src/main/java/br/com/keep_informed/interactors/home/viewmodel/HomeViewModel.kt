package br.com.keep_informed.interactors.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.fiap.mob18.newsapilibrary.model.Article
import br.com.keep_informed.domain.BaseViewModel
import br.com.keep_informed.domain.SingleLiveEvent
import br.com.keep_informed.services.news.repository.NewsRepository
import br.com.keep_informed.services.news.results.ArticleResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    private val repository: NewsRepository
): BaseViewModel() {


    private val _newsData = MutableLiveData<ArticleResult>().apply {
        disposables.add(
            repository.fetchTopHeadLines(null,null,"br",50,0)
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

    fun favorite(article: Article) {
        disposables.add(
            repository.favorite(article)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    favoriteEventData.postValue(ArticleResult.resultSuccess(listOf(it)))
                },{
                    favoriteEventData.postValue(ArticleResult.resultError(it))
                })
        )

    }


    val newsData : LiveData<ArticleResult> = _newsData
    val favoriteEventData = SingleLiveEvent<ArticleResult>()

}