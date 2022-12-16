package mypackage;

import java.util.Scanner;

public class Main {
    private static Double num1 = 0d;
    private static Double num2 = 0d;
    private static String operator = "";
    private static Double $last = 0d;

    public static void main(String[] args) {
        
        System.out.println("Welcome.");
        //-------
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            String content = scanner.nextLine().trim();

            // Handle "exit" --> print and break;
            if (content.equalsIgnoreCase("exit")) {
                System.out.println("Bye bye");
                break;
            }
            content = content.replace("$last", ""+$last);

            String[] words = content.split(" "); 
            
            // Validate input
            // "exit" handled above
            if (words.length != 3) {
                System.out.println("Expression not recognized");
                continue;
            }

            try {
            num1 = Double.parseDouble(words[0]); 
            operator = words[1];
            num2 = Double.parseDouble(words[2]);
            } catch (NumberFormatException e) {
                // Cannot parse input to Double
                System.out.println("Numbers not reggnized");
                continue;
            }

            switch (operator) {

                case "+":
                    $last = num1 + num2;
                    break;
                case "-":
                    $last = num1 - num2;
                    break;
                case "*":
                    $last = num1 * num2;
                    break;
                case "/":
                    try {
                        $last = num1 / num2;
                    } catch (ArithmeticException e){
                        // Apparently you can divide two Doubles
                        // So.. this will never run
                        // Still here because Singaporean kiasu
                        System.out.println("Nice try... Cannot divide by zero");
                        continue;
                    }
                    break;
                case default:
                    System.out.println("Cannot recognize operator.");
                    continue;
            }

            formatPrint($last);
        }
        scanner.close();
        System.exit(0);
    }

    private static void formatPrint(double value){
        /*
         * Method exist to conform with test case in question
         * Input must accept decimals, but output displays no-decimals when evaluating two "integers"
         * Probaby susceptible to floating-point rounding issue
         */
        if(value % 1 == 0) {
            System.out.println((int) value);
        } else {
            System.out.println(value);
        }
    }
}