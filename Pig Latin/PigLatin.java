/*
 Author: Rees de la Houssaye
 Date: 5/10/2018
 Description: Simple program to turn an english word into its pig latin couterpart.
 */
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PigLatin
{
    public static void main(String args[])
    {
        System.out.print("Please enter an english word... ");
        Scanner scan = new Scanner(System.in);
        String word = scan.nextLine();
        scan.close();
        
        Matcher match = Pattern.compile("[0123456789-_=+?.>,<`~!@#$%^&*:;]").matcher(word); //planning on making another pig latin program using matcher and pattern that translates whole phrases
        if(match.find())
        {
            System.out.println("One or more of the characters you inputted in " + word + " is invalid.");
            return;
        }
        
        int i = 0;
        
        while(word.charAt(i) != 'a' && word.charAt(i) != 'e' && word.charAt(i) != 'i' && word.charAt(i) != 'o' && word.charAt(i) != 'u') //finds first vowel index location in word
        {
            if(i+2 > word.length()) //make sure iterator is not at end of word. break if it is.
            {
                i = i + 2;
                break;
            }
            i++;
        }
        
        if(i > word.length())
        {
            System.out.println(word + " is not a real english word.");
            return;
        }
        else if(i == 0)
        {
            word = word.concat("ay");
            System.out.println(word);
            return;
        }
        else //if the vowel is not at beginning do this
        {
            for(int x = 0; x < i; x++)
            {
                word = word.concat(Character.toString(word.charAt(x))); //concat the the letters before the vowel onto the end of the word
            }
            word = word.substring(i, word.length()); // get rid of letters before vowel.
            word = word.concat("ay");
            System.out.println(word);
            return;
        }
        
        
    }
}
