import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class quizGen {
    private ArrayList<String> qsAndAs;
    private ArrayList<String> questions;
    private ArrayList<String> answers;
    private ArrayList<String> quizQs;
    private ArrayList<String> quizAs;
    private ArrayList<PrintStream> quizzes;

    public static void main(String[] args) throws FileNotFoundException {
        new quizGen();
    }
    public quizGen() throws FileNotFoundException {
        this.questions = new ArrayList<String>();
        this.answers = new ArrayList<String>();
        this.quizQs = new ArrayList<String>();
        this.quizAs = new ArrayList<String>();
        this.qsAndAs = new ArrayList<String>();
        displayIntro();
        readQuestion();
        makeQuiz();
        quizDisplay();
        quizzes.add(saveQuiz());
    }
    public static void displayIntro(){
        System.out.println("Welcome to the Java Geography Quiz Generator!");
        System.out.println("This application takes a preset list of 360 questions ");
        System.out.println("and makes a quiz completely randomly from a number that is entered.");
        System.out.println("The questions and answers can be saved to a file at the end of the program.");
    }

    public void readQuestion() throws FileNotFoundException {
        String file = "/Users/jeremy/IdeaProjects/QuizGenerator/src/question bank and answers.csv";
        File f = new File(file);
        Scanner sc = new Scanner(f);
        sc.useDelimiter(",");
        while (sc.hasNext())  //returns a boolean value
        {
            qsAndAs.add(sc.nextLine()); //find and returns the next complete token from this scanner
        }
        sc.close();  //closes the scanner
        System.out.println(questions);
        ArrayList<String> tempqsandas = new ArrayList<String>();
        int commaindex = 0;
        int comma2index = 0;
        int questionindex = 0;
        for(int i = 0; i < qsAndAs.size();i++){
            for(int j = 0; j < qsAndAs.get(i).length(); j++){
                if(qsAndAs.get(i).charAt(j) == ','){
                    commaindex = j;
                }


                if(qsAndAs.get(i).charAt(j) == '?'){
                    questionindex = j;
                    tempqsandas.add(qsAndAs.get(i).substring(commaindex+1,questionindex+1));
                }
                if(qsAndAs.get(i).charAt(j) == ','&& (questionindex == j-1 || questionindex == j-2)){
                    comma2index = j;
                    tempqsandas.add(qsAndAs.get(i).substring(comma2index+1,qsAndAs.get(i).length()));

                }


            }
        }
        for(int k = 0; k < tempqsandas.size(); k+=2){
            questions.add((String) tempqsandas.toArray()[k]);
        }
        for(int l = 1; l < tempqsandas.size(); l+=2){
            answers.add((String) tempqsandas.toArray()[l]);
        }


        System.out.println(qsAndAs);
        System.out.println(tempqsandas);
        System.out.println(questions);
        System.out.println(answers);



    }
    public void makeQuiz(){
        Scanner tempScan = new Scanner(System.in);
        System.out.println("Please enter the amount of questions in your quiz:");
        int questionNum = -1;
        while (questionNum < 0 ){
            try {
                questionNum = Integer.parseInt(tempScan.nextLine());
            }
            catch(NumberFormatException e)
            {
                System.out.println("Please enter a valid number");
            }
        }
        System.out.println(questionNum);

        ArrayList<String> tempQuestions = new ArrayList<String>();
        ArrayList<String> tempAnswers = new ArrayList<String>();
        while (!this.questions.isEmpty()){
            int randIndex = ((int)(Math.random() * 100))% this.questions.size();
            String tempQ = this.questions.remove(randIndex);
            String tempA = this.answers.remove(randIndex);
            tempAnswers.add(tempA);
            tempQuestions.add(tempQ);
        }
        questions = tempQuestions;
        answers = tempAnswers;
        ;


        for(int i = 0; i < questionNum; i++){
            quizQs.add(questions.remove(i));
        }
        for(int j = 0; j < questionNum; j++){
            quizAs.add(questions.remove(j));
        }


    }
    public void quizDisplay(){
        for (int i = 0; i < quizQs.size(); i++){
        System.out.println("Question: " + quizQs.toArray()[i] +
                " \nAnswer: " + quizAs.toArray()[i]);
        }
    }
    public PrintStream saveQuiz() throws FileNotFoundException {
        Scanner saveScan = new Scanner(System.in);
        System.out.println("Would you like to save this quiz?");
        if(saveScan.next().toLowerCase().contains("yes")){
            String quizname = "Quiz";
            PrintStream quiz = new PrintStream(quizname);
            for (int i = 0; i < quizQs.size(); i++){
                quiz.println("Question: " + quizQs.toArray()[i]  +
                        "\nAnswer: " + quizAs.toArray()[i]);
            }
            System.out.println("quiz saved");
            return quiz;
        }
        return null;
    }
}