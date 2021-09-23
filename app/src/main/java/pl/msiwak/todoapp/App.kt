package pl.msiwak.todoapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pl.msiwak.todoapp.util.di.viewModelModule

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        initDependencyInjection()
    }

    private fun initDependencyInjection() {
        startKoin {
            androidContext(this@App)
            modules(listOf(viewModelModule))
        }
    }

}