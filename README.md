# Hacking Race: A Vault Cracking Simulation

This project is a multi-threaded Java application that simulates a race between different algorithms to crack a secure vault's password. It provides a graphical user interface to visualize the process and compare the efficiency of each cracking strategy.

## Features

- **Graphical User Interface:** A simple Swing-based GUI to set the vault's password and monitor the cracking progress.
- **Multi-threading:** The application uses multiple threads to run the cracking algorithms concurrently.
- **Multiple Cracking Strategies:** The simulation includes three different cracking algorithms:
    - **Ascending Cracker:** A linear search that tries passwords from 0 to 9999.
    - **Descending Cracker:** A linear search that tries passwords from 9999 to 0.
    - **Binary Search Cracker:** A binary search algorithm that efficiently narrows down the search space.
- **Police Thread:** A thread that simulates a police intervention, adding a time limit to the cracking process.

## How to Run

This is a standard Maven project. To run the application, you can use your favorite IDE or the command line.

### From an IDE

1.  Open the project in your IDE (e.g., IntelliJ IDEA, Eclipse).
2.  Run the `Main` class located in `src/main/java/org/Main.java`.

### From the Command Line

1.  Make sure you have Maven and a JDK (version 25 or higher) installed.
2.  Navigate to the project's root directory.
3.  Compile the project using the following command:
    ```bash
    mvn compile
    ```
4.  Run the application using the following command:
    ```bash
    mvn exec:java -Dexec.mainClass="org.Main"
    ```

## Cracking Strategies

The simulation demonstrates three different password cracking strategies:

### 1. Ascending Cracker

This cracker uses a simple linear search, starting from password `0` and incrementing by one until it finds the correct password or reaches the maximum limit (`9999`). This strategy is most effective when the password is a low number.

### 2. Descending Cracker

This cracker also uses a linear search, but it starts from the maximum password (`9999`) and decrements by one until it finds the correct password or reaches the minimum limit (`0`). This strategy is most effective when the password is a high number.

### 3. Binary Search Cracker

This cracker uses a binary search algorithm to find the password. It repeatedly divides the search interval in half, making it significantly more efficient than the linear search strategies. This cracker will almost always win the race due to its logarithmic time complexity.

## Project Structure

The project is organized into the following classes:

-   `Main`: The entry point of the application. It sets up the GUI and starts the simulation.
-   `SecureVault`: Represents the vault with a secret password. It includes a simulated delay for each password guess.
-   `VaultCracker`: An abstract class for the cracker threads.
-   `VaultCrackerAscending`: A concrete implementation of `VaultCracker` that uses an ascending search.
-   `VaultCrackerDescending`: A concrete implementation of `VaultCracker` that uses a descending search.
-   `VaultCrackerBinary`: A concrete implementation of `VaultCracker` that uses a binary search.
-   `CrackingMonitor`: A GUI component that displays the progress of each cracker.
-   `GameState`: A class that manages the state of the simulation (e.g., whether the game is over).
-   `PoliceThread`: A thread that simulates a police intervention, ending the game after a certain amount of time.
