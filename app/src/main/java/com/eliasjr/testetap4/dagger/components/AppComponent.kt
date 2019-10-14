package com.eliasjr.testetap4.dagger.components

import com.eliasjr.testetap4.dagger.modules.AppModule
import com.eliasjr.testetap4.dagger.modules.DbModule
import com.eliasjr.testetap4.dagger.modules.NetModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class, DbModule::class, AppModule::class])
interface AppComponent {
    fun viewModelComponent(): ViewModelComponent
    fun fragmentComponent(): FragmentComponent
    fun activityComponent(): ActivityComponent
}