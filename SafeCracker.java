import java.util.concurrent.TimeUnit;
import java.util.Scanner;
import java.util.Arrays;
public class SafeCracker
{
   private static int fitness(int[] combo_list,  int[] attempt_list) 
   {
      // compare items in the two lists and count number of matches
      int grade = 0;
      for (int i = 0; i < combo_list.length; i++) {
         if (combo_list[i] == attempt_list[i])
            grade++;
      }
      return grade;
   }
   
   private static void crackIt() {
      Scanner myScan = new Scanner(System.in);
      System.out.print("Enter the combination that you want cracked: ");
      String combination = myScan.nextLine();
      int num_digits = combination.length();
      System.out.println("\nCombination = " + combination + ", number of digits = " + num_digits);
      
      // convert combination to an array (easier to work with later)
      String[] s_temp = combination.split("");
      int[] combo_list = new int[num_digits];
      for (int i=0; i < num_digits; i++)
          combo_list[i] = Integer.parseInt(s_temp[i]);

      // generate initial guess and obtain an initial fitness grade
      int[] best_attempt = new int[num_digits]; // java initializes the array to all zeroes
      int best_attempt_grade = fitness(combo_list, best_attempt);
      
      int count = 0;
      
      // evolve guess
      int[] next_try = new int[num_digits];
      while (! Arrays.equals(combo_list, best_attempt)) {
         // crossover of genetic material, in this case 100% crossover
         // represented by choosing the best_attempt found so far
         next_try = best_attempt.clone();
         
         // mutate (randomly pick a digit to change randomly)
         int lock_wheel = (int)(Math.random()*combo_list.length);
         next_try[lock_wheel] = (int)(Math.random()*10);
         
         // determine fitness grade (survival via natural selection)
         int next_try_grade = fitness(combo_list, next_try);
         //System.out.println("best_attempt_grade = " + best_attempt_grade);
         if (next_try_grade > best_attempt_grade) {
            best_attempt = next_try.clone();
            best_attempt_grade = next_try_grade;
         }
         //System.out.println("next_try = " + Arrays.toString(next_try) + ", best_attempt = " + 
         //                   Arrays.toString(best_attempt));
         count++;
      }
      System.out.print("\nCracked! correct value is " + Arrays.toString(best_attempt));
      System.out.println(" in " + count + " tries!");
   }

   public static void main(String[] args) {
      long startTime = System.nanoTime();
      crackIt();
      long endTime = System.nanoTime();
      long timeElapsed = endTime - startTime;
      System.out.println("\nRuntime for this program was " + timeElapsed/1000000 + " milliseconds");
   }
}