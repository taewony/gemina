# Gemini CLI와 VS Code 확장 프로그램 연결 문제 해결 과정

## 1. 문제 상황

Gemini CLI 실행 후 `/ide status` 명령어를 입력했을 때, 다음과 같은 오류 메시지가 발생하며 VS Code Companion 확장 프로그램과 연결이 실패함.

```
✕ 🔴 Disconnected: Failed to connect to IDE companion extension for VS Code. Please ensure the extension is running. To install the extension, run /ide install.
```

## 2. 진단 과정

### 가. 네트워크 포트 확인
- **실행 명령어:** `netstat -ano`
- **결과:** 활성화된 네트워크 연결 목록이 비어 있어, 확장 프로그램이 사용하는 포트가 리스닝 상태가 아님을 확인. 이는 확장 프로그램의 서버 기능이 정상적으로 시작되지 않았거나, CLI가 포트 정보를 제대로 찾지 못했을 가능성을 시사함.

### 나. 일반적인 원인 검색
- **검색어:** `VS Code extension CLI connection problem`
- **결과:** 문제가 너무 일반적이어서, 해결을 위해서는 확장 프로그램의 이름, 정확한 오류 메시지 등 구체적인 정보가 필요함을 확인.

### 다. 특정 오류 메시지로 재검색
- **검색어:** `"Gemini CLI Companion" "Failed to connect to IDE companion extension"`
- **결과:** 이 문제의 주된 원인이 **IDE 내장 터미널이 아닌 외부 터미널(cmd, PowerShell 등)을 사용**하여 발생한다는 사실을 확인함.

## 3. 원인 분석

VS Code의 "Gemini CLI Companion" 확장 프로그램은 실행될 때, CLI와의 통신에 필요한 환경 변수(`GEMINI_CLI_IDE_WORKSPACE_PATH`, `GEMINI_CLI_IDE_SERVER_PORT` 등)를 **VS Code 내장 터미널에만 설정**해준다.

따라서, VS Code 외부에서 독립적으로 실행된 터미널은 이 환경 변수 값을 받지 못하므로, CLI가 어느 작업 공간(Workspace)과 포트(Port)로 확장 프로그램과 통신해야 하는지 알 수 없어 연결에 실패하게 된다.

## 4. 해결 방법

VS Code 상단 메뉴에서 **`터미널` > `새 터미널`**을 선택하여 VS Code 내에 통합된 터미널을 연다.

이 내장 터미널에서 Gemini CLI 관련 명령어(`gemini`, `/ide status` 등)를 실행한다.

## 5. 결론

Gemini CLI와 같이 IDE와 깊게 통합되어 작동하는 도구를 사용할 때는, 도구가 정상적으로 작동하는 데 필요한 환경 변수나 컨텍스트가 자동으로 설정되는 **IDE 내장 터미널을 사용하는 것이 필수적**이다. 문제가 해결되었으며, 향후 유사한 문제 발생 시 내장 터미널 사용 여부를 가장 먼저 확인해야 한다.
