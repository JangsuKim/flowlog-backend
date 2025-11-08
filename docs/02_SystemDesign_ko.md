# 🧠 Flowlog - 시스템 설계서  
[🇯🇵 日本語版はこちら](./02_SystemDesign_ja.md)

---

## 1. 개요
이 문서는 **Flowlog** 시스템의 전체 구조, 구성요소, 기술 스택 및 설계 방침을 정의합니다.  
초기 버전은 **인증(Authentication)**, **프로젝트 관리(Project Management)**, **작업 관리(Task Management)**를 중심으로 구성됩니다.

---

## 2. 시스템 아키텍처 개요

```
[Frontend] React (Next.js + TypeScript)
       ↓  REST API
[Backend] Spring Boot (Controller → Service → Repository)
       ↓
[Database] MySQL (Docker container)
```

- **아키텍처 패턴**: Clean Architecture + RESTful API  
- **인증 방식**: JWT 기반 토큰 인증  
- **배포 환경**: Docker Compose (개발), Vercel + Render (운영)

---

## 3. 기술 스택

| 계층 | 기술 | 버전 / 구성 |
|------|------|-------------|
| **프론트엔드** | React (Next.js 14), TypeScript, TailwindCSS, Zustand, dnd-kit, React Query | UI 및 상태 관리 |
| **백엔드** | Spring Boot 3, Java 17, Spring Security (JWT), JPA | API 및 비즈니스 로직 |
| **데이터베이스** | MySQL 8 (Docker 기반) | 영속 데이터 관리 |
| **테스트** | JUnit5, Mockito, React Testing Library | 단위 / 통합 테스트 |
| **CI/CD** | GitHub Actions | 빌드 및 자동 배포 |

---

## 4. ER 다이어그램 (논리 모델)

```
┌───────────────┐      ┌───────────────┐
│   users        │1   n│   projects     │
│───────────────│      │───────────────│
│ id (PK)       │◄────┤ owner_id (FK)  │
│ name          │      │ name           │
│ email         │      │ description    │
└───────────────┘      └───────────────┘
        │1                   │1
        │                    │
        ▼                    ▼
┌───────────────┐      ┌───────────────┐
│   tasks        │n   1│   columns      │
│───────────────│      │───────────────│
│ id (PK)       │      │ name           │
│ title         │      │ position       │
│ status        │      │ project_id(FK) │
│ assignee_id   │      └───────────────┘
└───────────────┘
```

---

## 5. API 설계 (예시)

| HTTP | 엔드포인트 | 설명 |
|------|-------------|------|
| `POST` | `/api/auth/signup` | 신규 사용자 등록 |
| `POST` | `/api/auth/login` | 로그인 (JWT 발급) |
| `GET` | `/api/projects` | 프로젝트 목록 조회 |
| `POST` | `/api/projects` | 프로젝트 생성 |
| `GET` | `/api/tasks/{id}` | 작업(Task) 상세 조회 |
| `PUT` | `/api/tasks/{id}` | 작업(Task) 수정 |
| `DELETE` | `/api/tasks/{id}` | 작업(Task) 삭제 |

---

## 6. 프론트엔드 설계

- **UI 프레임워크**: TailwindCSS  
- **상태 관리**: Zustand  
- **드래그 앤 드롭**: dnd-kit  
- **데이터 요청 관리**: React Query  
- **라우팅**: Next.js App Router  

### 📁 디렉터리 구조 예시

```
frontend/
 ┣ app/
 ┣ components/
 ┣ store/
 ┣ hooks/
 ┣ lib/
 ┗ pages/
```

---

## 7. 백엔드 설계

- **Controller**: REST API 엔드포인트 정의  
- **Service**: 비즈니스 로직 처리  
- **Repository**: JPA 기반 데이터 접근  
- **DTO**: 데이터 전송용 객체 (Request / Response 분리)  

### 📁 디렉터리 구조 예시

```
backend/
 ┣ src/main/java/com/flowlog/
 ┃ ┣ controller/
 ┃ ┣ service/
 ┃ ┣ repository/
 ┃ ┗ dto/
 ┗ src/main/resources/
```

---

## 8. 보안 설계

| 항목 | 내용 |
|------|------|
| **인증 방식** | JWT (Access Token + Refresh Token) |
| **암호화** | Spring Security + BCrypt |
| **통신 방식** | HTTPS (TLS 1.2 이상) |
| **CORS 설정** | Next.js Origin 허용 기반 설정 |

---

## 9. 로깅 및 예외 처리

- **로깅**: SLF4J + Logback  
- **에러 처리**: `@ControllerAdvice` + `GlobalExceptionHandler`  
- **모니터링**: Docker 컨테이너 로그 기반 (향후 Prometheus 연동 예정)

---

## 10. 향후 확장 계획

- 댓글 / 파일 첨부 기능 추가  
- 실시간 알림 및 WebSocket 기반 협업 기능  
- 외부 서비스 연동 (Slack, Google Calendar 등)

---

## 11. 참고 문서

- [Vercel 공식 문서](https://vercel.com/docs)  
- [Spring Boot 공식 문서](https://spring.io/projects/spring-boot)  
- [MySQL 8 Reference](https://dev.mysql.com/doc/)  
- [JUnit5 User Guide](https://junit.org/junit5/docs/current/user-guide/)

