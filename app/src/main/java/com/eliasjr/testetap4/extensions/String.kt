package com.eliasjr.testetap4.extensions

import com.eliasjr.testetap4.utilities.apiImg

fun String.toFullUrl(): String {
    return "$apiImg$this"
}
