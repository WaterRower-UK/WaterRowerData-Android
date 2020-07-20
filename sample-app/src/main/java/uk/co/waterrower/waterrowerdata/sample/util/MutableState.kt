package uk.co.waterrower.waterrowerdata.sample.util

import androidx.compose.MutableState
import androidx.compose.mutableStateOf

fun <T, R> MutableState<T>.map(f: (T) -> R): MutableState<R> {
    return mutableStateOf(f(value))
}
