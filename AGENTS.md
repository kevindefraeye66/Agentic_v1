# Agent Workflow Orchestrator

## Overview
This is a minimal agent system that guides feature development through multiple phases with user approval at each stage.

## Phase Continuity Rules
- Maintain a `current_phase` until explicit user approval for handoff is given.
- If the user asks side questions/fixes (e.g., framework version, Lombok, test fix), handle them **within the current phase**.
- After every side-request response, restate:
  - current phase status
  - what was completed in this phase
  - exact approval phrase required to continue
- Do **not** jump phases implicitly because extra tasks were completed.

## Workflow Phases

### Phase 1: INTERPRET TICKET
**Agent**: `interpret-ticket.md`  
**Input**: User request or ticket description  
**Output**: Implementation plan listing all affected classes/files  
**Action Required**: User reviews and approves plan

**Handoff Condition**: ✅ User approves the plan

---

### Phase 2: DEVELOP
**Agent**: `develop.md`  
**Input**: Approved plan from Phase 1  
**Output**: 
- All implemented code files
- `implemented-changes.md` summary document  

**Action Required**: User reviews implemented changes and technical decisions

**Handoff Condition**: ✅ User approves the implementation

---

### Phase 3: TEST
**Agent**: `test.md`  
**Input**: 
- Plan from Phase 1
- `implemented-changes.md` from Phase 2  

**Output**: 
- Test classes for all implemented functionality
- `Test Implementation Report` showing coverage and any gaps

**Action Required**: User reviews test coverage and any identified gaps

**Handoff Condition**: ✅ User approves the tests

---

### Phase 4: CREATE PR
**Agent**: `create-pr.md`  
**Input**: All changes from Phases 2 and 3  
**Output**: 
- New branch: `myfeature`
- All changes committed
- PR description ready

**Action Required**: None - automated process, PR ready for submission

---

## User Approval Points
The workflow includes **3 approval checkpoints** where you (the user) must confirm before proceeding:

1. **After Phase 1**: "✅ Looks good, proceed to DEVELOP"
2. **After Phase 2**: "✅ Changes look good, proceed to TEST"
3. **After Phase 3**: "✅ Tests look good, proceed to CREATE PR"

## Example Workflow Execution

```
User: "Add a new CustomerService class that validates email addresses"
     ↓
[INTERPRET TICKET]
Agent: "I plan to create:
  - src/main/java/org/example/CustomerService.java (NEW)
  - src/main/java/org/example/EmailValidator.java (NEW)
  This will validate customer emails..."
     ↓
User: "✅ Approved"
     ↓
[DEVELOP]
Agent: "Created CustomerService and EmailValidator classes with proper
  validation logic. See implemented-changes.md for details"
     ↓
User: "✅ Approved"
     ↓
[TEST]
Agent: "Created CustomerServiceTest with 8 test cases covering:
  - Valid emails
  - Invalid emails
  - Edge cases
  All tests pass ✅"
     ↓
User: "✅ Approved"
     ↓
[CREATE PR]
Agent: "Branch 'myfeature' created with all changes committed.
  PR ready: 'Add email validation to customer service'"
     ↓
Done - Ready for code review!
```

## How to Use This System

1. **Describe Your Requirement**: Tell the agent what feature or change you want
2. **Phase 1 - Review Plan**: Check which files will be affected
3. **Phase 2 - Review Implementation**: Check the code and decisions
4. **Phase 3 - Review Tests**: Verify test coverage
5. **Phase 4 - Create PR**: Get a clean PR ready for submission

## Key Features
- ✅ Structured approach to feature development
- ✅ Clear handoff points between phases
- ✅ User approval at critical checkpoints
- ✅ Documentation of technical decisions
- ✅ Test coverage verification
- ✅ Clean git history with feature branches

