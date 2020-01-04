package br.com.keep_informed.services.news.module

import br.com.fiap.mob18.newsapilibrary.api.GoogleApiBuilder
import br.com.fiap.mob18.newsapilibrary.api.NewsApi
import br.com.keep_informed.services.news.repository.NewsRepository
import br.com.keep_informed.services.news.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class NewsModule {

    @Provides
    fun provideGoogleNewsApi() : NewsApi {
        return GoogleApiBuilder()
            .withApiKey("ca2d507945314e22b354bfc01251061a")
            .build()
    }

    @Provides
    fun provideNewsRepository(api: NewsApi) : NewsRepository{
        return NewsRepositoryImpl(api)
    }
}