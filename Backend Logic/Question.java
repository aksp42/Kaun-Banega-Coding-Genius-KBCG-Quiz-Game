package kbcg;

public class Question {
    private String question;
    private String optionA, optionB, optionC, optionD;
    private String answer;  

    public Question(String question, String a, String b, String c, String d, String ans) {
        this.question = question;
        this.optionA = a;
        this.optionB = b;
        this.optionC = c;
        this.optionD = d;
        this.answer = ans; 
    }

    public void display(int qNo) {
        System.out.println("\nQ" + qNo + ". " + question);
        System.out.println("A. " + optionA);
        System.out.println("B. " + optionB);
        System.out.println("C. " + optionC);
        System.out.println("D. " + optionD);
    }

    
    public String getCorrectOptionText() {
        switch (answer.toUpperCase()) {
            case "A": return "A. " + optionA;
            case "B": return "B. " + optionB;
            case "C": return "C. " + optionC;
            case "D": return "D. " + optionD;
            default: return "Unknown";
        }
    }

    public boolean checkAnswer(String ans) {
        return ans.equalsIgnoreCase(answer);
    }
}
