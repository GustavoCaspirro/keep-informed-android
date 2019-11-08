package br.com.keep_informed.di

import android.app.Application
import br.com.keep_informed.KeepInformedApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    BuilderModule::class
])
interface KeepInformedComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: KeepInformedApplication): Builder

        fun build(): KeepInformedComponent
    }

    fun inject(app: KeepInformedApplication)
}
