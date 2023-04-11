package uz.ilhomjon.newsapp

import android.app.Application
import uz.ilhomjon.newsapp.di.component.AppComponent
import uz.ilhomjon.newsapp.di.component.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().build()
    }
}