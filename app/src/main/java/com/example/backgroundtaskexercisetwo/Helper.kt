package com.example.backgroundtaskexercisetwo

import java.util.*

fun main(){

    val rightNow: Calendar = Calendar.getInstance()
    val currentMin: Int = rightNow.get(Calendar.MINUTE)
    val currentHour: Int = rightNow.get(Calendar.HOUR)
    println(currentHour)
    for(i in currentHour..currentHour+10){
        println(currentMin)
    }

}