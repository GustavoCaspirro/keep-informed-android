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
            .withApiKey("b6524b510dfd412a974e20de2ecbeeaf")
            .build()
    }

    @Provides
    fun provideNewsRepository(api: NewsApi) : NewsRepository{
        return NewsRepositoryImpl(api)
    }
}