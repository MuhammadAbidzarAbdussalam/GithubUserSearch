# GitHub User Search

A Kotlin Android native app demonstrating Clean Architecture (multi-module), MVVM, Coroutines, Retrofit/OkHttp, Moshi, Room caching, and Hilt DI.

## 1) Build & Run

Prereqs:
- Android Studio Ladybug or newer
- Android SDK 24+
- JDK 17 (Android Studio manages toolchains)

Steps:
1. Clone the repo and open in Android Studio.
2. Sync Gradle when prompted.
3. Build variants: select `debug` or `release`.
4. Run on a device/emulator (minSdk 24).

Modules:
- `:app` (presentation)
- `:domain` (use cases, models, repository contracts)
- `:data` (Retrofit, Room, repository impl, DI)

Debug tools:
- Chucker and OkHttp logging enabled in `debug` build type.

## 2) Features, Improvements, and Challenges

Implemented (Core MUSTs):
- GitHub user search using Retrofit + OkHttp.
- Results in RecyclerView with Glide avatars.
- Detail screen with username, avatar, bio, followers/following, and extended profile fields.
- Room caching:
  - Search pages cached by `query+page`.
  - User detail cached by `username`.
  - TTL-based invalidation (24h).
- Clean Architecture (domain, data, presentation), MVVM with ViewModel + LiveData, Coroutines, Hilt DI.
- Version Catalog for dependencies.
- Proguard/R8 enabled for release.

Nice-to-haves:
- Chucker integration (debug only).
- WorkManager periodic cache cleanup (12h).
- Unit tests (use cases, mappers) and instrumented tests (Room, basic Espresso flow).

Improvements made:
- Centralized mapping between DTO/Entity/Domain.
- Debounced search to reduce network calls.

Challenges & notes:
- Chucker not showing at Android 13+ due to platform restrictions; OkHttp logging used as fallback.

## 3) Architecture & Rationale

Pattern: **Clean Architecture + MVVM**

- View (in `:app`):
  - Activities/UI: `MainActivity`, `DetailActivity`
  - Layouts: `activity_main.xml`, `activity_detail.xml`, `item_user_summary.xml`
  - Adapter: `UserAdapter`
  - Role: Render UI, observe LiveData, forward events to ViewModel.

- ViewModel (in `:app`):
  - `SearchViewModel`, `DetailViewModel`
  - Role: Orchestrate use cases, manage UI state (`LiveData` for loading/error/data), use coroutines with debounce.

- Model (in `:domain` and `:data`):
  - Domain models: `UserSummary`, `UserDetail`
  - Domain logic: use cases `SearchUsersUseCase`, `GetUserDetailUseCase`; repository interface `GitHubRepository`
  - Data sources (in `:data`): Retrofit API/DTOs, Room entities/DAOs/DB, mappers, repository implementation `GitHubRepositoryImpl`

Why this approach:
- Separation of concerns and clear boundaries improve maintainability.
- Dependency inversion (domain depends on abstractions) improves testability.
- MVVM with LiveData and coroutines fits Android lifecycle and async data loading.
- Multi-module setup scales for team development and enforces layer isolation.

## API
Base URL: `https://api.github.com/`
- Search users: `GET /search/users?q={query}&page={n}`
- User detail: `GET /users/{username}`

## Caching Strategy
- Search: cache by `(query, page)` with timestamp; serve if fresh (<24h).
- Detail: cache by `username` with timestamp; serve if fresh (<24h).
- Manual refresh: type or edit query to re-trigger.

## Testing
- Unit: mappers, use cases.
- Instrumented: Room in-memory DAOs; simple Espresso flow (search → open detail).

## Debugging
- Chucker (debug) and OkHttp logging (debug) wired via Hilt networking module.

## Build System
- Kotlin DSL Gradle
- Version Catalog (`gradle/libs.versions.toml`)
- Build types: debug/release with R8/Proguard in release

## Submission
- Keep commits atomic and descriptive (Conventional Commits recommended).
- Include screenshots if desired.