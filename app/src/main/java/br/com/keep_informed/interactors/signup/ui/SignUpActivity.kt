package br.com.keep_informed.interactors.signup.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.keep_informed.R
import br.com.keep_informed.databinding.ActivitySignUpBinding
import br.com.keep_informed.interactors.navigation.ui.NavigationActivity
import br.com.keep_informed.interactors.signup.viewmodel.SignUpViewModel
import br.com.keep_informed.interactors.signup.viewmodel.SignUpViewModelFactory
import br.com.keep_informed.services.ServiceStatus
import br.com.keep_informed.services.authentication.model.AuthResponse
import br.com.keep_informed.services.authentication.model.AuthStatusError
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_sign_in.*
import javax.inject.Inject

class SignUpActivity : AppCompatActivity() {

    lateinit var binding : ActivitySignUpBinding

    @Inject
    lateinit var viewModelFactory: SignUpViewModelFactory
    lateinit var viewModel : SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this,viewModelFactory).get(SignUpViewModel::class.java)


        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up)
        binding.signUpButton.setOnClickListener {
            viewModel.signUp(
                username = binding.userNameEditText.text.toString(),
                password = binding.passwordEditText.text.toString(),
                confirmPassword =  binding.confirmPasswordEditText.text.toString()
                )
        }

        setupToolbar()

        viewModel.authResultData.observe(this, Observer {
            when(it.status){
                ServiceStatus.SUCCESS -> onUserCreatedSuccess(it.result)
                ServiceStatus.LOADING -> setLoadingIndicator(true)
                ServiceStatus.ERROR -> onUserCreatedError(it.throwable)
            }
        })




    }

    private fun onUserCreatedError(throwable: Throwable?) {
        setLoadingIndicator(false)
        binding.errorTextView.text = throwable?.message
    }

    private fun setLoadingIndicator(isLoading: Boolean) {
        clearError()
        binding.isLoading = isLoading
    }

    private fun onUserCreatedSuccess(result: AuthResponse?) {
        result?.let {
            setLoadingIndicator(false)
            if (it.success){
                showMainView()
            }else{
                when(result.errorType){
                    AuthStatusError.InvalidUserName -> showInvalidUserName()
                    AuthStatusError.InvalidPassword -> showInvalidPassword()
                    else -> {}
                }
            }
        }
    }

    private fun showMainView() {
        finish()
        startActivity(
            Intent(this, NavigationActivity::class.java).also {
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                it.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
        )
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = getString(R.string.label_sign_up)
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

    private fun showInvalidUserName(){
        binding.userNameEditText.error = getString(R.string.error_invalid_username)
        binding.userNameEditText.requestFocus()
    }

    private fun showInvalidPassword(){
        binding.passwordEditText.error = getString(R.string.error_invalid_or_unconfirmed_password)
        binding.passwordEditText.requestFocus()
    }

    private fun clearError(){
        binding.errorTextView.visibility = View.GONE
        binding.errorTextView.text = ""
    }
}
