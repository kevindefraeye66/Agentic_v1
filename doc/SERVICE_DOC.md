# Service Documentation

## Overview
This service exposes a simple Orders API backed by an in-memory H2 database. It supports fetching a single order by ID and returns a structured `OrderDto` response — keeping the JPA entity internal. Orders carry a `totalAmount` and a `currency` (ISO-4217 enum: `USD`, `EUR`).

## Architecture & Components

### Key Entities
| Entity | File | Purpose | Key Fields |
|---|---|---|---|
| `Order` | `src/main/java/org/example/Order.java` | JPA entity mapped to the `orders` table. | `id` (`Long`), `totalAmount` (`Double`), `currency` (`Currency`) |

### Key Classes & Their Roles
| Class | File | Responsibility |
|---|---|---|
| `Main` | `src/main/java/org/example/Main.java` | Spring Boot application entry point. |
| `Currency` | `src/main/java/org/example/Currency.java` | Enum of supported currency codes (`USD`, `EUR`). |
| `Order` | `src/main/java/org/example/Order.java` | JPA entity mapped to `orders`. |
| `OrderDto` | `src/main/java/org/example/OrderDto.java` | Immutable response DTO — decouples the API surface from the JPA entity. Fields: `id`, `totalAmount`, `currency`. |
| `OrderRepository` | `src/main/java/org/example/OrderRepository.java` | Data-access abstraction for `Order` via Spring Data JPA. |
| `OrderController` | `src/main/java/org/example/OrderController.java` | REST endpoint for retrieving orders by ID; maps `Order` → `OrderDto`. |

### Dependencies
- `OrderController` depends on `OrderRepository` (constructor-injected) to query persisted orders.
- `OrderController` maps `Order` entities to `OrderDto` before returning to the client — the entity is never serialised directly.
- `OrderRepository` depends on Spring Data JPA and Hibernate for persistence.
- Persistence is configured in `src/main/resources/application.properties` using H2 (`jdbc:h2:mem:testdb`).

## Input / API Entry Points

### REST Endpoints
| Method | Path | Response | Purpose |
|---|---|---|---|
| `GET` | `/api/orders/{id}` | `200 OK` with `OrderDto` JSON when found; `404 Not Found` when not found | Retrieve order details by ID. |

**Example `200` response:**
```json
{
  "id": 1,
  "totalAmount": 99.99,
  "currency": "USD"
}
```

### Data Ingestion
- Bootstrap SQL data is loaded from `src/main/resources/data.sql` at startup.
- JPA schema is recreated on startup/shutdown (`spring.jpa.hibernate.ddl-auto=create-drop`).

## Main Flow

### Happy Path
1. Client calls `GET /api/orders/{id}`.
2. `OrderController` receives `id` and calls `OrderRepository.findById(id)`.
3. If an order exists, `OrderController` maps it to an `OrderDto` and returns it as JSON with `200 OK`.

### Error Handling
- If no matching order exists, `OrderController` throws `ResponseStatusException(HttpStatus.NOT_FOUND)`, resulting in a `404 Not Found` response.
- There is no global `@ControllerAdvice` handler yet; Spring Boot's default error mechanism handles the response body.

## Complex Business Logic
There is no complex domain logic yet. The current implementation is a thin controller-to-repository read flow with entity-to-DTO mapping at the controller layer.

## Database Schema

### Tables
- `orders`
  - `id` — auto-generated primary key (`BIGINT`)
  - `total_amount` — order value (`DOUBLE`)
  - `currency` — ISO-4217 currency code stored as `VARCHAR` (`USD`, `EUR`)

## New Developer Quick Start

### To Add a New Feature
1. Add or update domain model in `src/main/java/org/example/` (entity + enum if needed).
2. Add repository queries in `src/main/java/org/example/OrderRepository.java`.
3. Create or update a DTO in `src/main/java/org/example/` (e.g., `OrderDto`).
4. Expose API changes in `src/main/java/org/example/OrderController.java` (or new controller/service classes), mapping entity → DTO.
5. Add tests under `src/test/java/org/example/`.

### To Run Tests
```powershell
mvn test
```

### To Start the Application
```powershell
mvn spring-boot:run
```

## Future Improvements / Tech Debt
- Introduce a dedicated service layer between controller and repository as business logic grows.
- Add a global `@ControllerAdvice` exception handler for consistent error response bodies.
- Expand the `Currency` enum as additional currencies are supported.


