package myotherpackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class main {
    
    private static String csv = "";
    private static String txt = "";
    private static List<String> headers = new LinkedList<>();
    private static List<String> values = new LinkedList<>();

    public static void main(String[] args) {
        
        // btw, you can press F5 in vscode to run .java files without compiling
        // and you can declare your myArgs and assign to args to simulate CLI input
        
        // String[] myArgs = {"task01/thankyou.csv", "task01/thankyou.txt"};
        // String[] myArgs = {"task01/tour_packages.csv", "task01/tour_packages.txt"};
        // args = myArgs;    

        if(!isArgsValid(args)) System.exit(-1);

        csv = args[0];
        txt = args[1];

        String templateContents = loadTemplate(txt);

        List<String> letters = generateLetters(templateContents);

        System.out.println("=".repeat(20));
        System.out.println("%d letters generated".formatted(letters.size()));
        for (String letter : letters) {
            System.out.println("=".repeat(20));
            System.out.println(letter);
        }
    }

    private static boolean isArgsValid (String[] args) {

        // Must be two args
        if(args.length != 2) {
            System.out.println("Please provide file names");
            return false;
        }

        // First arg must be .csv
        if(!args[0].contains(".csv")) {
            System.out.println("First argument must be.csv file");
            return false;
        }
        
        // Second arg must be .txt
        if(!args[1].contains(".txt")) {
            System.out.println("First argument must be .txt file");
            return false;
        }
        return true;
    }

    private static String loadTemplate(String fname) {
        System.out.println("Loading template...");
        String templateContents = "";
        
        try (BufferedReader br = Files.newBufferedReader(Paths.get(txt))){        
            
            String content = "";
            while (true) {
                
                content = br.readLine();
                if (content == null) break;

                templateContents += content + "\n";

            }
        } catch(IOException e) {
            System.out.print("Error reading .txt file");
            System.exit(-1);
        }
        return templateContents;
    }

    private static String getFilledTemplate(List<String> headers, List<String> values, String templateContents) {
        String filledTemplate = templateContents;

        if(headers.size() != values.size()) {
            System.out.println("cannot map value -> header");
            return null;
        }
    
        for (int i = 0; i < headers.size(); i++) {
            filledTemplate = filledTemplate.replace("<<%s>>".formatted(headers.get(i)), values.get(i));
        }

        return filledTemplate;
    }

    private static List<String> generateLetters(String templateContents) {
        System.out.println("Generating letters...");
        try (BufferedReader br = Files.newBufferedReader(Paths.get(csv))) {
            
            String contents = "";
            String[] words;
            Boolean isHeadersStored = false;
            List<String> filledTemplates = new LinkedList<>();

            while(true) {
                contents = br.readLine();
                if (contents == null) break;

                words = contents.split(",");
                
                if(!isHeadersStored) {
                    for(String word : words) {
                        headers.add(word.trim());
                        isHeadersStored = true;
                    }
                    continue;
                }

                // clear values from previous iterations
                values.removeAll(values);
                for (String word : words) {
                    values.add(word.trim().replace("\\n", "\n"));
                }

                // returned the letter here instead of printing immediately
                // because my console was not printing all the filledTemplates during testing
                // so i'm saving them into a list to check size
                String filledTemplate = getFilledTemplate(headers, values, templateContents);
                filledTemplates.add(filledTemplate);
            }         
            return filledTemplates;
        } 
        catch(IOException e) {
                System.out.print("Error reading .csv file");
                System.exit(-1);
        }
        return headers;
    }
}
