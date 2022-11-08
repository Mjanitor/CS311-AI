import java.io.*;
import java.nio.file.*;
import java.util.*;

// I'm not sure if this was what was indicated by the prompt, but I automated it anyway.  This is a program to automate the collection and writing of data responses for usage in the main program.  It presents the user with 10 questions with an a), b), c), or d) response and from that, it will store those answers in a list, in order.  After it collects all the answers, the final question is "What political party do you most closely identify with?"  Once that is selected, the program will then take that list of responses and increment the corresponding party's text file in the appropriate spaces.  This is repeated as many times as needed to build out the data (in my case 10 times due to limited participation).

class dataGather {
  // Prints each of the 10 questions in turn, line by line.
  public static void printQuestion(int count) {
    for(int i = 0; i < 5; i++){
      try {
        File file = new File("questions.txt");
        Scanner myReader = new Scanner(file);
        String line = Files.readAllLines(Paths.get("questions.txt")).get(count + i);
        System.out.println(line);
        // Generic catch needed when reading .txt files
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
    }
  }

  // Determines which line that the program will write to
  public static int determineLine(String answer, int line){
    if(answer.equals("a")){
      return (5 * line) + 0;}
    if(answer.equals("b")){
      return (5 * line) + 1;}
    if(answer.equals("c")){
      return (5 * line) + 2;}
    if(answer.equals("d")){
      return (5 * line) + 3;}
    else{
      return 0;
    }
  }

  // This program will obtain the results of the final "party" question and write all the answers from the aforementioned list to the                corresponding .txt file in the order and location that it was obtained.
  public static void writeResponses(String response, String[] answers) { 
    switch(response){
      case "a":  // Liberal selecton
        String party = "liberal.txt"; 
        // For each response (10)
        for(int i = 0; i < 10; i++){
          try{
            List<String> lines = Files.readAllLines(Paths.get(party));
            String currentValue = Files.readAllLines(Paths.get(party)).get(determineLine(answers[i], i));  // Finds the requisite line
            int newValue = Integer.parseInt(currentValue) + 1;
            currentValue = Integer.toString(newValue);
            lines.set(determineLine(answers[i], i), currentValue); // What line and what text to write
            Files.write(Paths.get(party), lines); // Committing the writing
            // Generic catch needed when reading .txt files
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
          break;
        }
      case "b":  // Democrat selecton
        party = "democrat.txt";
        // For each response (10)
        for(int i = 0; i < 10; i++){
          try{
            List<String> lines = Files.readAllLines(Paths.get(party));
            String currentValue = Files.readAllLines(Paths.get(party)).get(determineLine(answers[i], i));  // Finds the requisite line
            int newValue = Integer.parseInt(currentValue) + 1;
            currentValue = Integer.toString(newValue);
            lines.set(determineLine(answers[i], i), currentValue); // What line and what text to write
            Files.write(Paths.get(party), lines); // Committing the writing
            // Generic catch needed when reading .txt files
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
          break;
        }
      case "c":  // Republican selecton
        party = "republican.txt"; 
        // For each response (10)
        for(int i = 0; i < 10; i++){
          try{
            List<String> lines = Files.readAllLines(Paths.get(party));
            String currentValue = Files.readAllLines(Paths.get(party)).get(determineLine(answers[i], i));  // Finds the requisite line
            int newValue = Integer.parseInt(currentValue) + 1;
            currentValue = Integer.toString(newValue);
            lines.set(determineLine(answers[i], i), currentValue); // What line and what text to write
            Files.write(Paths.get(party), lines); // Committing the writing
            // Generic catch needed when reading .txt files
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
          break;
        }
      case "d":  // Libertarian selecton
        party = "libertarian.txt";
        // For each response (10)
        for(int i = 0; i < 10; i++){
          try{
            List<String> lines = Files.readAllLines(Paths.get(party));
            String currentValue = Files.readAllLines(Paths.get(party)).get(determineLine(answers[i], i));  // Finds the requisite line
            System.out.print("Libertarian Old: ");
            System.out.println(currentValue);
            int newValue = Integer.parseInt(currentValue) + 1;
            currentValue = Integer.toString(newValue);
            lines.set(determineLine(answers[i], i), currentValue); // What line and what text to write
            Files.write(Paths.get(party), lines); // Committing the writing
            // Generic catch needed when reading .txt files
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
          break;
        }
    }
  }

  // Main function
  public static void main(String[] args) {
    // Creating the "empty" string to receive answer letters
    String[] answers = {"x", "x", "x", "x", "x", "x", "x", "x", "x", "x"};
    int lineCount = 0;
    Scanner input = new Scanner(System.in);
    // Loop through 10 questions
    for(int i = 0; i < 10; i++){
      printQuestion(lineCount);
      lineCount += 6;
      // Get Answer
      String answer = input.nextLine();
      // Input Validation
      while(!answer.equals("a") && !answer.equals("b") && !answer.equals("c") && !answer.equals("d")){
        System.out.println("Try again.");
        answer = input.nextLine();
      }
      // Overwrite answer of current index
      answers[i] = answer;
      System.out.println("");
    }
    printQuestion(60); // Printing the party validation question, manually inputting line 60
    String party = input.nextLine();
    // Input Validation
    while(!party.equals("a") && !party.equals("b") && !party.equals("c") && !party.equals("d")){
      System.out.println("Try again.");
      party = input.nextLine();
    }
    writeResponses(party, answers);
  }
}
