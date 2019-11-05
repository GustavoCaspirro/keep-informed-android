package br.com.keep_informed.interactors.splash.ui

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.keep_informed.R
import br.com.keep_informed.interactors.navigation.ui.NavigationActivity
import br.com.keep_informed.interactors.signin.ui.SignInActivity

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
        val intent = Intent(this, NavigationActivity::class.java)
        intent.flags =  Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

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
