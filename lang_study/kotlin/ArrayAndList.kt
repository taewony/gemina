fun main() {
    // 1. Array
    // 크기는 고정되지만(fixed-size), 요소의 값은 변경 가능(mutable)
    val anArray = arrayOf(1, 2, 3)
    println("초기 배열: ${anArray.joinToString()}")

    // 요소의 값을 변경하는 것은 가능
    anArray[0] = 10
    println("배열 요소 변경 후: ${anArray.joinToString()}")

    // 배열의 크기를 변경하는 것은 불가능 (컴파일 에러 발생)
    // anArray.add(4) // -> 이런 메소드 자체가 존재하지 않음

    println("---------------------------------")

    // 2. List (Immutable)
    // 크기와 요소 모두 변경 불가능
    val aList = listOf(1, 2, 3)
    println("초기 리스트: ${aList.joinToString()}")

    // 요소의 값을 변경하려고 하면 에러 발생 (컴파일 에러)
    // aList[0] = 10 // -> Unresolved operator: aList[0] = 10

    // 새로운 요소를 추가하는 것도 불가능 (컴파일 에러)
    // aList.add(4) // -> Unresolved reference: add

    println("---------------------------------")

    // 3. MutableList
    // 크기와 요소 모두 변경 가능
    val aMutableList = mutableListOf(1, 2, 3)
    println("초기 MutableList: ${aMutableList.joinToString()}")

    // 요소 값 변경 가능
    aMutableList[0] = 10
    println("MutableList 요소 변경 후: ${aMutableList.joinToString()}")

    // 요소 추가 가능
    aMutableList.add(4)
    println("MutableList 요소 추가 후: ${aMutableList.joinToString()}")

    // 요소 삭제 가능
    aMutableList.removeAt(1) // index 1의 요소(2)를 삭제
    println("MutableList 요소 삭제 후: ${aMutableList.joinToString()}")
}
