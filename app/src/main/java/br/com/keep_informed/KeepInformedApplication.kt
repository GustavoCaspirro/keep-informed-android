package br.com.keep_informed

import android.app.Application
import br.com.keep_informed.di.DaggerKeepInformedComponent
import br.com.keep_informed.di.KeepInformedComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class KeepInformedApplication: Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    lateinit var component: KeepInformedComponent

    override fun onCreate() {
        super.onCreate()

        component = initDagger()
        component.inject(this)
    }

    private fun initDagger(): KeepInformedComponent {
        return DaggerKeepInformedComponent.builder()
            .application(this)
            .build()
    }


    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }


}