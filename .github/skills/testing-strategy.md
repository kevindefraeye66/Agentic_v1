# Skill: Testing Strategy

## Purpose
Provide a repeatable method for creating high-value automated tests for implemented changes.

## When to Use
Use this skill whenever functionality is added, modified, or fixed and tests must be created or updated.

## Inputs
- approved implementation plan
- implemented changes summary
- changed source files
- current test suite
- framework context (e.g. Spring Boot, JPA, MockMvc)

## Outputs
- test classes to add or modify
- scenario coverage matrix
- explicit coverage gap report
- rationale for chosen test levels

## Procedure

### 1. Identify behavior under test
For each changed class or endpoint, list:
- public behaviors
- inputs
- outputs
- side effects
- error conditions

### 2. Build a scenario matrix
Include:
- happy path
- invalid input
- missing data / not found
- boundary values
- persistence behavior
- framework/API contract behavior
- regression scenario for the changed behavior

### 3. Choose the lowest effective test level
Prefer:
1. unit tests for pure logic
2. slice tests for framework boundaries
3. full integration tests only when wiring across layers must be verified

### 4. Write assertions against outcomes
Assert:
- returned values
- status codes
- JSON fields
- persisted state
- thrown exceptions or mapped errors

Avoid asserting:
- internal private implementation details
- framework internals
- incidental formatting unless part of the contract

### 5. Report gaps explicitly
If something is not tested, state:
- what is missing
- risk level
- reason

## Decision Rules
- Prefer focused tests over broad end-to-end tests
- Do not rely on a single happy-path integration test
- Add at least one negative or edge case per meaningful behavior
- For controllers, verify both status and payload
- For repositories, verify query/persistence behavior with realistic test data

## Spring Boot Guidance
- Use `@WebMvcTest` for controller-only HTTP behavior
- Use `@DataJpaTest` for repository behavior
- Use `@SpringBootTest` only when multi-layer integration is required

## Output Contract
Provide:
- test classes created
- scenarios covered
- gaps
- assumptions
- pass/fail/build status

## Example
Feature: GET `/api/orders/{id}`

Scenarios:
- existing order returns 200 and expected JSON
- missing order returns 404
- invalid id format returns 400
- response contains required fields only

Recommended test approach:
- `@WebMvcTest` for controller contract
- repository/data setup only if controller behavior depends on actual persistence wiring

## Anti-Example
Bad approach:
- one `@SpringBootTest` test that only checks 200 for `/api/orders/1`
  Reason:
- does not cover missing order
- does not validate error mapping
- is broader and slower than necessary
