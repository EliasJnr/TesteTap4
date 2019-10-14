package com.eliasjr.testetap4.dagger

import android.app.Application
import com.eliasjr.testetap4.dagger.components.*
import com.eliasjr.testetap4.dagger.modules.AppModule
import com.eliasjr.testetap4.dagger.modules.DbModule
import com.eliasjr.testetap4.dagger.modules.NetModule

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        val appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .dbModule(DbModule(this))
            .netModule(NetModule())
            .build()
        setComponent(appComponent)
    }


   private fun setComponent(component: AppComponent) {
        appComponent = component
        viewModelComponent = appComponent.viewModelComponent()
        fragmentComponent = appComponent.fragmentComponent()
        actComponent = appComponent.activityComponent()
    }

    companion object {
        lateinit var appComponent: AppComponent
        lateinit var actComponent: ActivityComponent
        lateinit var viewModelComponent: ViewModelComponent
        lateinit var fragmentComponent: FragmentComponent
    }
}