package com.damai.base.extensions

/**
 * Created by damai007 on 02/October/2023
 */

fun Int?.orZero() = this ?: 0

fun Boolean?.orFalse() = this ?: false

fun Double?.orZero() = this ?: 0.0

fun Long?.orZero() = this ?: 0L