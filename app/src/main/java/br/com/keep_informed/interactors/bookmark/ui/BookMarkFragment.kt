package br.com.keep_informed.interactors.bookmark.ui

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
import br.com.keep_informed.databinding.FragmentBookmarkBinding
import br.com.keep_informed.domain.article.ArticleListener
import br.com.keep_informed.domain.article.ArticlesAdapter
import br.com.keep_informed.interactors.bookmark.viewmodel.BookmarkViewModel
import br.com.keep_informed.interactors.bookmark.viewmodel.BookmarkViewModelFactory
import br.com.keep_informed.services.ServiceStatus
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class BookMarkFragment : Fragment() {

    lateinit var binding : FragmentBookmarkBinding

    @Inject
    lateinit var viewModelFactory: BookmarkViewModelFactory

    lateinit var viewModel: BookmarkViewModel

    private val articlesAdapter = ArticlesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookmark, container, false)

        binding.lifecycleOwner = this

        viewModel = ViewModelProviders.of(this,viewModelFactory).get(
            BookmarkViewModel::class.java)

        viewModel.newsData.observe(this, Observer {
            when(it.status){
                ServiceStatus.SUCCESS -> onNewsResult(it.result)
                ServiceStatus.LOADING -> setLoadingIndicator(true)
                ServiceStatus.ERROR -> onRequestError(it.throwable)
            }
        })

        setupView()

        return binding.root
    }


    private fun setupView() {
        with(binding.articlesRecyclerView){
            this.setHasFixedSize(true)
            this.layoutManager = LinearLayoutManager(context)
            articlesAdapter.articleListener = asArticleListener()
            articlesAdapter.showBookmarkIndicator = false
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
        override fun onArticleClicked(article: Article) {
            val bundle = bundleOf("extra-article" to article)
            findNavController().navigate(R.id.navigation_detail, bundle)
        }

        override fun onFavoriteClicked(article: Article) {}


    }

}
