/*
 Author: Rees de la Houssaye
 Date: 5/14/2018
 Description: Program that counts the number of words in a given text input.
*/
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.*;

public class WordCounter
{
    public static void main(String args[]) throws FileNotFoundException, Exception
    {
        while(true)
        {
            System.out.print("Are you entering in a String by hand(press 1) or from a file(press 2)? Press 5 to  exit... ");
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();
            if(input.equals("1"))
            {
                System.out.println("Enter a string... ");
                String word = scan.nextLine();
                wordCount(word);
            }
            else if(input.equals("2"))
            {
                System.out.println("Enter the file path or its name if its in the same folder.");
                String word = new String(Files.readAllBytes(Paths.get(scan.nextLine())));
                wordCount(word);
            }
            else if(input.equals("5"))
            {
                return;
            }
            else
            {
                System.out.println("That is not a valid answer. Please enter a 1, a 2, or a 5.");
            }
        }
    }
    
    public static void wordCount(String word)
    {
        int counter = 0;
        int i = 0;
        boolean delim = false;
        
        while(i < word.length())
        {
            if(!(Character.isWhitespace(word.charAt(i))) && delim == false)
            {
                counter++;
                delim = true;
            }
            if(Character.isWhitespace(word.charAt(i)))
            {
                delim = false;
            }
            i++;
        }
        
        System.out.println(counter);
    }
}
