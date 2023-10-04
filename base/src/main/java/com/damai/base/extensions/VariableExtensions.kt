package com.damai.base.extensions

import com.damai.base.utils.Constants.ONE_DAY_IN_MILLISECONDS

/**
 * Created by damai007 on 02/October/2023
 */

fun Int?.orZero() = this ?: 0

fun Boolean?.orFalse() = this ?: false

fun Double?.orZero() = this ?: 0.0

fun Long?.orZero() = this ?: 0L

fun Long.isCacheValid(days: Int): Boolean {
    val time = System.currentTimeMillis() - this
    return time < (days * ONE_DAY_IN_MILLISECONDS)
}