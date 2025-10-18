# Kaun Banega Coding Genius (KBCG!) - Java Swing Quiz Game

## 🌟 Project Overview

**KBCG!** is an interactive, console-style quiz application built using **Java Swing** that challenges users' knowledge in various programming languages (Java, Python, C, C++). The game is designed to be a fun and engaging way to test fundamental coding concepts, featuring a KBC (Kaun Banega Crorepati) style interface with custom visual feedback and level progression.

## ✨ Features

* **Multi-Language Quizzes:** Currently supports quizzes for **Java, Python, C, and C++**.
* **Level Progression:** The game advances through 3 levels of increasing difficulty.
* **Custom UI/UX:** Built with a modern dark theme using custom **Java Swing components** and `RoundedButtonUI` for an attractive, professional look (as seen in the screenshots).
* **Themed Dialogs:** Custom `JDialogs` provide distinct visual feedback for correct answers, wrong answers, Level Up (Orange Header), Game Over (Red Header), and Game Win (Green Header).
* **Lives System:** Players start with 3 lives (`❤️`) and lose one for each incorrect answer.
* **Data Driven:** All quiz questions are loaded dynamically from specific **CSV files** (e.g., `Java_L1.csv`), making the content easy to manage and expand.

## 🛠️ Technology Stack

* **Core Language:** Java
* **GUI Library:** Java Swing (AWT/Swing)
* **Build/Run Environment:** Standard JDK (Tested with Java 8+)
* **Data Format:** CSV (for Question Bank)

## 🚀 Getting Started

Follow these steps to download, compile, and run the KBCG! game on your local machine.

### Prerequisites

You need to have the **Java Development Kit (JDK)** installed on your system.

### Installation and Setup

1.  **Clone the Repository:**
    ```bash
    git clone [YOUR GITHUB REPO LINK]
    cd kbcg-java-quiz
    ```

2.  **Ensure Question Files:**
    Make sure all necessary CSV files (`Java_L*.csv`, `Python_L*.csv`, etc.) and the background image (`k2.png`) are present in the root directory alongside the Java source files.

3.  **Compile the Code:**
    Compile all Java files into a `bin` directory:
    ```bash
    mkdir bin
    javac -d bin *.java
    ```

4.  **Run the Game:**
    Start the main application class (`KBCG_Main.java` is assumed to contain the main method):
    ```bash
    java -cp bin KBCG_Main
    ```

## 📂 Project Structure (Key Files)

| File Name | Description |
| :--- | :--- |
| `KBCG_Main.java` | Main entry point of the application. |
| `KBCG_Interface.java` | Handles the name input and language selection screens. |
| `QuizChallengeGUI.java` | Main game window and core UI/logic integration. |
| `CustomMessageDialog.java` | Reusable class for creating custom-styled dialogs (Win/Loss/Level Up). |
| `QuizManager.java` | Backend logic handling question loading, life management, and scoring. |
| `Player.java`, `Question.java` | Simple data models for game entities. |
| `*_L*.csv` | Question bank files (e.g., `Java_L1.csv`, `Python_L2.csv`). |

## 🤝 Contribution

Feel free to fork this repository, submit issues, or suggest improvements! Contributions are welcome, especially for expanding the question bank or refining the UI/UX.

## 🧑‍💻 Author

* **Akanksha Singh** - https://www.linkedin.com/in/akanksha-singh-4715a0351/

