package br.com.keep_informed.services.news.results

import br.com.fiap.mob18.newsapilibrary.model.Article
import br.com.keep_informed.services.ServiceResult
import br.com.keep_informed.services.ServiceStatus

class ArticleResult(result: List<Article>?, status: ServiceStatus, throwable: Throwable?) :
    ServiceResult<List<Article>?>(result, status, throwable) {

    companion object{
        fun resultSuccess(value: List<Article>) : ArticleResult {
            return ArticleResult(
                value,
                ServiceStatus.SUCCESS,
                null
            )
        }

        fun resultError(throwable: Throwable?) : ArticleResult {
            return ArticleResult(
                null,
                ServiceStatus.ERROR,
                throwable
            )
        }

        fun resultLoading() : ArticleResult {
            return ArticleResult(
                null,
                ServiceStatus.LOADING,
                null
            )
        }
    }
}