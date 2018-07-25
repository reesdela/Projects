/*
 Author: Rees de la Houssaye
 Date: 7/25/18
 Description: Simple program that uses the Jsoup library to get the Internation Atomic Time off the web.
 */
import java.io.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JavaApplication3
{
    public static void main(String[] args)
    {
        try
        {
            Document doc = Jsoup.connect("https://www.timeanddate.com/time/international-atomic-time.html").get();
            Element temp = doc.select("span.ctm-hrmn").first();
            Element temp2 = doc.select("span.ctm-sec").first();
            System.out.println(temp.text() + ":" + temp2.text());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
