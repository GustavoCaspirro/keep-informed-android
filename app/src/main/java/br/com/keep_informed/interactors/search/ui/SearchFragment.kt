package br.com.keep_informed.interactors.search.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.fiap.mob18.newsapilibrary.model.Article

import br.com.keep_informed.R
import br.com.keep_informed.databinding.FragmentSearchBinding
import br.com.keep_informed.domain.article.ArticleListener
import br.com.keep_informed.domain.article.ArticlesAdapter
import br.com.keep_informed.interactors.home.viewmodel.HomeViewModel
import br.com.keep_informed.interactors.home.viewmodel.HomeViewModelFactory
import br.com.keep_informed.interactors.navigation.events.SearchEvent
import br.com.keep_informed.interactors.search.viewmodel.SearchViewModel
import br.com.keep_informed.interactors.search.viewmodel.SearchViewModelFactory
import br.com.keep_informed.services.ServiceStatus
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject


class SearchFragment : Fragment() {

    lateinit var binding : FragmentSearchBinding

    @Inject
    lateinit var viewModelFactory: SearchViewModelFactory

    lateinit var viewModel: SearchViewModel

    private val articlesAdapter = ArticlesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        binding.lifecycleOwner = this

        viewModel = ViewModelProviders.of(this,viewModelFactory).get(
            SearchViewModel::class.java)

        viewModel.newsData.observe(this, Observer {
            when(it.status){
                ServiceStatus.SUCCESS -> onNewsResult(it.result)
                ServiceStatus.LOADING -> setLoadingIndicator(true)
                ServiceStatus.ERROR -> onRequestError(it.throwable)
            }
        })

        viewModel.favoriteEventData.observe(this, Observer {
            when(it.status){
                ServiceStatus.SUCCESS -> onFavoriteResult(it.result)
            }
        })

        setupView()

        return binding.root
    }


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe
    fun onSearchEvent(event : SearchEvent){
        viewModel.search(event.query)
    }

    private fun onFavoriteResult(result: List<Article>?) {
        result?.map {articlesAdapter.updateArticle(it)}
    }

    private fun setupView() {
        with(binding.articlesRecyclerView){
            this.setHasFixedSize(true)
            this.layoutManager = LinearLayoutManager(context)
            articlesAdapter.articleListener = this@SearchFragment.asArticleListener()
            this.adapter = articlesAdapter

        }
    }

    override fun onDestroyView() {
        viewModel.newsData.removeObservers(this)
        super.onDestroyView()
    }

    private fun setLoadingIndicator(isLoading: Boolean) {
        binding.isLoading = isLoading
    }

    private fun onNewsResult(result: List<Article>?) {
        result?.let {
            articlesAdapter.addAll(it)
        }
    }

    private fun onRequestError(throwable: Throwable?) {
        throwable?.let {
            Snackbar.make(binding.root,throwable.message ?: "", Snackbar.LENGTH_LONG)
        }
    }

    private fun asArticleListener() = object: ArticleListener {
        override fun onFavoriteClicked(article: Article) {
            viewModel.favorite(article)
        }
    }

}
