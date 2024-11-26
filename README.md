
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

![클래스 다이어그램](클래스다이어그램_최종.png)

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

# qfit-front

이 프로젝트는 Vue 3.0 Composition API를 활용하여 프론트엔드를 구현한 템플릿입니다. Vite를 사용하여 빠르고 효율적인 개발 환경을 제공합니다.

## 프로젝트 설정

### 권장 IDE 설정

- [VSCode](https://code.visualstudio.com/)와 [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) 확장 프로그램을 설치하는 것을 추천합니다.
  - Vetur 확장 프로그램은 비활성화하세요.

## 프로젝트 구성

Vite 설정 파일에 대한 자세한 내용은 [Vite Configuration Reference](https://vite.dev/config/)를 참고하세요.

## 프로젝트 설치

다음 명령어를 사용하여 프로젝트 의존성들을 설치하세요:

```
npm install
```


## 개발 서버 실행

개발 환경에서 애플리케이션을 실행하려면 다음 명령어를 사용하세요:

```
npm run dev
```

이 명령어는 개발 서버를 실행하고, 파일 변경 시 자동으로 핫 리로드가 적용됩니다.

## 프로덕션 빌드

프로덕션 환경에 맞게 빌드를 하려면 다음 명령어를 사용하세요:

```
npm run build
```

빌드된 파일은 `dist` 폴더에 생성됩니다. 이 파일들을 서버에 배포하여 실제 서비스를 제공할 수 있습니다.

## Vue 3.0 Composition API 활용

이 프로젝트는 Vue 3.0의 Composition API를 활용하여 상태 관리와 컴포넌트 설계를 효율적으로 처리합니다. Composition API를 통해 더 나은 코드 분리와 재사용성을 제공합니다.

### 주요 특징
- **Composition API**: `ref`, `reactive`, `computed`, `watch` 등 다양한 Vue 3의 기능을 사용하여 상태 관리와 로직을 분리하고, 코드의 가독성과 재사용성을 높였습니다.
- **Vite**: 빠르고 효율적인 빌드 시스템을 제공하며, 핫 모듈 리플레이스먼트(HMR)를 지원합니다.
- **Vue Router**: 페이지 간의 이동을 위해 Vue Router를 사용하고 있습니다.

## 개발 중 사용한 주요 라이브러리

- Vue 3.0
- Vite
- Vue Router
- Pinia (상태 관리)

## 라이센스

이 프로젝트는 [MIT 라이센스](LICENSE)에 따라 제공됩니다.


## 기여 방법

1. 이 리포지토리를 포크합니다.
2. 새로운 브랜치를 생성하여 기능을 추가합니다.
3. 변경 사항을 커밋하고 푸시합니다.
4. 풀 리퀘스트를 생성하여 변경 사항을 반영합니다.

---

프로젝트와 관련한 문의 사항은 이슈로 등록해 주세요.
