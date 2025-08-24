class Person (val name: String, age: Int) {
    init {
        println("Person 객체 생성 완료: 이름=${name}, 나이=${age}")
    }
}

fun main() {
    val p = Person("kk", 17)
    println(" a= ${p.name}")
}