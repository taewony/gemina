[참고]
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