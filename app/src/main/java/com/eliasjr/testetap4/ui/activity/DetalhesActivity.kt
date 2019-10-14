package com.eliasjr.testetap4.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eliasjr.testetap4.R

class DetalhesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
