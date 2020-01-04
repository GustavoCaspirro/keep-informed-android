package br.com.keep_informed.interactors.about

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import br.com.keep_informed.BuildConfig
import br.com.keep_informed.R
import br.com.keep_informed.databinding.ActivityAboutBinding
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

class AboutActivity : AppCompatActivity() {

    lateinit var binding : ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_about)
        binding.versionTextView.text = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"

        var textAbout = FirebaseRemoteConfig.getInstance().getString("text_about")
        binding.aboutTextView.text = Html.fromHtml(textAbout)


        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = getString(R.string.label_about)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
