package com.eliasjr.testetap4.dagger.components

import com.eliasjr.testetap4.ui.viewmodel.MovieViewModel
import dagger.Subcomponent

@Subcomponent
interface ViewModelComponent {
    fun vmInject(viewModel: MovieViewModel)
}