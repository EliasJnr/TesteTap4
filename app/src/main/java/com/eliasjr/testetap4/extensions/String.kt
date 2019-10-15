package com.eliasjr.testetap4.extensions

import com.eliasjr.testetap4.utilities.apiImg

fun String.toUrlImage(): String {
    return "$apiImg$this"
}
