package br.com.keep_informed.interactors.details.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.fiap.mob18.newsapilibrary.model.Article
import br.com.keep_informed.R
import br.com.keep_informed.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*


class DetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail)

        val article : Article? = intent?.extras?.getParcelable("extra-article")

        setupToolbar()
        setupView(article)
    }

    private fun setupView(article: Article?) {
        article?.let {
            binding.titleTextView.text = article.title
            binding.contentTextView.text = article.content
            binding.dateTextView.text = SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(article.publishedAt)
            binding.shareImageView.setOnClickListener {
                val share = Intent.createChooser(Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, article.url)
                    putExtra(Intent.EXTRA_TITLE, article.title)
                }, null)
                startActivity(share)
            }
            Picasso.get().load(article.urlToImage).into(binding.mainImageView)
            binding.urlTextView.text = article.url

            binding.urlTextView.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                startActivity(browserIntent)
            }
        }

    }

    private fun setupToolbar() {
        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

}
