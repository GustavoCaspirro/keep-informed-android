package br.com.keep_informed

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {

    interface OnTaskExecute {
        fun onExecute()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        SleepTask().execute(object : OnTaskExecute {
            override fun onExecute() {
                startNextActivity()
            }
        })
    }

    fun startNextActivity() {

        val intent = Intent(this, SignInActivity::class.java)
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
