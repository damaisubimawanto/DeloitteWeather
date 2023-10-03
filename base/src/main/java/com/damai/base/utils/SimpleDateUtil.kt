package com.damai.base.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by damai007 on 03/October/2023
 */
class SimpleDateUtil {

    companion object {

        fun parseDateToString(
            givenDate: Date,
            outputFormat: DateFormat
        ): String {
            val format = SimpleDateFormat(outputFormat.pattern, Locale.getDefault())
            return format.format(givenDate)
        }

        fun parseStringToDate(
            givenDateString: String,
            sourceFormat: DateFormat
        ): Date? {
            val format = SimpleDateFormat(sourceFormat.pattern, Locale.getDefault())
            return try {
                format.parse(givenDateString)
            } catch (e: ParseException) {
                null
            } catch (e: RuntimeException) {
                null
            } catch (e: Exception) {
                null
            }
        }

        fun getDateFromUnixTimestamp(
            unixTimestamp: Long
        ): Date {
            val timeInMillis = unixTimestamp * 1_000L
            return Date(timeInMillis)
        }

        fun getDayNameFromUnixTimestamp(
            unixTimestamp: Long
        ): String {
            val date = getDateFromUnixTimestamp(unixTimestamp = unixTimestamp)
            return parseDateToString(
                givenDate = date,
                outputFormat = DateFormat.EEEE
            )
        }
    }

    enum class DateFormat(val pattern: String) {
        DD_MM_YYYY("dd-MM-yyyy"),
        YYYY_MM_DD("yyyy-MM-dd"),
        HH_MM("hh:mm"),
        EEEE("EEEE")
    }
}