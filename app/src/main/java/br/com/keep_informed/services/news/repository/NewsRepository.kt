package br.com.keep_informed.services.news.repository

import br.com.fiap.mob18.newsapilibrary.model.ArticleResponse
import br.com.fiap.mob18.newsapilibrary.model.Category
import br.com.fiap.mob18.newsapilibrary.model.Sorter
import io.reactivex.Single
import java.util.*


interface NewsRepository {

    fun fetchTopHeadLines(query: String?,
                       category: Category?,
                       country: String,
                       pageSize: Int,
                       page: Int) : Single<ArticleResponse>

    fun fetchEverything(query: String?,
                        queryInTitle: String?,
                        from: Date?,
                        to: Date?,
                        sortBy: Sorter?,
                        language: String,
                        pageSize: Int,
                        page: Int) : Single<ArticleResponse>
}