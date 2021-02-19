package com.john.purejava.kt

/**
 * Created by JohnZh on 2020/11/17
 *
 * <p></p>
 */
interface Window {
    fun open()
}

class MinWindow(val x: Int) : Window {
    override fun open() {
        print("This window has $x glasses")
    }
}

class BigWindow(delegate: Window) : Window by delegate