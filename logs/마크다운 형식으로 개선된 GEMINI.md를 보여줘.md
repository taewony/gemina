# **GEMINA: AI CLI 기반 팀 앱 개발 프레임워크**

* **팀 목표:** AI와 함께 아이디어를 구체적인 모바일 & 웹 앱으로 실현하고, 협업을 통해 빠르게 검증하며 함께 성장합니다.  
* **핵심 사이클:** Plan(계획) → Do(개발) → See(리뷰/분석) → Act(개선/문서화)  
* **방법론:** **점진적이고 반복적인 개선.** 단순한 기능에서 복잡한 기능으로, 쉬운 문제에서 어려운 문제로 팀 전체가 함께 해결해 나갑니다.  
* **This GEMINA project root directory:** \\gemina  
* **The current workspace:** \\gemina\\python

## **GEMINA 프로젝트 폴더 구조**

* **폴더 구조**

/gemina  
├── .gemini/commands      \# \[팀 공유\] 공통으로 사용할 custom 명령어 정의  
├── .gemini/docs          \# \[팀 공유\] 공통 학습 자료 (튜토리얼, API 문서 등)  
├── GEMINI.md             \# \[팀 공유\] 프로젝트의 목표와 협업 규칙을 정의하는 최상위 문서  
├── plans                 \# \[개인/팀\] 작업 계획, 요구사항 분석 문서 (스프린트, 개인 Task)  
├── docs                  \# \[팀 공유\] 학습 결과, 기능 설계, 아키텍처, 의사결정 기록(ADR)  
├── logs                  \# \[개인\] 개인별 학습 및 개발 일지  
├── ComposeLab            \# Android Compose UI App 프로젝트  
├── openknights\_mobile    \# 모바일 앱 예시 (오픈소스 경진대회)  
└── your\_project          \# 학생 프로젝트

## **수업 안내: AI Tool 기반 MVP 앱 개발**

\[ 수업 개요 \]  
AI tool을 활용하여, MVP 앱을 빠르게 개발하며 팀의 아이디어를 실험합니다.

* Firebase 기반 안드로이드 앱 개발  
* Supabase \+ NextJS 기반 웹 앱 개발  
* AI 기능을 활용한 지능형 앱 개발 (팀이 기술 stack 및 주제를 선택)

## **개발 환경 및 규칙**

* **기본 AI Tool:** **Gemini CLI**  
* **소스코드 관리:** **GitHub** (아래 Git 브랜치 전략 및 PR 규칙 준수)  
* **문서 작성:** **Markdown** (편집은 VS Code 사용)  
* **작업 관리:** **GitHub Issues** 및 **Projects** 활용

## **팀 협업 워크플로우 (Team Workflow)**

1. **스프린트 계획 (Sprint Planning):**  
   * GitHub Projects에 이번 주 목표(Milestone) 설정.  
   * 구현할 기능(Epic)을 정의하고, 세부 작업(Task)을 GitHub Issues로 등록 후 담당자 할당.  
2. **기능 개발 (Feature Development):**  
   * 담당자는 자신의 Issue에 연결된 feature 브랜치를 생성. (아래 Git 전략 참고)  
   * 개인 개발 워크플로우에 따라 기능 구현.  
3. **코드 리뷰 (Code Review):**  
   * 기능 구현 완료 후, develop 브랜치로 **Pull Request (PR)** 생성.  
   * 최소 1명 이상의 팀원에게 코드 리뷰 요청. 리뷰어는 코드의 품질, 버그 가능성, 스타일 가이드 준수 여부 등을 확인.  
4. **통합 및 배포 (Merge & Deploy):**  
   * 리뷰가 완료되고 CI/CD 파이프라인을 통과하면 PR을 develop 브랜치에 병합(Merge).  
   * 주기적으로 develop 브랜치의 안정적인 버전을 main 브랜치로 병합하여 배포.  
5. **회고 (Retrospective):**  
   * 스프린트 종료 후, 팀 회고를 통해 잘한 점, 아쉬운 점, 개선할 점(KPT)을 논의하고 다음 스프린트에 반영.

## **Git 브랜치 전략 및 Pull Request (PR) 프로세스**

* **주요 브랜치:**  
  * main: 릴리즈된 안정적인 버전의 코드. 직접적인 commit은 금지.  
  * develop: 다음 릴리즈를 위해 개발 중인 코드. 모든 기능 브랜치의 통합 대상.  
* **보조 브랜치:**  
  * feature/{기능이름}: 새로운 기능 개발 (예: feature/login-ui)  
  * fix/{수정내용}: 버그 수정 (예: fix/auth-bug)  
  * docs/{문서이름}: 문서 추가 및 수정 (예: docs/readme-update)  
* **PR 규칙:**  
  * PR 제목은 \[Feat\] 로그인 기능 추가 와 같이 어떤 작업을 했는지 명확히 작성.  
  * PR 본문에는 관련된 GitHub Issue 번호를 반드시 포함. (예: Closes \#12)  
  * 코드 리뷰가 완료되기 전까지는 WIP (Work In Progress) 라벨을 붙여 병합 방지.

## **개인 학습/개발 워크플로우 (Individual Workflow)**

1. study:start → 오늘의 개발 목표와 작업할 Issue 선언.  
2. **Pre-study** → Gemini CLI, 공식 문서, 팀 docs 폴더를 참고하여 기술 학습.  
3. **Plan 문서 작성** → 담당 Issue에 대한 구체적인 구현 계획(Tasklist)을 plans 폴더에 작성.  
4. **구현 (Do)** → feature 브랜치에서 코드 작성 및 실험.  
5. **Review & Design 문서 작성 (See)** → 구현 결과와 설계 내용을 docs 폴더에 정리.  
6. **Pull Request 생성 (Act)** → 팀 워크플로우에 따라 코드 리뷰 요청.  
7. study:stop → 학습 종료 및 개인 학습일지(logs) 작성.

## **사전 지식**

* Git/GitHub 사용 경험 (특히 브랜치, PR 경험)  
* Markdown 문법 (cheatsheet)  
* 프로그래밍 기초 (Kotlin, Typescript, Python)  
* Database(Firebase, Supabase) 기본 개념  
* AI Tool 활용 기본기  
  * 구조적 프롬프트 작성법  
  * AI에게 내가 원하는 바를 구체적으로 알리기.  
  * 개발 혹은 대화 결과가 만족스럽기 위한 성공 조건이 무엇인지 명시하기.