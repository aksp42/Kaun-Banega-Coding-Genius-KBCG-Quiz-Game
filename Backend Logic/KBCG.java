package kbcg;

import java.io.*;
import java.util.*;

public class KBCG {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("----------------------------------------------------------");
        System.out.println("Welcome to KBCG - Kaun Banega Coding Genius!");
        System.out.println("----------------------------------------------------------");
        System.out.print("Enter Coder Name : ");
        Player player = new Player(sc.nextLine());
        System.out.println("----------------------------------------------------------");

        
        System.out.println("\nChoose Language: ");
        System.out.println("1. Python  2. Java  3. C  4. C++");
        int langChoice = sc.nextInt();
        String language = switch (langChoice) {
            case 1 -> "Python";
            case 2 -> "Java";
            case 3 -> "C";
            case 4 -> "Cpp";
            default -> "Python";
        };

        
        for (int level = 1; level <= 3; level++) {
            int numQuestions = (level == 1) ? 5 : (level == 2) ? 4 : 3;

            List<Question> questions = loadQuestions(language, level);
            if (questions.isEmpty()) {
                System.out.println("No questions found for " + language + " Level " + level);
                continue;
            }

            playLevel(questions, player, level, numQuestions);

            if (player.getLives() <= 0) {
                System.out.println("\nYou ran out of lives! Game Over!");
                break;
            }

            
            System.out.println("\nCongratulations! You completed Level " + level  );
            System.out.println("Your current score: " + player.getScore());

            if(level == 1) {
                System.out.println("Awesome! You are ready to take on Level 2 \n Keep your coding hat on! ");
                System.out.println("--------------------------------------------------------------");
            } else if(level == 2) {
                System.out.println("Amazing! Level 3 is calling. \n Show your coding genius! ");
                System.out.println("--------------------------------------------------------------");
            } else {
                System.out.println("Incredible! You have conquered all levels! \n You are a true Code Genius! ");
                System.out.println("--------------------------------------------------------------");
            }
        }

        
        System.out.println("\n" + player.getName() + "'s Final Score: " + player.getScore());
        if (player.getScore() >= 10)
            System.out.println("Congratulations! You are a Code Genius! ");
        else
            System.out.println("Keep practicing to become a Code Genius!");
    }

    
    static List<Question> loadQuestions(String language, int level) {
        List<Question> questions = new ArrayList<>();
        String fileName = language + "_L" + level + ".csv"; 

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;

            // skip header
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    questions.add(new Question(parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]));
                }
            }

            br.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return questions;
    }

    
    static void playLevel(List<Question> questions, Player player, int level, int numQuestions) {
        System.out.println("\n------------- Level " + level + "-----------");
        Collections.shuffle(questions); 

        for (int i = 0; i < numQuestions && player.getLives() > 0; i++) {
            Question q = questions.get(i);
            q.display(i + 1);

            System.out.print("Enter your answer (A/B/C/D): ");
            String userAnswer = sc.next();

            if (q.checkAnswer(userAnswer)) {
                System.out.println("Correct Answer! +1 Star\n");
                player.addScore();  // fixed
            } else {
                System.out.println(" Wrong Answer!");
                System.out.println("Correct Answer was: " + q.getCorrectOptionText() + "\n");
                player.loseLife();
            }

            System.out.println("Score: " + player.getScore() + " | Lives: " + player.getLives());
        }
    }
}
