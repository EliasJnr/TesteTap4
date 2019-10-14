package com.eliasjr.testetap4.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eliasjr.testetap4.R
import com.eliasjr.testetap4.dagger.MainApplication
import com.eliasjr.testetap4.model.Movie
import kotlinx.android.synthetic.main.activity_detalhes.*

class DetailsActivity : AppCompatActivity() {

    init {
        MainApplication.actComponent.detailsActInject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)

        val movie = intent.getSerializableExtra("movie") as Movie
        tvTitulo.text = movie.title
    }
}
