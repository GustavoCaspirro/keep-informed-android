package br.com.keep_informed.interactors.home.repository

import br.com.fiap.mob18.newsapilibrary.model.ArticleResponse
import br.com.keep_informed.services.news.repository.NewsRepository
import io.reactivex.Single

class HomeRepositoryImpl(
    private val newsRepository : NewsRepository
) : HomeRepository {
    override fun fetchTopHeadlines(): Single<ArticleResponse> {
        return newsRepository.fetchTopHeadLines(
            null,
            null,
            "br",
            50,
            0
        )
    }
}