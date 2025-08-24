package org.example

fun main() {
    // 1. for loop
    println("1부터 5까지:")
    for (i in 1..5) {
        println(i)
    }

    // 2. step 사용
    println("\n1부터 10까지 홀수:")
    for (i in 1..10 step 2) {
        println(i)
    }

    // 3. downTo 사용
    println("\n5부터 1까지 감소:")
    for (i in 5 downTo 1) println(i)

    // 4. until 사용 (상한 제외)
    println("\n1부터 5 전까지 (until):")
    for (i in 1 until 5) {
        println(i) // 1,2,3,4 출력
    }

    // 5. 컬렉션 순회
    val fruits = listOf("사과", "바나나", "오렌지")
    println("\n과일 목록:")
    for (fruit in fruits) {
        println(fruit)
    }

    // 6. 인덱스와 함께 순회
    println("\n과일 목록 (인덱스 포함):")
    for ((index, fruit) in fruits.withIndex()) {
        println("$index: $fruit")
    }

    // 7. while loop
    println("\nWhile loop:")
    var count = 3
    while (count > 0) {
        println(count)
        count--
    }

    // 8. break & continue
    println("\nBreak 예제:")
    for (i in 1..10) {
        if (i == 5) break
        println(i)
    }
}