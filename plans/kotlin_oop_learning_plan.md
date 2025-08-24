# Kotlin 객체지향 프로그래밍 학습 계획

이 문서는 Kotlin의 객체지향 프로그래밍(OOP) 핵심 개념을 실습 중심으로 학습하기 위한 계획입니다.

## 학습 목표
1.  **클래스와 생성자:** 객체의 기본 설계도인 클래스를 정의하고, 주/보조 생성자를 통해 객체를 초기화하는 방법을 익힙니다.
2.  **상속:** 코드를 재사용하고 클래스 간의 계층 구조를 만들기 위해 상속을 활용하는 방법을 학습합니다.
3.  **코틀린의 특별한 클래스:** `data`, `enum`, `sealed` 등 코틀린이 제공하는 특수한 목적의 클래스들의 사용법과 장점을 이해합니다.

---

## Step 1: 클래스와 생성자 (Classes and Constructors)

-   **개념:** 클래스는 속성(프로퍼티)과 동작(메서드)을 가지는 객체의 템플릿입니다. 생성자는 클래스로부터 객체(인스턴스)를 만들 때 호출되며, 프로퍼티를 초기화하는 역할을 합니다.
-   **실습 과제:**
    1.  `Person` 클래스를 만듭니다.
        -   `name` (이름, `String`)과 `age` (나이, `Int`)를 프로퍼티로 가집니다.
        -   객체가 생성될 때 `"Person 객체 생성 완료: 이름={name}, 나이={age}"` 메시지를 출력하도록 `init` 블록을 추가합니다.
    2.  이름만으로 `Person` 객체를 만들 수 있도록 보조 생성자를 추가합니다.
        -   이름만 인자로 받고, 나이는 `0`으로 기본 설정합니다.

-   **예상 코드 (`/lang_study/kotlin/OopStudy.kt`에 작성):**
    ```kotlin
    // Step 1: 클래스와 생성자
    class Person(val name: String, var age: Int) {
        init {
            println("Person 객체 생성 완료: 이름=${name}, 나이=${age}")
        }

        // 보조 생성자
        constructor(name: String) : this(name, 0) {
            println("보조 생성자 호출됨")
        }

        fun introduce() {
            println("안녕하세요, 저는 ${name}이고 ${age}세입니다.")
        }
    }
    ```

---

## Step 2: 상속 (Inheritance)

-   **개념:** 상속은 부모 클래스의 속성과 메서드를 자식 클래스가 물려받아 재사용하고 확장하는 기능입니다. 코틀린에서는 `open` 키워드가 붙은 클래스만 상속할 수 있습니다.
-   **실습 과제:**
    1.  `Person` 클래스를 상속 가능한 `open` 클래스로 변경합니다.
    2.  `Person`을 상속받는 `Student` 클래스를 만듭니다.
        -   `studentId` (학번, `String`) 프로퍼티를 추가합니다.
    3.  `Person`의 `introduce` 메서드를 `Student`에 맞게 재정의(`override`)하여 학번 정보도 함께 출력하도록 합니다.

-   **예상 코드 (`/lang_study/kotlin/OopStudy.kt`에 이어서 작성):**
    ```kotlin
    // Step 2: 상속
    open class Person(val name: String, var age: Int) {
        // ... (이전 코드)

        // open 키워드로 메서드를 재정의 가능하도록 설정
        open fun introduce() {
            println("안녕하세요, 저는 ${name}이고 ${age}세입니다.")
        }
    }

    class Student(name: String, age: Int, val studentId: String) : Person(name, age) {
        override fun introduce() {
            // super.introduce() // 부모 메서드 호출이 필요하다면 사용
            println("안녕하세요, 저는 ${age}세 ${name}입니다. 제 학번은 ${studentId}입니다.")
        }
    }
    ```

---

## Step 3: 코틀린의 특별한 클래스 (Special Classes)

-   **개념:** 코틀린은 특정 목적에 최적화된 편리한 클래스들을 제공합니다.
    -   **`data class`**: 데이터를 담기 위한 클래스로, `equals()`, `hashCode()`, `toString()`, `copy()` 등 유용한 메서드를 자동으로 생성해줍니다.
    -   **`enum class`**: 정해진 상수들의 집합을 표현하는 열거형 클래스입니다.
    -   **`sealed class`**: 상속을 특정 클래스들로만 제한하는 봉인된 클래스로, `when` 식과 함께 사용할 때 강력한 타입 안정성을 제공합니다.
-   **실습 과제:**
    1.  **Data Class**: `Book` 데이터 클래스를 만들고, 객체를 복사(`copy`)하며 일부 속성만 변경해봅니다.
    2.  **Enum Class**: `DayOfWeek` 열거형 클래스를 만들고, 요일에 따라 다른 활동을 추천하는 함수를 작성합니다.
    3.  **Sealed Class**: 네트워크 요청의 결과를 나타내는 `Result` 실드 클래스를 `Success`와 `Error` 상태로 나누어 정의하고, 결과에 따라 다른 메시지를 출력하는 함수를 작성합니다.

-   **예상 코드 (`/lang_study/kotlin/OopStudy.kt`에 이어서 작성):**
    ```kotlin
    // Step 3.1: Data Class
    data class Book(val title: String, val author: String, val price: Int)

    // Step 3.2: Enum Class
    enum class DayOfWeek(val koreanName: String) {
        MONDAY("월요일"),
        TUESDAY("화요일"),
        WEDNESDAY("수요일"),
        THURSDAY("목요일"),
        FRIDAY("금요일"),
        SATURDAY("토요일"),
        SUNDAY("일요일")
    }

    fun recommendActivity(day: DayOfWeek) {
        when(day) {
            DayOfWeek.SATURDAY, DayOfWeek.SUNDAY -> println("${day.koreanName}에는 휴식을 취하세요.")
            else -> println("${day.koreanName}에는 열심히 공부하세요!")
        }
    }

    // Step 3.3: Sealed Class
    sealed class Result {
        data class Success(val data: String) : Result()
        data class Error(val message: String) : Result()
    }

    fun handleResult(result: Result) {
        when(result) {
            is Result.Success -> println("성공: ${result.data}")
            is Result.Error -> println("오류: ${result.message}")
        }
    }
    ```

---

## 다음 단계
-   위 실습 코드를 `lang_study/kotlin/OopStudy.kt` 파일에 단계별로 작성하고, `main` 함수에서 각 클래스의 인스턴스를 생성하여 결과를 확인합니다.
-   각 개념이 왜 필요한지, 어떤 상황에서 유용한지 고민하며 학습합니다.
