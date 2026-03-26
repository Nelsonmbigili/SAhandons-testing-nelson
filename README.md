# In-Class Exercise: Software Testing

## Technical Requirements

Before starting this exercise, ensure you have the following:

### Software

- **Java 11+ & Maven 3.6+** — [Download Java](https://adoptium.net/) | [Download Maven](https://maven.apache.org/download.cgi)
- **GitHub Account** — [Sign up for GitHub](https://github.com/signup)

### Skills

- Basic understanding of unit testing (e.g., JUnit)
- Familiarity with running commands in a terminal
- Basic Java programming

### Setup Verification

```bash
java --version      # Should show Java 11 or higher
mvn --version       # Should show Maven 3.6 or higher
```

---

## Overview

A test suite's quality cannot be measured by coverage alone. A test that executes a line of code is not the same as a test that _verifies_ the code behaves correctly. Two complementary techniques help measure test quality more rigorously: **test coverage** and **mutation testing**.

- **Test Coverage** measures which lines and branches of source code are executed when tests run.
- **Mutation Testing** artificially introduces small faults (_mutants_) into the source code and checks whether the existing tests detect them. A mutant that is caught by any test is _killed_; one that is not caught _survives_ (or is _alive_).

**This exercise focuses on running and interpreting both techniques on the bank-demo repository.** You will learn to:

1. Run test coverage tools and interpret their reports
2. Run mutation testing tools and identify surviving mutants
3. Explain the gap between coverage percentage and mutation score
4. Write new tests to increase coverage and improve the mutation score
5. Compare two suites with the same coverage but different assertion quality (and thus different mutation scores)

---

## GenAI Usage Policy

The use of Generative AI tools (e.g., ChatGPT, Cursor, GitHub Copilot, Claude) is permitted for this exercise with the following guidelines:

### Allowed Uses

- Understanding tool documentation (PIT, JaCoCo)
- Debugging error messages or installation issues
- Clarifying concepts about mutation testing or coverage metrics
- Understanding what a specific mutant operator does

### Not Allowed

- Having AI interpret your mutation results for you (you must analyze surviving mutants yourself)
- Using AI to write the new tests required in Part 2
- Having AI answer the reflection questions

### Requirements

- You must be able to explain any code you submit
- Document any AI assistance in your submission (brief note at the end of your PDF)

---

# Bank Demo — Project Structure

```
bank-demo/
├── pom.xml                                    # Maven config
├── src/
│   ├── main/java/com/softwareanalytics/bank/
│   │   └── BankAccount.java                   # System under test
│   └── test/java/com/softwareanalytics/bank/
│       └── BankAccountTest.java               # Test suite
└── README.md
```

### System Under Test: `BankAccount`

A simple bank account class with:

| Method                                             | Description                                                        |
| -------------------------------------------------- | ------------------------------------------------------------------ |
| `BankAccount(String owner, double initialBalance)` | Constructor; rejects null/blank owner or negative balance          |
| `deposit(double amount)`                           | Adds funds; rejects frozen account or amount ≤ 0                   |
| `withdraw(double amount)`                          | Removes funds; rejects frozen, amount ≤ 0, or insufficient balance |
| `transfer(BankAccount target, double amount)`      | Moves funds between accounts                                       |
| `freeze()` / `unfreeze()`                          | Toggle frozen state                                                |
| `applyMonthlyInterest(double annualRatePercent)`   | Apply monthly interest when balance > 0                            |
| `getBalance()`, `getOwner()`, `isFrozen()`         | Getters                                                            |

---

## Repository Setup

**Create your own repository from this template:**

1. Click **"Use this template"** (green button at the top of the repo)
2. Select **"Create a new repository"**
3. Name it appropriately (e.g., `SAhandons-testing-yourname`)
4. **Include all branches:** When creating the repo, check **"Include all branches"** so the `reference-suite` branch (needed for Part 3) is copied to your new repository.

**Clone your new repository and confirm the test suite runs:**

```bash
git clone <your-repo-url>
cd bank-demo

mvn test
```

> **Note:** Do NOT fork or clone the template directly. Use the "Use this template" button to create your own copy. When creating your repo from the template, be sure to select **"Include all branches"** so you have access to all branches (including `reference-suite`) after you clone.

---

## Exercise Instructions

**Total Time: 95 minutes**

---

## Part 1: Test Coverage and Mutation Analysis (35 minutes)

**Goal:** Set up JaCoCo and PIT, run coverage and mutation reports, and interpret the results.

### Task 1.1: Setup JaCoCo and Run the Coverage Report (5 minutes)

Research how to configure JaCoCo for a Maven project and set it up for this repository. Your setup should:

- Generate an HTML coverage report when you run `mvn test`
- Produce a report you can open in a browser (e.g., `target/site/jacoco/index.html`)

Use the [JaCoCo documentation](https://www.jacoco.org/jacoco/trunk/doc/) and other resources to complete the setup.

### Task 1.2: Interpret the Coverage Report (5 min)

**Coverage:** Record the following in your report:

1. What is the overall **line coverage** percentage for `BankAccount`?
2. What is the overall **branch coverage** percentage?
3. Which methods in `BankAccount` have **low coverage** (below 60%)? Why do you think they are undertested?
4. Which methods have **high coverage** (above 90%)? Does high coverage guarantee the tests are effective? Explain your reasoning.
5. Include a **screenshot** of your coverage report showing the overall percentages and the `BankAccount` class.

### Task 1.3 Run Mutation Testing and Identify a Surviving Mutant (15 minutes)

**Mutation testing:** Set up PIT and run mutation testing. Research how to configure PIT for a Maven project. Your setup should target `com.softwareanalytics.bank.*`, generate an HTML report, and work with JUnit 5. Use the [PIT documentation](https://pitest.org/) and [PIT Maven Plugin Quickstart](https://pitest.org/quickstart/maven/).

Record in your report:

1. What is the **mutation score** (percentage of mutants killed)? How many mutants **generated** and **survived**?
2. Include a **screenshot** of your mutation testing report.

**Identify one surviving mutant:** Using the PIT report, find **one surviving mutant** and document:

3. The **mutant information** — file, line, original vs. mutated code
4. A plain-English explanation of why no existing test caught this mutant — what scenario or input would expose the fault?
5. Was the mutated line covered by the tests (check JaCoCo)? If so, what does this tell you about coverage vs. mutation testing?

---

## Part 2: Increase Coverage (40 minutes)

**Goal:** Create a branch, write new tests to increase coverage, and rerun mutation testing to improve the mutation score.

### Task 2.1: Create the `full-coverage` Branch (5 minutes)

Create a new Git branch named `full-coverage` and switch to it:

All new tests in this part should be committed to this branch.

### Task 2.2: Write Tests to Increase Coverage (25 minutes)

Write additional test cases in `BankAccountTest.java` to increase the coverage of `BankAccount`. Focus on:

- Uncovered methods (e.g., `freeze`, `unfreeze`, `transfer`, `applyMonthlyInterest`)
- Uncovered branches (constructor guards, deposit/withdraw validation, frozen-account behavior)

Aim to significantly improve both **line coverage** and **branch coverage** for `BankAccount`.

### Task 2.3: Rerun Coverage and Mutation Testing (10 minutes)

Rerun both tools and record the new results:

Record in your report:

1. The new **line coverage** and **branch coverage** percentages
2. The new **mutation score** and mutant counts (generated, killed, survived)
3. Include **screenshots** of both the updated JaCoCo report and the PIT report
4. Briefly explain: How did your new tests affect coverage? How did they affect the mutation score?

---

## Part 3: Compare Test Suite Effectiveness (20 minutes)

**Goal:** Switch to the `reference-suite` branch, measure its mutation score, and compare the effectiveness of two suites that both achieve ~100% coverage.

### Task 3.1: Run Mutation Testing on the `reference-suite` Branch (10 minutes)

The repository has a branch called `reference-suite` with an alternative test suite that also achieves ~100% line and branch coverage of `BankAccount`. You will compare its results with your `full-coverage` branch.

Check out the `reference-suite` branch and run mutation testing.

Record in your report:

1. The **mutation score** on the `reference-suite` branch
2. How many mutants **generated**, **killed**, and **survived**?
3. Include a **screenshot** of the PIT report for the `reference-suite` branch

### Task 3.2: Compare the Two Suites (10 minutes)

Create a comparison table or summary:

| Metric           | Your `full-coverage` branch | `reference-suite` branch |
| ---------------- | --------------------------- | ------------------------ |
| Line coverage    |                             |                          |
| Branch coverage  |                             |                          |
| Mutation score   |                             |                          |
| Mutants killed   |                             |                          |
| Mutants survived |                             |                          |

Answer the following in your report:

1. Both suites achieve ~100% coverage. Why do they have different mutation scores?
2. What does this comparison tell you about the relationship between coverage and test effectiveness?
3. In your own words: is mutation score a better indicator of test quality than coverage alone? Do you think common mutation techniques (e.g., switching operators etc) are effective at simulating real faults to determine test effectiveness?

---

## Submission Instructions

Submit the following to **Brightspace**:

### Required Submissions

**Total Points: 40 points**

1. **Part 1 — Test Coverage and Mutation Analysis (10 points)**
   - Overall line and branch coverage percentages for `BankAccount`
   - Identified low-coverage and high-coverage methods with explanations
   - Mutation score, mutant counts, and one surviving mutant analysis
   - Screenshots of coverage and mutation reports

2. **Part 2 — Increase Coverage (10 points)**
   - Evidence of the `full-coverage` branch and new tests
   - New line/branch coverage and mutation score with screenshots
   - Explanation of how your tests affected coverage and mutation score

3. **Part 3 — Compare Test Suite Effectiveness (10 points)**
   - Mutation score and metrics for the `reference-suite` branch with screenshot
   - Comparison table (full-coverage vs. reference-suite)
   - Answers to the reflection questions on coverage vs. mutation score

4. **GenAI Disclosure (if applicable)**
   - Brief note describing any AI tools used and how they assisted you

5. **Repository Link (10 points)**
   - Submit a link to your final GitHub repository
   - The repo must include the `full-coverage` branch with your updated test suite
   - Store the coverage and mutation reports: commit `target/site/jacoco/` and `target/pit-reports/` (or the relevant output directories) so the different reports from your runs are preserved in the repo

### Submission Format

- Combine all findings, screenshots, and written answers into a single PDF
- Name your file: `LastName_FirstName_SA_Handson_Testing.pdf`
- Include your repository link in the PDF

---

## Resources and References

- **JaCoCo Documentation**: https://www.jacoco.org/jacoco/trunk/doc/
- **PIT Mutation Testing**: https://pitest.org/
- **PIT Maven Plugin Quickstart**: https://pitest.org/quickstart/maven/
- **JUnit 5 User Guide**: https://junit.org/junit5/docs/current/user-guide/

---

## Acknowledgement

This exercise was developed with the assistance of [Claude](https://claude.ai) (an AI assistant by Anthropic) and [Cursor](https://cursor.com/), an AI-powered code editor. Claude and Cursor were used to:

- Brainstorm ideas for the exercise structure and tasks
- Draft and refine this README documentation

## License

MIT License — See [LICENSE](LICENSE) file for details.
