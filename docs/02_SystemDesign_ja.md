# 🧠 Flowlog - システム設計書  
[🇰🇷 한국어 버전 보기](./02_SystemDesign_ko.md)

---

## 1. 概要
本ドキュメントは **Flowlog** システムの全体構成、コンポーネント、技術スタック、および設計方針を定義します。  
初期バージョンは **認証(Authentication)**、**プロジェクト管理(Project Management)**、**タスク管理(Task Management)** を中心に構築されます。

---

## 2. システムアーキテクチャ概要

```
[Frontend] React (Next.js + TypeScript)
       ↓  REST API
[Backend] Spring Boot (Controller → Service → Repository)
       ↓
[Database] MySQL (Docker container)
```

- **アーキテクチャパターン**: Clean Architecture + RESTful API  
- **認証方式**: JWT ベーストークン  
- **デプロイ環境**: Docker Compose（開発） / Vercel + Render（本番）

---

## 3. 技術スタック

| 層 | 技術 | バージョン / 構成 |
|----|------|----------------|
| **フロントエンド** | React (Next.js 14), TypeScript, TailwindCSS, Zustand, dnd-kit, React Query | UI と状態管理 |
| **バックエンド** | Spring Boot 3, Java 17, Spring Security (JWT), JPA | API とビジネスロジック |
| **データベース** | MySQL 8 (Docker ベース) | 永続データ管理 |
| **テスト** | JUnit5, Mockito, React Testing Library | 単体 / 統合テスト |
| **CI/CD** | GitHub Actions | ビルドと自動デプロイ |

---

## 4. ER 図 (論理モデル)

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

## 5. API 設計 (例)

| HTTP | エンドポイント | 説明 |
|------|----------------|------|
| `POST` | `/api/auth/signup` | 新規ユーザー登録 |
| `POST` | `/api/auth/login` | ログイン（JWT発行） |
| `GET` | `/api/projects` | プロジェクト一覧取得 |
| `POST` | `/api/projects` | プロジェクト新規作成 |
| `GET` | `/api/tasks/{id}` | タスク詳細取得 |
| `PUT` | `/api/tasks/{id}` | タスク更新 |
| `DELETE` | `/api/tasks/{id}` | タスク削除 |

---

## 6. フロントエンド設計

- **UI フレームワーク**: TailwindCSS  
- **状態管理**: Zustand  
- **ドラッグ＆ドロップ**: dnd-kit  
- **データ取得**: React Query  
- **ルーティング**: Next.js App Router  

### 📁 ディレクトリ構成例

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

## 7. バックエンド設計

- **Controller**: REST API エンドポイント定義  
- **Service**: ビジネスロジック処理  
- **Repository**: JPA ベースのデータアクセス  
- **DTO**: データ転送オブジェクト（Request / Response 分離）  

### 📁 ディレクトリ構成例

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

## 8. セキュリティ設計

| 項目 | 内容 |
|------|------|
| **認証方式** | JWT (Access Token + Refresh Token) |
| **暗号化** | Spring Security + BCrypt |
| **通信** | HTTPS (TLS 1.2 以上) |
| **CORS 設定** | Next.js の Origin 許可設定 |

---

## 9. ログ・例外処理

- **ログ管理**: SLF4J + Logback  
- **エラーハンドリング**: `@ControllerAdvice` + `GlobalExceptionHandler`  
- **モニタリング**: Docker コンテナログ（将来的に Prometheus 連携予定）

---

## 10. 今後の拡張計画

- コメント / ファイル添付機能の追加  
- リアルタイム通知および WebSocket ベースのコラボレーション機能  
- 外部サービス連携（Slack, Google Calendar など）

---

## 11. 参考資料

- [Vercel 公式ドキュメント](https://vercel.com/docs)  
- [Spring Boot 公式ドキュメント](https://spring.io/projects/spring-boot)  
- [MySQL 8 リファレンス](https://dev.mysql.com/doc/)  
- [JUnit5 ユーザーガイド](https://junit.org/junit5/docs/current/user-guide/)

