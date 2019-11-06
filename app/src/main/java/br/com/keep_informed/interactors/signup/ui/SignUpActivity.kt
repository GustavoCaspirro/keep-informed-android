package br.com.keep_informed.interactors.signup.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import br.com.keep_informed.R
import br.com.keep_informed.databinding.ActivitySignUpBinding
import br.com.keep_informed.interactors.navigation.ui.NavigationActivity

class SignUpActivity : AppCompatActivity() {

    lateinit var binding : ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up)

        binding.sigUpButton.setOnClickListener {
            finish()
            startActivity(
                Intent(this,NavigationActivity::class.java).also {
                    it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    it.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                }
            )
        }
    }
}
