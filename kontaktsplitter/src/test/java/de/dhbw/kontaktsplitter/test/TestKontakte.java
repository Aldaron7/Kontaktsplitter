package de.dhbw.kontaktsplitter.test;

import org.junit.Test;

import de.dhbw.kontaktsplitter.Splitter;
import de.dhbw.kontaktsplitter.model.Kontakt;
import de.dhbw.kontaktsplitter.model.Kontakte;

public class TestKontakte
{
    @Test
    public void testKontakteSpeichern()
    {
        Splitter splitter = new Splitter();
        Kontakte kontakte = new Kontakte();
        for (String input : Cons.NAMES)
        {
            Kontakt kontakt = splitter.split(input);
            System.out.println(kontakt);
            kontakte.add(kontakt);
        }
        kontakte.save();
    }

    @Test
    public void testKontakteLoad()
    {
        Kontakte kontakte = new Kontakte();
        kontakte.load();
        System.out.println(kontakte.getKontakte());
    }
}
