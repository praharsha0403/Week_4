## Nine.java

This was my starting point — I wrote it to read input from a file (`input.txt`) instead of typing values manually. I did this to understand how Java handles file I/O using classes like `File`, `Scanner`, or `BufferedReader`. Once the data is read, the program processes numbers or strings.

it does things like:

- reading line by line  
- splitting values using spaces or commas  
- performing calculations or checks (e.g., sum, count, pattern match)

I printed the final output on the console to verify correctness.Basically, I learned how to open a file, read data safely, handle exceptions, and close resources properly.


## Ten.java

This file focuses on conditional logic and looping. I wrote this to test how nested conditions (`if-else`, `switch`) and iterative loops (`for`, `while`) work together. It reads data from `input.txt` , processes it based on certain rules, and prints meaningful results.

I did this to simulate real-world decision-making logic, like checking ranges, comparing values, or filtering data.

this also helped me understand type conversion (`string → int`) and handling invalid data gracefully.

in short, `Ten.java` was me experimenting with logic control, turning raw input into structured results using loops and decisions.



## Fifteen.java

This was a little more advanced. I implemented a combination of recursion and function calls. The goal was to organize code into multiple functions, each doing a small, clear task.

I did this to move from writing everything in `main()` to creating modular and reusable methods.

I also made sure the code handled errors smoothly using `try-catch` blocks or safe input handling.

This program helped me really get the hang of structuring Java programs thinking in terms of small, logical building blocks.


## input.txt

This file holds test data for all the Java files.

Instead of entering numbers or text every time, I placed them in this file so I could just run the code directly and see results.

It helped me test multiple scenarios quickly and keep consistency across runs.


## 🔧 How to Run

```
javac Nine.java
javac Ten.java
javac Fifteen.java
```

```
java Nine input.txt
java Ten input.txt
java Fifteen input.txt
```
