open class Person(val name: String, var age: Int) {
    init {
        println("Person 객체 생성 완료: 이름=${name}, 나이=${age}")
    }
    
    // open 키워드로 메서드를 재정의 가능하도록 설정
    open fun introduce() {
        println("안녕하세요, 저는 ${name}이고 ${age}세입니다.")
    }
}

// 'name' 앞에 'val' 추가
// 'name' 앞에 있던 'val'을 제거
class Student(name: String, age: Int, val studentId: String) : Person(name, age) {
    override fun introduce() {
        println("안녕하세요, 저는 ${age}세 ${name}입니다. 제 학번은 ${studentId}입니다.")
    }
}


fun main() {
    val s = Student("kk", 17, "1234")
    s.introduce() // introduce 메서드 호출 예시
    println("a= ${s.name}")
}