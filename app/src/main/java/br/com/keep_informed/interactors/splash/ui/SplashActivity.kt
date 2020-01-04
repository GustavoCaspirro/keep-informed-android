package br.com.keep_informed.interactors.splash.ui

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.keep_informed.R
import br.com.keep_informed.interactors.navigation.ui.NavigationActivity
import br.com.keep_informed.interactors.signin.ui.SignInActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth


class SplashActivity : AppCompatActivity() {

    interface OnTaskExecute {
        fun onExecute()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        SleepTask().execute(object :
            OnTaskExecute {
            override fun onExecute() {
                startNextActivity()
            }
        })
    }

    fun startNextActivity() {
        intent.flags =  Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        intent = if (FirebaseAuth.getInstance().currentUser == null) {
            Intent(this, SignInActivity::class.java)
        } else {
            Intent(this,NavigationActivity::class.java)
        }
        finish()
        startActivity(intent)
    }

    class SleepTask : AsyncTask<OnTaskExecute, Void, OnTaskExecute?>(){
        override fun doInBackground(vararg p0: OnTaskExecute?): OnTaskExecute? {
            Thread.sleep(3000)
            return p0[0]
        }

        override fun onPostExecute(result: OnTaskExecute?) {
            super.onPostExecute(result)
            result?.onExecute()
        }
    }
}
