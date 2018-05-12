/*
 Author: Rees de la Houssaye
 Date: 5/11/2018
 Description: Program that takes a phrase or word and gives the pig latin version of it. Does not hand tab or more than one whitespace or periods or question/exclamation marks :p.
 */
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Piglatinphrase
{
    public static void main(String args[])
    {
        System.out.print("Please enter an english word... ");
        Scanner scan = new Scanner(System.in);
        String phrase = scan.nextLine();
        String newPhrase = "";
        scan.close();
        Matcher match = Pattern.compile("[0123456789-_=+?.!>,<`~@#$%^&*:;]").matcher(phrase);
        
        if(match.find())
        {
            System.out.println("One or more of the characters you inputted in " + phrase + " is invalid.");
            return;
        }
        
        int x = 0;
        int i = 0;
        while(i < phrase.length())
        {
            if(Character.isWhitespace(phrase.charAt(i)) || i+1 == phrase.length())
            {
                String word = "";
                if(i+1 == phrase.length())
                {
                    word = phrase.substring(x,i+1);
                }
                else
                {
                    word = phrase.substring(x, i);
                }
                Matcher match2 = Pattern.compile("[aeiouy]").matcher(word);
                if(!(match2.find()))
                   {
                       System.out.println(word + " is not a real english word. Terminating program.");
                       return;
                   }
                x = 0;
                for(; word.charAt(x) != 'a' && word.charAt(x) != 'e' && word.charAt(x) != 'i' && word.charAt(x) != 'o' && word.charAt(x) != 'u' && word.charAt(x) != 'y'; x++)
                {
                }
                if(x == 0)
                {
                    word = word.concat("ay ");
                    newPhrase += word;
                }
                else
                {
                    word = word.concat(word.substring(0,x) + "ay ");
                    word = word.substring(x,word.length());
                    newPhrase += word;
                }
                x = i+1;
            }
            i++;
        }
        System.out.println(newPhrase);
    }
}
