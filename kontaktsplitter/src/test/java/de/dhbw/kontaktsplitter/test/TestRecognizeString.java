package de.dhbw.kontaktsplitter.test;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import de.dhbw.kontaktsplitter.repository.NamenszusatzRepository;
import de.dhbw.kontaktsplitter.repository.TitelRepository;

public class TestRecognizeString
{

    @Test
    public void testRecognizeTitel()
    {
        TitelRepository titelRepo = TitelRepository.instance();
        Iterator<String> iterator = titelRepo.getValues().iterator();
        StringBuilder regex = new StringBuilder("(");
        while (iterator.hasNext())
        {
            regex.append(iterator.next().replaceAll("\\.", "\\\\."));
            if (iterator.hasNext())
            {
                regex.append("|");
            }
        }
        regex.append(")");
        System.out.println(regex.toString());
        Pattern pattern = Pattern.compile(regex.toString());
        for (String input : Cons.NAMES)
        {
            System.out.println(input);
            Matcher matcher = pattern.matcher(input);
            while (matcher.find())
            {
                System.out.print(matcher.group() + " ");
            }
            System.out.println();
        }
    }

    @Test
    public void testRecognizeNamenszusatz()
    {
        NamenszusatzRepository instance = NamenszusatzRepository.instance();
        Iterator<String> iterator = instance.getValues().iterator();
        StringBuilder regex = new StringBuilder("(");
        while (iterator.hasNext())
        {
            regex.append(iterator.next().replaceAll("\\.", "\\\\."));
            if (iterator.hasNext())
            {
                regex.append("|");
            }
        }
        regex.append(")");
        System.out.println(regex.toString());
        Pattern pattern = Pattern.compile(regex.toString());
        for (String input : Cons.NAMES)
        {
            System.out.println(input);
            Matcher matcher = pattern.matcher(input);
            while (matcher.find())
            {
                System.out.print(matcher.group() + " ");
            }
            System.out.println();
            for (String namenszusatz : NamenszusatzRepository.instance().getValues())
            {
                if (input.contains(namenszusatz))
                {
                    System.out.println(namenszusatz);
                }
            }
        }

    }
}
