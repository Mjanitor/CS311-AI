import java.io.*;
import java.nio.file.*;
import java.util.*;

// This program will measure the weight of training data that has been acquired previously to predict what political party the user most closely
// associates with.  It does this by asking a question and weighing the probability of that answer with the probability of the choice being made
// by someone who was confirmed to have identified with that party.  If it doesn't have enough confidence to put forth a prediction, the next
// question will be put forward to hopefully strengthen the confidence level.  It will proceed like this until either it has enough confidence 
// to make a guess or the final question is reached.  If the final question is reached, the program will simply guess the party with the 
// current highest amount of confidence.

class partyGuesser {
  // Prints each of the 10 questions in turn, line by line.
  public static void printQuestion(int count) {
  for(int i = 0; i < 5; i++){
    try {
      // Grabs the questions text file
      File file = new File("questions.txt");
      Scanner myReader = new Scanner(file);
      // Stores the line number then prints it out
      String line = Files.readAllLines(Paths.get("questions.txt")).get(count + i);
      System.out.println(line);
      // General exception needed for syntax when reading .txt files
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}
  // The meat of the program that, once an answer is selected, adds confidence based on the            amount of weighted training data
  public static int[] increaseConfidence(int numQuestions, String response){
    String[] parties = {"liberal.txt", "democrat.txt", "republican.txt", "libertarian.txt"};
    int[] arrValues = {0, 0, 0, 0};
    int lineModifier = 0;
    switch(response){
      case "a":  // first answer
        lineModifier = 0;
        // Grabs the value for each of the four party .txt files
        for(int i=0; i < 4; i++)
          try{
            String line = Files.readAllLines(Paths.get(parties[i])).get((5 * numQuestions) + lineModifier);
            arrValues[i] += Integer.parseInt(line); // Writes the weight to the respective index of the arrValues array
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        break;
      case "b":  // second answer
        lineModifier = 1;
        for(int i=0; i < 4; i++)
          try{
            String line = Files.readAllLines(Paths.get(parties[i])).get((5 * numQuestions) + lineModifier);
            arrValues[i] += Integer.parseInt(line);
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        break;
      case "c":  // third answer
        lineModifier = 2;
        for(int i=0; i < 4; i++)
          try{
            String line = Files.readAllLines(Paths.get(parties[i])).get((5 * numQuestions) + lineModifier);
            arrValues[i] += Integer.parseInt(line);
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        break;
      case "d":  // fourth answer
        lineModifier = 3;
        for(int i=0; i < 4; i++)
          try{
            String line = Files.readAllLines(Paths.get(parties[i])).get((5 * numQuestions) + lineModifier);
            arrValues[i] += Integer.parseInt(line);
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        break;
    }
    // Returns the array of current weights to be added to persistent weight
    return arrValues;
  }

  // The final piece of the program which will execute after the third question as a hard-coded rule.  It is what determines if a guess is           made.
  public static boolean guessParty(float[] parties, int question) {
    // Hash Table so the values can be manipulated without losing their party Strings
    Hashtable<Float, String> partyValue = new Hashtable<Float, String>();
    partyValue.put(parties[0], "Liberal");
    partyValue.put(parties[1], "Democrat");
    partyValue.put(parties[2], "Republican");
    partyValue.put(parties[3], "Libertarian");

    // Sorting to make it easier to compare values
    Arrays.sort(parties);
    if (question == 9 || parties[3] - parties[2] > 0.17){  // If at the end of the questions or enough confidence
      System.out.println("You probably most closely identify as a: " + partyValue.get(parties[3]));
      return true;
    }
    return false;
  }

  // Main function 
  public static void main(String[] args) {
    // I'm very sorry about these variables, please forgive me.
    float liberalParticipants = 3;
    float democratParticipants = 3;
    float republicanParticipants = 2;
    float libertarianParticipants = 2;
    float liberalTotal = 0;
    float democratTotal = 0;
    float republicanTotal = 0;
    float libertarianTotal = 0;
    float liberalProbability = 0;
    float democratProbability = 0;
    float republicanProbability = 0;
    float libertarianProbability = 0;
    int lineCount = 0;

    Scanner input = new Scanner(System.in);
    
    for(int i = 0; i < 10; i++){ // One for each question
      printQuestion(lineCount);
      lineCount += 6; // Accounting for lines to skip to the next question
      String response = input.nextLine(); // Gathering each answer
      // Input Validation
      while(!response.equals("a") && !response.equals("b") && !response.equals("c") && !response.equals("d")){
        System.out.println("Try again.");
        response = input.nextLine();
      }
      // Saving answer weights into an array for writing purposes
      int[] arr = increaseConfidence(i, response);
      // Aforementioned writing purposes
      liberalTotal += arr[0];
      democratTotal += arr[1];
      republicanTotal += arr[2];
      libertarianTotal += arr[3];
      // Quick Math to calculate the probability versus the amount of respondants for each party.  This was done so that if I randomly had               10 liberal affiliates take the quiz and 2 republicans, it wouldn't just automatically respond with 'you're a liberal' due to the                sheer amount of liberal responses.
      liberalProbability = ((liberalTotal / liberalParticipants) / (i + 1));
      democratProbability = ((democratTotal / democratParticipants) / (i + 1));
      republicanProbability = ((republicanTotal / republicanParticipants) / (i + 1));
      libertarianProbability = ((libertarianTotal / libertarianParticipants) / (i + 1));
      // To make it easy on the eyes
      System.out.println("------------------------------------");

      // If far enough into the program.  If you try to guess the party too early, you're gonna get bad results.
      if(i >= 2){
        float[] parties = {liberalProbability, democratProbability, republicanProbability, libertarianProbability};
        // If party is guessed, exit the program
        if (guessParty(parties, i)){
          System.exit(0);
        }
      }
    }
  }
}
