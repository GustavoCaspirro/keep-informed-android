package br.com.keep_informed.interactors.signin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.keep_informed.R
import br.com.keep_informed.databinding.ActivitySignInBinding
import br.com.keep_informed.interactors.navigation.ui.NavigationActivity
import br.com.keep_informed.interactors.signin.viewmodel.SignInViewModel
import br.com.keep_informed.interactors.signin.viewmodel.SignInViewModelFactory
import br.com.keep_informed.interactors.signup.ui.SignUpActivity
import br.com.keep_informed.services.ServiceStatus
import br.com.keep_informed.services.authentication.model.AuthResponse
import br.com.keep_informed.services.authentication.model.AuthStatusError
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_sign_in.*
import javax.inject.Inject

class SignInActivity : AppCompatActivity() {

    lateinit var binding : ActivitySignInBinding

    @Inject
    lateinit var viewModelFactory: SignInViewModelFactory
    lateinit var viewModel : SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this,viewModelFactory).get(SignInViewModel::class.java)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_in)

        binding.signInButton.setOnClickListener {
            viewModel.signIn(binding.userNameEditText.text.toString(), binding.passwordEditText.text.toString())
        }

        binding.continueTextView.setOnClickListener {
            startMainView()
        }

        viewModel.authResultData.observe(this, Observer {
            when(it.status){
                ServiceStatus.SUCCESS -> onSignInSuccess(it.result)
                ServiceStatus.LOADING -> setLoadingIndicator(true)
                ServiceStatus.ERROR -> onSignInError(it.throwable)
            }
        })



        binding.signUpButton.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
    }

    private fun onSignInError(throwable: Throwable?) {
        setLoadingIndicator(false)
        binding.errorTextView.text = throwable?.message
    }

    private fun setLoadingIndicator(loading: Boolean) {
        clearError()
        binding.isLoading = loading

    }

    private fun onSignInSuccess(result: AuthResponse?) {
        setLoadingIndicator(false)
        result?.let {
            if (it.success){
                startMainView()
            }else{
                when(result.errorType){
                    AuthStatusError.InvalidUserName -> showInvalidUserName()
                    AuthStatusError.InvalidPassword -> showInvalidPassword()
                    AuthStatusError.InvalidCredentials -> showInvalidCredentials()
                    null -> {}
                }
            }
        }

    }

    private fun startMainView() {
        finish()
        startActivity(
            Intent(this, NavigationActivity::class.java).also { intent ->
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
        )
    }

    private fun showInvalidUserName(){
        binding.userNameEditText.error = getString(R.string.error_invalid_username)
        binding.userNameEditText.requestFocus()
    }

    private fun showInvalidPassword(){
        binding.passwordEditText.error = getString(R.string.error_invalid_password)
        binding.passwordEditText.requestFocus()

    }
     private fun showInvalidCredentials(){
        binding.errorTextView.visibility = View.VISIBLE
        binding.errorTextView.text = getString(R.string.error_invalid_credentials)
     }

    private fun clearError(){
        binding.errorTextView.visibility = View.GONE
        binding.errorTextView.text = ""
    }
}
