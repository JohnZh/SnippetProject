package com.john.purejava

import com.john.purejava.kt.DateHelper
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by JohnZh on 2020/11/17
 *
 * <p></p>
 */
object Main {

    const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZZZZZ"
    const val LOCAL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    const val TIME = "2021-01-11T14:52:00+09:00"
    const val TIME0 = "2021-01-11T15:52:00+09:00"

    @JvmStatic
    fun main(args: Array<String>) {
        val domain = "http://www.baidu.com//http://ss"
        println(domain.replace("http://", "https://"))
    }
}