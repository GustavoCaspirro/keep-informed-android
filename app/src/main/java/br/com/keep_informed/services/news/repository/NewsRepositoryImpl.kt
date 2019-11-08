package br.com.keep_informed.services.news.repository

import br.com.fiap.mob18.newsapilibrary.api.NewsApi
import br.com.fiap.mob18.newsapilibrary.model.ArticleResponse
import br.com.fiap.mob18.newsapilibrary.model.Category
import br.com.fiap.mob18.newsapilibrary.model.Sorter
import io.reactivex.Single
import java.util.*

class NewsRepositoryImpl(
    private val api : NewsApi
): NewsRepository {
    override fun fetchTopHeadLines(
        query: String?,
        category: Category?,
        country: String,
        pageSize: Int,
        page: Int
    ): Single<ArticleResponse> {
        return Single.create {
            try {
                val response = api.fetchTopHeadlines(query,category,country,pageSize,page)
                if (! it.isDisposed){
                    it.onSuccess(response)
                }
            } catch (e: Throwable) {
                if (!it.isDisposed){
                    it.onError(e)
                }
            }
        }
    }

    override fun fetchEverything(
        query: String?,
        queryInTitle: String?,
        from: Date?,
        to: Date?,
        sortBy: Sorter?,
        language: String,
        pageSize: Int,
        page: Int
    ): Single<ArticleResponse> {
        return Single.create {
            try {
                val response = api.fetchEverything(query,queryInTitle,from,to,sortBy,language,pageSize,page)
                if (! it.isDisposed){
                    it.onSuccess(response)
                }
            } catch (e: Throwable) {
                if (!it.isDisposed){
                    it.onError(e)
                }
            }
        }

    }
}