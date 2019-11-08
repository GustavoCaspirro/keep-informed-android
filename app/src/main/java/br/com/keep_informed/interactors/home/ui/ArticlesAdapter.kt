package br.com.keep_informed.interactors.home.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.mob18.newsapilibrary.model.Article
import br.com.keep_informed.R
import br.com.keep_informed.databinding.CellArticleBinding
import br.com.keep_informed.extensions.load

class ArticlesAdapter : RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {
    private val articles = mutableListOf<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = DataBindingUtil.inflate<CellArticleBinding>(LayoutInflater.from(parent.context), R.layout.cell_article,parent,false)
        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    fun addAll(list: List<Article>){
        articles.clear()
        articles.addAll(list)
        notifyDataSetChanged()
    }


    inner class ArticleViewHolder(private val binding: CellArticleBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(article: Article){
            binding.tvNews.text = article.description
            binding.ivNews.load(article.urlToImage,null,null)
        }
    }
}