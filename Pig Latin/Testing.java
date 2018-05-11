/*
 Author: Rees de la Houssaye
 Date: 5/10/2018
 Description: another version of the same pig latin program i have already posted. A little bit shorter and requires less time as well. Bit of a rushed process. Will go into greater deatil when I program the phrase version. Did not do the whole nine yards with this program as i've already done most of that in the first version
 */

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Testing
{
    public static void main(String [ ] args)
    {
        Scanner scan = new Scanner(System.in);
        String fuck = scan.nextLine();
        
        Matcher match = Pattern.compile("[aeiou]").matcher(fuck);
        
        if(!(match.find()))
        {
            return;
        }
        
        int i = fuck.indexOf('a');
        if(i > fuck.indexOf('e') && fuck.indexOf('e') != -1 || i == -1)
        {
            i = fuck.indexOf('e');
        }
        if(i > fuck.indexOf('i') && fuck.indexOf('i') != -1 || i == -1)
        {
            i = fuck.indexOf('i');
        }
        if(i > fuck.indexOf('o') && fuck.indexOf('o') != -1 || i == -1)
        {
            i = fuck.indexOf('o');
        }
        if(i > fuck.indexOf('u') && fuck.indexOf('u') != -1 || i == -1)
        {
            i = fuck.indexOf('u');
        }
        
       // System.out.println(i);
        
        if(i == 0)
        {
            fuck = fuck.concat("ay");
            System.out.println(fuck);
            return;
        }
        else
        {
            for(int x = 0; x < i; x++)
            {
                fuck = fuck.concat(Character.toString(fuck.charAt(x)));
            }
            fuck = fuck.substring(i, fuck.length());
            fuck = fuck.concat("ay");
            System.out.println(fuck);
            return;
        }
        
    }
}
