/*
 Autor: Rees de la Houssaye
 Date: 5/14/2018
 Description: Program that determines if a word is the same backwards as it is forwards.
 */
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Palindrome
{
    public static void main(String args[])
    {
        System.out.print("Please enter a word... ");
        Scanner scan = new Scanner(System.in);
        String word = scan.nextLine();
        String secWord = "";
        scan.close();
        
        int index = word.length()-1;
        
        for(; index >= 0; index--)
        {
            secWord += word.charAt(index);
        }
        
        Matcher match = Pattern.compile(word).matcher(secWord);
        
        if(match.find())
        {
            System.out.println(word + " is a palindrome.");
        }
        else
        {
            System.out.println(word + " is not a palindrome.");
        }
    }
}
