package br.com.keep_informed.interactors.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.fiap.mob18.newsapilibrary.model.Article
import br.com.keep_informed.R
import br.com.keep_informed.databinding.FragmentHomeBinding
import br.com.keep_informed.domain.article.ArticleListener
import br.com.keep_informed.domain.article.ArticlesAdapter
import br.com.keep_informed.interactors.home.viewmodel.HomeViewModel
import br.com.keep_informed.interactors.home.viewmodel.HomeViewModelFactory
import br.com.keep_informed.services.ServiceStatus
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class HomeFragment : Fragment() {


    lateinit var binding : FragmentHomeBinding

    @Inject
    lateinit var viewModelFactory: HomeViewModelFactory

    lateinit var homeViewModel: HomeViewModel

    private val articlesAdapter = ArticlesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.lifecycleOwner = this

        homeViewModel = ViewModelProviders.of(requireActivity(),viewModelFactory).get(
            HomeViewModel::class.java)

        homeViewModel.newsData.observe(this, Observer {
            when(it.status){
                ServiceStatus.SUCCESS -> onNewsResult(it.result)
                ServiceStatus.LOADING -> setLoadingIndicator(true)
                ServiceStatus.ERROR -> onRequestError(it.throwable)
            }
        })

        homeViewModel.favoriteEventData.observe(this, Observer {
            when(it.status){
                ServiceStatus.SUCCESS -> onFavoriteResult(it.result)
            }
        })

        setupView()

        return binding.root
    }

    private fun onFavoriteResult(result: List<Article>?) {
        result?.map {articlesAdapter.updateArticle(it)}
    }

    private fun setupView() {
        with(binding.articlesRecyclerView){
            this.setHasFixedSize(true)
            this.layoutManager = LinearLayoutManager(context)
            articlesAdapter.articleListener = this@HomeFragment.asArticleListener()
            this.adapter = articlesAdapter

        }
    }

    override fun onDestroyView() {
        homeViewModel.newsData.removeObservers(this)
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
            Snackbar.make(binding.root,throwable.message ?: "",Snackbar.LENGTH_LONG)
        }
    }

    private fun asArticleListener() = object:ArticleListener{
        override fun onArticleClicked(article: Article) {
            val bundle = bundleOf("extra-article" to article)
            findNavController().navigate(R.id.navigation_detail, bundle)
        }

        override fun onFavoriteClicked(article: Article) {
            homeViewModel.favorite(article)
        }
    }
}




