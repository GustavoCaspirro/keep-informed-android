package br.com.keep_informed.domain.article

import br.com.fiap.mob18.newsapilibrary.model.Article

interface ArticleListener {

    fun onFavoriteClicked(article: Article)
    fun onArticleClicked(article: Article)

}