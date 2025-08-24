package org.example

import kotlin.text.get

val sum = {x:Int, y:Int -> x+y }

fun <T> Collection<T>.joinToString(
    separator: String = ", ",
    prefix: String = "",
    postfix: String = ""
): String {
    val sb = StringBuilder(prefix)
    var first = true
    for (element in this) {
        if (!first) {
            sb.append(separator)
        } else {
            first = false
        }
        sb.append(element.toString())
    }
    sb.append(postfix)
    return sb.toString()
}


fun <T> List<T>.firstAndLast(): Pair<T, T> {
    return first() to last()
}

// 확장 프로퍼티
val <T> List<T>.lastOne: T
    get() = this[size-1]

fun main() {
    println(sum(3,4))

    val list = listOf(1, 2, 3)
    val result = list.joinToString(" - ") // "1 - 2 - 3"
    println(result)
    println(list.firstAndLast())   // (1,3)

    val letters = ('a' .. 'z').toList()
    println(letters.slice<Char>(0..2)) // [a, b, c]
    println(letters.slice<Char>(10..13)) // [k, l, m, n]

    println(letters.lastOne) // z
}