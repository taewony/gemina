/**
 * 코틀린 람다(Lambda) 함수의 다양한 활용법을 보여주는 예제 코드입니다.
 * 이 코드는 Kotlin Playground(play.kotlinlang.org)에서 바로 실행해볼 수 있습니다.
 */

// 3. 고차 함수 직접 만들어보기
// 람다를 인자로 받는 고차 함수
fun operate(a: Int, b: Int, operation: (Int, Int) -> Int): Int {
    return operation(a, b)
}

// 4. 수신 객체 지정 람다를 위한 데이터 클래스
data class Person(var name: String, var age: Int)

fun main() {
    println("### 1. 람다의 기본 ###")
    // 가장 기본적인 람다 함수 선언
    // 형식: { 매개변수 -> 반환값 }
    val sum: (Int, Int) -> Int = { a, b -> a + b }
    println("두 수의 합: ${sum(5, 3)}") // 출력: 두 수의 합: 8

    // 매개변수가 없는 람다
    val greet: () -> Unit = { println("Hello, Kotlin Lambda!") }
    greet() // 출력: Hello, Kotlin Lambda!

    // 매개변수가 하나인 람다 (it 키워드 사용)
    val square: (Int) -> Int = { it * it }
    println("5의 제곱: ${square(5)}") // 출력: 5의 제곱: 25

    // 타입 추론을 활용한 람다 선언
    val multiply = { a: Int, b: Int -> a * b }
    println("두 수의 곱: ${multiply(4, 6)}") // 출력: 두 수의 곱: 24

    println("\n------------------------------------\n")

    println("### 2. 컬렉션과 람다 (고차 함수) ###")
    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println("전체 숫자: $numbers")

    // forEach: 각 요소를 순회
    print("forEach: ")
    numbers.forEach { print("$it ") }
    println()

    // map: 각 요소를 변환하여 새로운 컬렉션 생성
    val squaredNumbers = numbers.map { it * it }
    println("map (제곱): $squaredNumbers")

    // filter: 조건에 맞는 요소만 선택하여 새로운 컬렉션 생성
    val evenNumbers = numbers.filter { it % 2 == 0 }
    println("filter (짝수): $evenNumbers")

    // 연쇄 호출 (Chaining)
    val result = numbers.filter { it % 2 != 0 } // 홀수만 선택
                         .map { it * 10 }     // 10을 곱함
                         .joinToString(", ") // 쉼표로 구분된 문자열로 합침
    println("Chaining (홀수*10): $result")

    println("\n------------------------------------\n")

    println("### 3. 고차 함수 사용하기 ###")
    // 미리 정의된 람다를 인자로 전달
    val sumResult = operate(10, 5, sum)
    println("고차 함수 결과 (덧셈): $sumResult")

    // 즉석에서 람다를 작성하여 전달
    val multiplyResult = operate(10, 5, { a, b -> a * b })
    println("고차 함수 결과 (곱셈): $multiplyResult")

    // 후행 람다 (Trailing Lambda): 함수의 마지막 인자가 람다인 경우, 괄호 밖으로 뺄 수 있음
    val subtractResult = operate(10, 5) { a, b -> a - b }
    println("고차 함수 결과 (뺄셈): $subtractResult")

    println("\n------------------------------------\n")

    println("### 4. 수신 객체 지정 람다 (Lambdas with Receivers) ###")
    val person = Person("Alice", 20)

    // apply: 객체 초기화 및 설정에 유용. 객체 자신을 반환.
    val configuredPerson = person.apply {
        name = "Bob"
        age = 30
    }
    println("apply 결과: $configuredPerson")

    // with: 특정 객체의 컨텍스트 내에서 여러 작업을 할 때 유용. 마지막 표현식을 반환.
    val personInfo = with(person) {
        "이름: $name, 나이: $age"
    }
    println("with 결과: $personInfo")

    println("\n------------------------------------\n")

    /*
    Q: result.length 는 get() 함수를 invoke한건가?

    A: 네, 정확히 이해하셨습니다.
    코틀린에서 `result.length`와 같이 클래스의 속성(property)에 접근하는 것처럼 보이는 코드는
    내부적으로 getter 함수인 `get()`을 호출하는 것입니다.

    코틀린은 `person.name`과 같이 필드에 직접 접근하는 것처럼 간단하게 사용할 수 있는
    프로퍼티 접근 구문(Property Access Syntax)을 제공하여 코드의 가독성을 높여줍니다.

    `String` 클래스의 `length` 프로퍼티는 `val`로 선언되어 있으므로 getter만 존재하며,
    `result.length` 코드는 컴파일러에 의해 `result.getLength()` 메서드 호출로 변환됩니다.
    */

    /*
    Q: 4. 수신 객체 지정 람다 방식은 확장 함수와도 같이 사용될 수 있는가? 수신 객체에서 수신이라는 단어는 뭘 받는다는 거지?

    A:
    ### 1. 수신 객체 지정 람다는 확장 함수와 같이 사용될 수 있는가?

    **네, 그렇습니다.** 사실, 수신 객체 지정 람다는 **확장 함수와 매우 밀접한 관련**이 있으며, 함께 사용될 때 강력한 시너지를 냅니다.
    `apply`, `run`, `with`, `also`, `let`과 같은 코틀린 표준 라이브러리의 스코프 함수(Scope Functions)들은 내부적으로 수신 객체 지정 람다를 활용하는 대표적인 예시입니다.
    이 함수들은 사실상 모든 타입에 대한 확장 함수로 구현되어 있습니다.

    예를 들어, `Int` 타입에 대해 "이 숫자가 짝수일 때만 특정 동작을 수행하는" 확장 함수를 직접 만들어 본다고 가정해 보겠습니다.

    ```kotlin
    // Int 타입에 대한 확장 함수 onEven() 정의
    // action이라는 수신 객체 지정 람다를 인자로 받음
    // 이 람다 안에서 'this'는 Int 자기 자신을 가리킴
    fun Int.onEven(action: Int.() -> Unit) {
        if (this % 2 == 0) {
            this.action() // 짝수일 때만 람다 실행
        }
    }

    // main 함수 안에 아래 코드를 추가하여 테스트해볼 수 있습니다.
    val number = 42
    number.onEven {
        // 이 블록은 Int의 확장 함수처럼 동작
        // 따라서 this.plus(5)나 그냥 plus(5) 사용 가능
        println("짝수입니다! 이 숫자에 5를 더하면 ${this.plus(5)} 입니다.")
    }
    
    3.onEven { // 3은 홀수이므로 이 블록은 실행되지 않음
        println("이 메시지는 보이지 않습니다.")
    }
    ```

    위 코드에서 `onEven`은 `Int`에 대한 확장 함수입니다. 이 함수는 `Int.() -> Unit` 타입의 람다를 인자로 받는데, 이것이 바로 **`Int`를 수신 객체로 지정하는 람다**입니다.
    `onEven` 함수 블록 안에서 `this`는 `Int` 객체(예: `number`)를 가리키고, `action()` 람다 블록 안에서도 `this`는 동일한 `Int` 객체를 가리키게 됩니다.

    이처럼 확장 함수와 수신 객체 지정 람다를 결합하면, 특정 클래스의 인스턴스 컨텍스트 내에서 실행되는 코드 블록(람다)을 인자로 넘길 수 있어 매우 유연하고 가독성 높은 코드를 작성할 수 있습니다. 이는 DSL(Domain-Specific Language)을 만들 때 핵심적인 기술이기도 합니다.

    ### 2. "수신 객체"에서 "수신"이라는 단어는 뭘 받는다는 거지?

    "수신(Receive)"이라는 단어는 **"람다 함수가 실행될 컨텍스트(맥락)가 되는 객체를 받는다"**는 의미입니다.

    일반적인 람다를 생각해 보겠습니다.
    `val sum = { a: Int, b: Int -> a + b }`
    이 람다는 단순히 두 개의 `Int`를 **인자(argument)로 받아서** 더한 값을 반환합니다. 람다 자신이 어떤 객체에 소속되어 동작하는 것이 아닙니다.

    하지만 수신 객체 지정 람다는 다릅니다.
    ```kotlin
    val person = Person("Alice", 20)
    person.apply { // 이 apply 블록이 바로 수신 객체 지정 람다입니다.
        // 여기서 name과 age는 누구의 것일까요?
        name = "Bob"
        age = 30
        // this.name, this.age 와 동일
    }
    ```

    위 `apply`의 람다 블록 `{}` 안에서는 `name`과 `age`라는 프로퍼티를 아무런 한정자 없이 직접 사용하고 있습니다. 이 `name`과 `age`는 바로 **수신 객체인 `person`의 프로퍼티**입니다.

    즉, 이 람다는 `person`이라는 객체를 "수신"하여, 그 객체의 컨텍스트 안으로 들어가서 실행되는 것입니다. 람다 블록 안에서 `this` 키워드는 바로 그 수신 객체(`person`)를 가리킵니다.

    **요약:**

    *   **수신(Receive):** 람다 블록이 실행될 때, 그 람다의 주인이자 기준이 되는 객체를 받는다는 의미입니다.
    *   **수신 객체(Receiver Object):** 람다에게 자신의 컨텍스트를 제공해주는 객체입니다. (위 예시에서는 `person`)
    *   **효과:** 람다 블록 내에서 수신 객체의 `public` 멤버(프로퍼티, 메서드)들을 `this.` 접두사 없이 바로 접근할 수 있게 됩니다. 마치 그 클래스의 멤버 함수 안에서 코드를 작성하는 것과 같은 효과를 줍니다.
    */
}