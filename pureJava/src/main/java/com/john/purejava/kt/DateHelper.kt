package com.john.purejava.kt

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by JohnZh on 1/8/21
 *
 * <p></p>
 */
object DateHelper {

    private val FORMATTER = SimpleDateFormat()

    private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZZZZZ"
    private const val DATE_JP_FORMAT = "MM月dd日(E) HH:mm"

    @JvmOverloads
    fun isBeforeNow(utcDate: String, utcFormat: String = DATE_FORMAT): Boolean {
        FORMATTER.applyPattern(utcFormat)
        return try {
            val parse = FORMATTER.parse(utcDate)
            parse < Calendar.getInstance().time
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun formatToJpDate(utcDate: String, utcFormat: String = DATE_FORMAT, jpFormat: String = DATE_JP_FORMAT): String {
        FORMATTER.applyPattern(utcFormat)
        return try {
            val simpleDateFormat = SimpleDateFormat(jpFormat, Locale.JAPAN)
            val date = FORMATTER.parse(utcDate)
            simpleDateFormat.format(date)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            ""
        }
    }
}