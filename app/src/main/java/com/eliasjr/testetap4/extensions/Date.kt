package com.eliasjr.testetap4.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.toPtBr(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return "Lançamento:\n"+formatter.format(this)
}