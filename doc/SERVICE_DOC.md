# Service Documentation

## Overview
This service exposes a simple Orders API backed by an in-memory H2 database. It currently supports fetching a single order by ID and returns persisted order data from the `orders` table.

## Architecture & Components

### Key Entities
| Entity | File | Purpose | Key Fields |
|---|---|---|---|
| `Order` | `src/main/java/org/example/Order.java` | Represents an order record persisted with JPA. | `id` (`Long`), `totalAmount` (`Double`) |

### Key Classes & Their Roles
| Class | File | Responsibility |
|---|---|---|
| `Main` | `src/main/java/org/example/Main.java` | Spring Boot application entry point. |
| `Order` | `src/main/java/org/example/Order.java` | JPA entity mapped to `orders`. |
| `OrderRepository` | `src/main/java/org/example/OrderRepository.java` | Data-access abstraction for `Order` via Spring Data JPA. |
| `OrderController` | `src/main/java/org/example/OrderController.java` | REST endpoint for retrieving orders by ID. |

### Dependencies
- `OrderController` depends on `OrderRepository` to query persisted orders.
- `OrderRepository` depends on Spring Data JPA and Hibernate for persistence.
- Persistence is configured in `src/main/resources/application.properties` using H2 (`jdbc:h2:mem:testdb`).

## Input / API Entry Points

### REST Endpoints
| Method | Path | Response | Purpose |
|---|---|---|---|
| `GET` | `/api/orders/{id}` | `200` with `Order` JSON when found; `200` with empty body (`null`) when not found | Retrieve order details by ID. |

### Data Ingestion
- Bootstrap SQL data is loaded from `src/main/resources/data.sql` at startup.
- JPA schema is recreated on startup/shutdown (`spring.jpa.hibernate.ddl-auto=create-drop`).

## Main Flow

### Happy Path
1. Client calls `GET /api/orders/{id}`.
2. `OrderController` receives `id` and calls `OrderRepository.findById(id)`.
3. If an order exists, the entity is returned as JSON.

### Error Handling
- If no matching order exists, the controller returns `null` (serialized as an empty response body).
- There is currently no dedicated global exception mapping layer.

## Complex Business Logic
There is no complex domain logic yet. The current implementation is a thin controller-to-repository read flow.

## Database Schema

### Tables
- `orders`
  - Expected by JPA entity mapping: `id`, `total_amount`
  - Seed script currently inserts `currency` as well; if the entity remains unchanged, align `data.sql` with the mapped columns to avoid schema/seed drift.

## New Developer Quick Start

### To Add a New Feature
1. Add or update domain model in `src/main/java/org/example/`.
2. Add repository queries in `src/main/java/org/example/OrderRepository.java`.
3. Expose API changes in `src/main/java/org/example/OrderController.java` (or new controller/service classes).
4. Add tests under `src/test/java/org/example/`.

### To Run Tests
```powershell
mvn test
```

### To Start the Application
```powershell
mvn spring-boot:run
```

## Future Improvements / Tech Debt
- Introduce a service layer between controller and repository as complexity grows.
- Use constructor injection in `OrderController` instead of field injection.
- Return proper HTTP status codes for missing resources (e.g., `404`).
- Add request/response DTOs instead of exposing entity types directly.
- Align `src/main/resources/data.sql` columns with `src/main/java/org/example/Order.java`.

