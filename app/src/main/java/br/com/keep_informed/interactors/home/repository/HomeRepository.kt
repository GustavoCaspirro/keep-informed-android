package br.com.keep_informed.interactors.home.repository

import br.com.fiap.mob18.newsapilibrary.model.ArticleResponse
import io.reactivex.Single

interface HomeRepository {

    fun fetchTopHeadlines(): Single<ArticleResponse>

}