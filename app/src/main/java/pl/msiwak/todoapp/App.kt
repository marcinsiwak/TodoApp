package pl.msiwak.todoapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pl.msiwak.todoapp.util.di.appModule
import pl.msiwak.todoapp.util.di.viewModelModule
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        initializeTimber()
        initDependencyInjection()
    }

    private fun initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initDependencyInjection() {
        startKoin {
            androidContext(this@App)
            modules(listOf(viewModelModule, appModule))
        }
    }

}