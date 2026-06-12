# Requirement Log — ART02DEV10-ATT2

## Ticket
- **ID:** ART02DEV10-ATT2
- **Date:** 2026-06-12

## Original Description
> Add an extra field 'currency' to Order

## Codebase Findings
| Symbol / File | Observation |
|---------------|-------------|
| `src/main/java/org/example/Order.java` | JPA entity with `id` (Long) and `totalAmount` (Double). No `currency` field exists yet. Has a 2-arg constructor used in tests. |
| `src/main/java/org/example/OrderController.java` | `GET /api/orders/{id}` returns `Order` directly — new field will appear in JSON automatically. |
| `src/main/java/org/example/OrderRepository.java` | Standard Spring Data JPA repository — no changes needed. |
| `src/main/resources/data.sql` | Seeds 2 rows using only `(total_amount)`. Must be extended to supply a `currency` value per row. |
| `src/main/resources/application.properties` | H2 in-memory DB with `create-drop` DDL — no Flyway/Liquibase migration needed. |
| `src/test/java/org/example/OrderControllerTest.java` | MockMvc test asserts `$.id` and `$.totalAmount`. Must also assert `$.currency`. |

## Clarifications
| # | Question | Answer |
|---|----------|--------|
| 1 | What type/format should `currency` be? | User confirmed: Java **enum** with values `EUR` and `USD` (3-char ISO-4217 codes). |
| 2 | Should the controller return the entity directly? | User confirmed: **No** — introduce an `OrderDto` so the entity is never exposed directly. |

## Final Understanding
Add a `Currency` enum (values: `USD`, `EUR`) to the project. Add a non-null `Currency currency` field to the `Order` JPA entity, stored as a string via `@Enumerated(EnumType.STRING)`. Update the all-arg constructor to accept `Currency`. Create an `OrderDto` (Java record) mirroring the public fields (`id`, `totalAmount`, `currency`). Update `OrderController` to map `Order` → `OrderDto` before returning. Update `data.sql` seed inserts to include a `currency` string value. Update `OrderControllerTest` to assert `$.currency`. No new endpoints; no Flyway/Liquibase needed (H2 `create-drop`).

## Acceptance Criteria
- [ ] A `Currency` enum exists with at least `USD` and `EUR` values.
- [ ] `Order` entity has a `Currency currency` field annotated with `@Enumerated(EnumType.STRING)` and `@Column(nullable = false)`.
- [ ] The `Order(Long id, Double totalAmount, Currency currency)` constructor exists.
- [ ] An `OrderDto` type exists with fields `id`, `totalAmount`, `currency`.
- [ ] `GET /api/orders/{id}` returns an `OrderDto` (entity is not serialised directly).
- [ ] `data.sql` seeds rows with a valid `currency` string value (`'USD'`).
- [ ] `OrderControllerTest.testGetOrder()` asserts `$.currency` equals `"USD"` and passes.
- [ ] All existing tests continue to pass.

## Approved Plan Summary
Add a `Currency` enum (`USD`, `EUR`). Extend `Order` with `@Enumerated(EnumType.STRING) Currency currency`. Introduce `OrderDto` (Java record). Update `OrderController` to map `Order` → `OrderDto`. Update `data.sql` seed rows with `'USD'`. Extend `OrderControllerTest` to assert `$.currency`.

## Develop Phase Clarifications
| # | Question | Answer | Impact |
|---|----------|--------|--------|
| 1 | Should `OrderDto` be a Java record as planned? | Maven's runtime JVM is Java 1.8 (JAVA_HOME not set to the installed Zulu 21); records require Java 16+. Replaced with a Lombok `@Getter @AllArgsConstructor` class — identical API surface, same JSON output. | `OrderDto.java` uses Lombok instead of a record declaration. No functional change. |


