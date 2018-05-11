package de.dhbw.kontaktsplitter.test;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Ignore;
import org.junit.Test;

import de.dhbw.kontaktsplitter.Splitter;
import de.dhbw.kontaktsplitter.repository.NamenszusatzRepository;
import de.dhbw.kontaktsplitter.repository.TitelRepository;

public class TestRecognizeString
{
    private Splitter splitter = new Splitter();

    @Test
    @Ignore
    public void testRecognizeTitelAlgo()
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
    @Ignore
    public void testRecognizeNamenszusatzAlgo()
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
                System.out.print("regex: " + matcher.group() + " ");
            }
            System.out.println();
            for (String namenszusatz : NamenszusatzRepository.instance().getValues())
            {
                String regex1 = ".*\\b" + namenszusatz.replaceAll("\\.", "\\\\.") + "\\b.*";
                if (input.matches(regex1))
                {
                    System.out.println("regex1: " + namenszusatz);
                }
            }
        }
    }

    @Test
    public void testRecognizeAnrede()
    {
        System.out.println("--RecognizeAnrede--");
        for (String input : Cons.NAMES)
        {
            System.out.println("input: " + input);
            String result = this.splitter.recognizeAnrede(input).orElse("");
            System.out.println("result: " + result);
        }
    }

    @Test
    public void testRecognizeNamenszusatz()
    {
        System.out.println("--RecognizeNamenszusatz--");
        for (String input : Cons.NAMES)
        {
            System.out.println("input: " + input);
            String result = this.splitter.recognizeNamenszusatz(input).orElse("");
            System.out.println("result: " + result);
        }
    }

    @Test
    public void testRecognizeTitel()
    {
        System.out.println("--RecognizeTitel--");
        for (String input : Cons.NAMES)
        {
            System.out.println("input: " + input);
            List<String> result = this.splitter.recognizeTitel(input);
            System.out.println("result: " + result);
        }
    }

    @Test
    public void testRecognizeNachname()
    {
        System.out.println("--RecognizeNachname--");
        for (String input : Cons.NAMES)
        {
            System.out.println("input: " + input);
            String result = this.splitter.recognizeNachname(input).orElse("");
            System.out.println("result: " + result);
        }
    }

    @Test
    public void testRecognizeVorname()
    {
        System.out.println("--RecognizeVorname--");
        for (String input : Cons.NAMES)
        {
            System.out.println("input: " + input);
            String result = this.splitter.recognizeVorname(input).orElse("");
            System.out.println("result: " + result);
        }

    }
}
