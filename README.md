# 📚 EPS Week 4 - Java Styles: 9, 10, 15

This project explores different programming styles (from *Exercises in Programming Style*) implemented in Java. Each file represents a different style of structuring logic, reading input, and producing output.

---

## 🧩 `Nine.java`

This was my starting point — I wrote it to read input from a file (`input.txt`) instead of typing values manually.

### 🔍 What it does:
- Reads input using Java I/O classes like `File`, `Scanner`, and `BufferedReader`
- Processes numbers or strings by:
  - Reading line by line
  - Splitting values by space or comma
  - Performing calculations (sum, count, etc.)
- Handles exceptions and closes resources properly
- Prints output to console for easy debugging

### ✅ What I learned:
- How to open and read files safely in Java
- How to parse and manipulate string input
- Basic error handling

---

## 🔢 `Ten.java`

This file focuses on **conditional logic and loops**.

### 🔍 What it does:
- Uses nested `if-else`, `switch`, and loops (`for`, `while`)
- Reads data from `input.txt`
- Applies rules to filter, calculate, or transform the data
- Demonstrates input validation, type casting (e.g., `String → int`)

### ✅ What I learned:
- How to use conditionals and loops together
- How to simulate real-world logic flows
- Converting and validating input

---

## 🔁 `Fifteen.java`

This one was more advanced — it implements a **callback-style control flow** (EPS Style #15).

### 🔍 What it does:
- Breaks logic into **modular components** using interfaces and classes
- Uses an "event-driven" pattern (Hollywood style: "Don’t call us, we’ll call you")
- Loads data → Processes → Outputs frequency
- Uses interfaces like:
  - `LoadHandler`
  - `WorkHandler`
  - `EndHandler`
  - `WordHandler`

### ✅ What I learned:
- How to organize Java code using interfaces and callbacks
- How to avoid putting everything in `main()`
- Error handling with `try-catch`
- Thinking in small, testable building blocks

---

## 📂 `input.txt`

This file holds **test data** for all Java programs.

### 🔍 Why I used it:
- Avoids manual input every time I run a program
- Makes testing faster and repeatable
- Ensures consistency across test runs

---

## ▶️ How to Run

```bash
javac Nine.java
javac Ten.java
javac Fifteen.java
