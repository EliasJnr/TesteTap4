package com.eliasjr.testetap4.interfaces

import com.eliasjr.testetap4.model.Movie

interface ListenerItems {
    var items: MutableList<Movie>
    fun notifyDataSetChanged()
}