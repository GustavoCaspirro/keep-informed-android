package br.com.keep_informed.domain.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.mob18.newsapilibrary.model.Article
import br.com.keep_informed.R
import br.com.keep_informed.databinding.CellArticleBinding
import br.com.keep_informed.extensions.load

class ArticlesAdapter : RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {
    private val articles = mutableListOf<Article>()

    var articleListener : ArticleListener? = null
    var showBookmarkIndicator : Boolean = true

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

    fun updateArticle(value: Article){
        articles.mapIndexed { index, article ->
            if (article.title.contentEquals(value.title)){
                notifyItemChanged(index)
            }
        }
    }


    inner class ArticleViewHolder(private val binding: CellArticleBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(article: Article){
            binding.titleTextView.text = article.title
            binding.articleTextView.text = article.description
            binding.articleImageView.load(article.urlToImage,null,null)
            binding.bookmarkImageView.setOnClickListener {
                articleListener?.onFavoriteClicked(article)
            }

            binding.bookmarkImageView.visibility = if(showBookmarkIndicator) View.VISIBLE else View.INVISIBLE
            binding.bookmarkImageView.setImageResource(
                if (article.isFavorite){
                    R.drawable.ic_bookmark_black_24dp
                }else{
                    R.drawable.ic_bookmark_border_black_24dp
                }
            )
        }
    }
}