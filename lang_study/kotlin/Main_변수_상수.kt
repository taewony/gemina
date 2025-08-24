package org.example

// 5. 상수 (컴파일 타임 상수)
const val PI = 3.141592

fun ex(x: Int): Int {
    val y: Int
    y = x + 100
    return y
}

fun main() {
    val z = ex(7)
    println("z = $z")

    // 1. 변수 선언
    val immutableValue = "변경 불가" // 읽기 전용 (val)
    var mutableValue = 10           // 변경 가능 (var)
    mutableValue = 20

    // 2. 타입 추론
    val name = "김코틀린"   // String으로 추론
    val age = 25          // Int로 추론
    val height = 175.5    // Double로 추론

    // 3. 명시적 타입 선언
    val score: Int = 100
    val temperature: Double = 23.5

    // 4. Nullable 변수
    var nullableString: String? = null
    nullableString = "이제 값 있음"

    println("이름: $name, 나이: $age, 키: $height")
    println("PI = $PI")
    println("Nullable: len($nullableString) = ${nullableString?.length ?: "null입니다"}")
}