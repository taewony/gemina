package org.example

fun main() {
    val score = 85
    val grade: String

    // 1. if-else 문
    if (score >= 90) {
        grade = "A"
    } else if (score >= 80) {
        grade = "B"
    } else {
        grade = "C"
    }
    println("성적: $grade")

    // 1. if-else 식
    val grade2 = if (score >= 90) "A" else if (score >= 80) "B" else "C"
    println("성적: $grade2")

    // 2. when 표현식 (switch 대체)
    val result = when {
        score >= 90 -> "우수"
        score >= 80 -> "좋음"
        score >= 70 -> "보통"
        else -> "노력 필요"
    }
    println("결과: $result")

    // 3. when 값 매칭
    val x = 10
    when (x) {
        1 -> println("하나")
        10, 20 -> println("10 또는 20")
        in 11..19 -> println("11~19 사이")
        else -> println("기타")
    }

    // 4. 삼항 연산자 대신 if 표현식
    val isPass = if (score >= 70) "합격" else "불합격"
    println("시험 결과: $isPass")
}