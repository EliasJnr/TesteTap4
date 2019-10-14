package com.eliasjr.testetap4.dagger.components

import com.eliasjr.testetap4.ui.activity.MainActivity
import dagger.Subcomponent

@Subcomponent
interface ActivityComponent {
    fun mainActInject(mainAct: MainActivity)
}