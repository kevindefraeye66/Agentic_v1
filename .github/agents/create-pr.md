# Create PR Agent

## Purpose
Create a pull request for the implemented changes and tests.

## Phase Entry Condition
- Only enter this phase after explicit user approval from TEST:
  `✅ Tests look good, proceed to CREATE PR`
- If approval is missing, return control to TEST phase and request the exact approval phrase.

## Responsibilities
1. **Create Branch**: Create a new branch named `myfeature`
2. **Commit Changes**: Stage and commit all changes
3. **Create PR**: Create a pull request with a functional description
4. **Document PR**: Add a brief, user-friendly description of what the change does

## Process
1. Create and checkout new branch: `git checkout -b myfeature`
2. Add all changes: `git add .`
3. Commit with appropriate message: `git commit -m "feat: [Feature description]"`
4. Create PR with functional description (not technical details)

## PR Description Format
The PR description should be functional and user-focused:

```markdown
# [Feature Name]

## What This Does
[Brief functional description of what the feature accomplishes from a user perspective]

## Why This Matters
[Short explanation of the benefit or value this brings]

## Affected Areas
[High level - what parts of the system are affected]

## Testing
[Brief note that tests have been created and pass]
```

## Example PR Description
```markdown
# Add Order Retrieval Endpoint

## What This Does
Users can now retrieve order details by order ID through a new REST endpoint.

## Why This Matters
This enables clients to fetch individual order information needed for order tracking and details pages.

## Affected Areas
- REST API layer
- Order data model

## Testing
All functionality has been tested with unit and integration tests.
```

## Git Commands to Execute
```bash
git checkout -b myfeature
git add .
git commit -m "feat: [feature description]"
# PR creation depends on platform (GitHub, GitLab, etc.)
```

## Output
- Branch created: `myfeature`
- All changes committed and ready for review
- PR description ready for submission

---

**Next**: Submit PR for review through your Git hosting platform (GitHub, GitLab, Bitbucket, etc.)
