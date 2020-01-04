package br.com.keep_informed.interactors.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.fiap.mob18.newsapilibrary.model.Article
import br.com.fiap.mob18.newsapilibrary.model.Sorter
import br.com.keep_informed.domain.BaseViewModel
import br.com.keep_informed.domain.SingleLiveEvent
import br.com.keep_informed.services.news.repository.NewsRepository
import br.com.keep_informed.services.news.results.ArticleResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class SearchViewModel(
    private val repository: NewsRepository
): BaseViewModel() {
    private val _newsData = MutableLiveData<ArticleResult>()

    fun search(query: String){
        disposables.add(
            repository.fetchEverything(query,query,null,null,Sorter.PublishedAt,"br",100,1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { _newsData.postValue(ArticleResult.resultLoading()) }
                .subscribe(
                    {
                        _newsData.postValue(ArticleResult.resultSuccess(it.articles))
                    },
                    {
                        _newsData.postValue(ArticleResult.resultError(it))
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