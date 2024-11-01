
# QFit

QFit 프로젝트는 피트니스 관련 기능을 제공하는 스프링 부트 기반 애플리케이션입니다. 회원, 트레이너, 리뷰 등의 관리를 위한 REST API를 제공합니다.

## 프로젝트 구조

- **`src/main/java/com/qfit/mvc`**
  - **`QfitApplication.java`**: 메인 애플리케이션 파일로, 애플리케이션 실행 엔트리 포인트입니다.
  - **config**: 데이터베이스 및 Swagger 설정 파일
    - `DBConfig.java`: 데이터베이스 설정 클래스
    - `SwaggerConfig.java`: Swagger API 문서화를 위한 설정 클래스
  - **controller**: REST API 컨트롤러 패키지
    - `ReviewController.java`, `TaskRestController.java`: 리뷰 및 태스크 관련 API
    - **user**: 사용자 관련 REST 컨트롤러
      - `MemberRestController.java`: 일반 회원 관리 API
      - `TraineeRestController.java`: 트레이니(훈련생) 관련 API
      - `TrainerRestController.java`: 트레이너 관련 API
  - **model/dao**: 데이터 액세스 객체 (DAO)
    - `ReviewDao.java`: 리뷰 관련 데이터베이스 상호작용 클래스

## 클래스 다이어그램

아래는 QFit 프로젝트의 클래스 다이어그램입니다. 이 다이어그램은 `User`, `Task`, `TodayGuest`, `Review`, `Feedback` 등 주요 클래스와 이들의 관계를 시각적으로 보여줍니다.

![클래스 다이어그램](다이어그램_수정3.drawio.png)

## 설정 방법

1. **Java 및 Maven**: JDK 11 이상 및 Maven이 필요합니다. `mvnw` 및 `mvnw.cmd` 파일을 통해 로컬에 Maven 설치 없이 실행할 수 있습니다.
2. **데이터베이스 설정**: `DBConfig.java` 파일에서 데이터베이스 연결 정보를 설정해야 합니다.

## 빌드 및 실행 방법

1. 프로젝트 디렉토리에서 다음 명령어를 실행하여 프로젝트를 빌드하고 실행합니다:

   ```bash
   ./mvnw spring-boot:run
   ```

2. 서버는 기본적으로 `http://localhost:8080`에서 실행됩니다.

## Swagger 문서화

QFit 프로젝트는 API 문서화를 위해 Swagger를 사용합니다. 서버 실행 후 Swagger UI를 통해 API 문서를 확인할 수 있습니다.

### Swagger UI 접근

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- OpenAPI JSON 문서: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
- OpenAPI YAML 문서: [http://localhost:8080/v3/api-docs.yaml](http://localhost:8080/v3/api-docs.yaml)

## 주요 기능

- **회원 관리**: 회원 가입, 로그인, 정보 수정
- **리뷰 작성 및 조회**: 회원 및 트레이너에 대한 리뷰 관리
- **트레이너 관리**: 트레이너 등록, 정보 조회
- **태스크 관리**: 피트니스 태스크 관련 API

## 기여 방법

1. 이 리포지토리를 포크합니다.
2. 새로운 브랜치를 생성하여 기능을 추가합니다.
3. 변경 사항을 커밋하고 푸시합니다.
4. 풀 리퀘스트를 생성하여 변경 사항을 반영합니다.

---

프로젝트와 관련한 문의 사항은 이슈로 등록해 주세요.
