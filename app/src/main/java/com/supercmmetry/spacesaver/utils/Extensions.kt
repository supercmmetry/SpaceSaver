package com.supercmmetry.spacesaver.utils

import android.content.Intent

fun Boolean.toInt() : Int {
    return if (this) {
        1
    } else {
        0
    }
}