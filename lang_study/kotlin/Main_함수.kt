package org.example

// 1. 기본 함수
fun greet(name: String): String {
    return "안녕하세요, $name 님!"
}

// 2. 단일 표현식 함수
fun add(a: Int, b: Int) = a + b

// 3. 기본 매개변수
fun createMessage(message: String = "기본 메시지") {
    println(message)
}

// 4. 가변 인자
fun printAll(vararg items: String) {
    for (item in items) {
        println(item)
    }
}

// 5. 확장 함수
fun String.addExclamation() = "$this!"

fun main() {
    // 함수 사용
    println(greet("철수"))
    println("합계: ${add(5, 3)}")

    createMessage() // 기본값 사용
    createMessage("커스텀 메시지")

    printAll("사과", "바나나", "오렌지")

    val myString = "안녕"
    println(myString.addExclamation()) // "안녕!"
}