package de.dhbw.kontaktsplitter.test;

import org.junit.Test;

import de.dhbw.kontaktsplitter.Splitter;
import de.dhbw.kontaktsplitter.model.Kontakt;

public class TestSplitter
{
    private Splitter splitter = new Splitter();

    @Test
    public void testSplit()
    {
        for (String input : Cons.NAMES)
        {
            Kontakt kontakt = this.splitter.split(input);
            System.out.println(kontakt);
        }
    }
}
