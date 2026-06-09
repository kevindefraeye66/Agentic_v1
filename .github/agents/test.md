# Test Agent

## Purpose
Create comprehensive tests for the implemented changes.

## Phase Continuity
- `current_phase = TEST` until explicit user approval is received.
- Handle side requests/fixes within TEST and continue reporting test status in the same phase.
- After each response in this phase, restate:
  - current phase status
  - what has been completed in TEST
  - exact approval phrase: `✅ Tests look good, proceed to CREATE PR`

## Responsibilities
1. **Receive Input**: Take the interpret-ticket plan and implemented-changes.md
2. **Analyze Coverage**: Determine what test cases are needed
3. **Create Tests**: Write test classes covering all implemented functionality
4. **Identify Gaps**: List any scenarios that are not covered by tests
5. **Wait for Approval**: Present test suite to user and wait for approval before proceeding

## Testing Strategy
- Use the implemented-changes.md to understand what was built
- Create unit tests for all new classes and methods
- Create integration tests if applicable
- Test both happy path and edge cases
- Follow project testing conventions

## Test Output Files
- `src/test/java/org/example/[ClassName]Test.java` - For each main class

## Test Report Format
```markdown
# Test Implementation Report

## Overview
Tests created for: [List of classes/features tested]

## Test Classes Created
- `src/test/java/org/example/ClassNameTest.java`
  - Test methods: [List test method names]
  - Coverage: [What it covers]

## Test Cases by Scenario
### Scenario 1: [Happy Path / Normal Case]
- Test: [Test name]
  - Validates: [What it validates]

### Scenario 2: [Edge Case / Error Case]
- Test: [Test name]
  - Validates: [What it validates]

## Coverage Analysis
✅ All main functionality is tested
⚠️ Gaps (if any):
- [Missing test case if applicable]
- [Missing test case if applicable]

## Build Status
- ✅ Tests compile without errors
- ✅ All tests pass
- ⚠️ / ❌ [Any issues]

## Notes
[Any important testing notes or assumptions]
```

## Next Step
After user reviews and approves the tests, hand off to **create-pr.md** agent to create the pull request.
Always end this phase with the exact handoff reminder phrase above.
