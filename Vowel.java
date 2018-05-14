import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Vowel
{
    public static void main(String args[])
    {
        int count = 0;
        Scanner scan = new Scanner(System.in);
        String word = scan.nextLine();
        Matcher match = Pattern.compile("[aeiou]").matcher(word);
        
        while(match.find())
        {
            count++;
        }
        
        System.out.println(count);
    }
}
