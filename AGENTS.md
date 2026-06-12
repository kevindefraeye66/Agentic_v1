# AGENTS.md — Agentic Workflow for Agentic_v1

This file defines the **global rules** that apply to every agent in this
repository's agentic workflow. All agents must read and follow this file
in addition to their own agent file.

---

## Workflow Pipeline

Requirements flow through four sequential phases. Each agent owns exactly
one phase and produces a single hand-off artefact for the next.

```
┌─────────────────────┐
│  Phase 1            │  .github/agents/intake-and-plan.md
│  Intake & Plan      │  Input : ticket_id + requirement description
│                     │  Output: approved development plan
└────────┬────────────┘
         │ approve
         ▼
┌─────────────────────┐
│  Phase 2            │  .github/agents/develop.md
│  Develop            │  Input : approved plan
│                     │  Output: working, tested code + test report
└────────┬────────────┘
         │ approve
         ▼
┌─────────────────────┐
│  Phase 3            │  .github/agents/document.md
│  Document           │  Input : approved code diff + doc/pr-log/<ticket_id>.md
│                     │  Output: verified requirements + updated doc/SERVICE_DOC.md
└────────┬────────────┘
         │ approve
         ▼
┌─────────────────────┐
│  Phase 4            │  .github/agents/pull-request.md
│  Pull Request       │  Input : all artefacts from phases 1-3
│                     │  Output: a ready-to-review GitHub PR
└─────────────────────┘
```

---

## Global Rules (apply to every agent)

### 1 · Phase Awareness
Every agent must know and state its current phase. After any interaction
with the user (clarification, answer to a side-question, status update)
the agent **must** append a one-line phase reminder, for example:

> 📍 **Current phase: Intake & Plan (1/4)** — reply `approve` to move to
> Develop, or continue the discussion.

### 2 · Approval Gate
No agent may hand off to the next phase without an explicit `approve`
from the user (case-insensitive). This applies even if the output looks
complete.

### 3 · Handoff Block
When handing off, every agent must print a standardised block:

```
---
✅ <Phase name> approved for <ticket_id>.
Handing off to → Phase <N+1>: <Next phase name> (.github/agents/<file>.md)
Artefact: <one-line description of what is being handed over>
---
```

### 4 · Artefact Traceability
All artefacts (plans, code, docs, PR body) must reference the original
`ticket_id` so the full chain is traceable.

### 5 · Side-Quest Handling
If the user asks a question or raises a topic outside the current phase's
scope, the agent should:
1. Answer briefly and helpfully.
2. Anchor back to the current phase with the phase reminder (Rule 1).
3. Never abandon the current phase's pending steps.

### 6 · Tone & Format
- Use plain, direct language.
- Prefer tables and bullet lists over prose for structured information.
- Code references must always use full file paths relative to the repo root.

---

## Project Context

| Property | Value |
|----------|-------|
| Language | Java 21 |
| Framework | Spring Boot 3.3 |
| Build | Maven (`pom.xml`) |
| Persistence | Spring Data JPA + H2 (runtime) |
| Utilities | Lombok |
| Testing | JUnit 5, Spring Boot Test, MockMvc |
| Package | `org.example` |
| Source root | `src/main/java/org/example/` |
| Test root | `src/test/java/org/example/` |
| Resources | `src/main/resources/` |

### Key existing classes

| Class | Role |
|-------|------|
| `Main` | Spring Boot entry point |
| `Order` | JPA entity — fields: `id` (Long), `totalAmount` (Double) |
| `OrderRepository` | Spring Data JPA repository for `Order` |
| `OrderController` | REST controller — `GET /api/orders/{id}` |
| `OrderControllerTest` | MockMvc integration test for `OrderController` |

---

## Agent Registry

| Phase | File | Responsibility |
|-------|------|----------------|
| 1 | `.github/agents/intake-and-plan.md` | Understand requirement, plan, get approval |
| 2 | `.github/agents/develop.md` | Implement plan, write tests, get approval |
| 3 | `.github/agents/document.md` | Verify requirements, create/update doc/SERVICE_DOC.md |
| 4 | `.github/agents/pull-request.md` | Open GitHub PR, run GHAS + SonarQube, record PR URL in doc/pr-log |

