package uz.ilhomjon.newsapp.di.component

import dagger.Component
import uz.ilhomjon.newsapp.MainActivity
import uz.ilhomjon.newsapp.di.module.DatabaseModule
import uz.ilhomjon.newsapp.di.module.NetworkModule
import uz.ilhomjon.newsapp.view.ui.HomeFragment
import uz.ilhomjon.newsapp.view.ui.InfoFragment
import uz.ilhomjon.newsapp.view.ui.SavedFragment
import uz.ilhomjon.newsapp.view.ui.SelectFragment
import uz.ilhomjon.newsapp.view.ui.SplashScreenFragment
import uz.ilhomjon.newsapp.view.ui.register.ThirdFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun injectThirdFragment(thirdFragment: ThirdFragment)
    fun injectHomeFragment(homeFragment: HomeFragment)
    fun injectSelectFragment(selectFragment: SelectFragment)
    fun injectSplashFragment(splashScreenFragment: SplashScreenFragment)
    fun injectInfoFragment(infoFragment: InfoFragment)
    fun injectBookmarkFragment(savedFragment: SavedFragment)
}