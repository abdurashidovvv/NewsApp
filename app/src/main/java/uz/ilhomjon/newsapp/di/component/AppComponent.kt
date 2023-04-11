package uz.ilhomjon.newsapp.di.component

import dagger.Component
import uz.ilhomjon.newsapp.MainActivity
import uz.ilhomjon.newsapp.di.module.DatabaseModule
import uz.ilhomjon.newsapp.di.module.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
}