package br.com.keep_informed.interactors.signin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import br.com.keep_informed.R
import br.com.keep_informed.databinding.ActivitySignInBinding
import br.com.keep_informed.interactors.navigation.ui.NavigationActivity
import br.com.keep_informed.interactors.signup.ui.SignUpActivity

class SignInActivity : AppCompatActivity() {

    lateinit var binding : ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_in)

        binding.buttonSignIn.setOnClickListener {
            finish()
            startActivity(
                Intent(this,NavigationActivity::class.java).also {
                    it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    it.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                }
            )
        }

        binding.buttonSignUp.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }

    }
}
