package br.com.keep_informed.interactors.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.keep_informed.interactors.home.repository.HomeRepository
import br.com.keep_informed.services.news.results.ArticleResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    private val homeRepository: HomeRepository
): ViewModel() {

    private val _newsData = MutableLiveData<ArticleResult>().apply {
        homeRepository.fetchTopHeadlines()
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
    }

    val newsData : LiveData<ArticleResult> = _newsData

}