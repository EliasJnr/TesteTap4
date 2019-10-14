package com.eliasjr.testetap4.dagger.components

import com.eliasjr.testetap4.ui.fragments.MainFragment
import com.eliasjr.testetap4.ui.fragments.MovieDetailsFragment
import dagger.Subcomponent

@Subcomponent
interface FragmentComponent {
    fun mainFragInject(mainFrag: MainFragment)
    fun detailsFragInject(detailsFrag: MovieDetailsFragment)
}