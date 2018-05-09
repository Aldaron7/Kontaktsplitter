package de.dhbw.kontaktsplitter.test;

import org.junit.Test;

import de.dhbw.kontaktsplitter.Splitter;
import de.dhbw.kontaktsplitter.model.Kontakt;

public class TestSplitter
{
    private Splitter splitter = new Splitter();
    private String[] names    = new String[] { "Frau Sandra Berger", "Herr Dr. Sandro Gutmensch", "Professor Heinrich Freiherr vom Wald", "Mrs. Doreen Faber",
                                               "Mme. Charlotte Noir", "Estobar y Gonzales", "Frau Prof. Dr. rer. nat. Maria von Leuthäuser-Schnarrenberger",
                                               "Herr Dipl. Ing. Max von Müller", "Dr. Russwurm, Winfried",
                                               "Herr Dr.-Ing. Dr. rer. nat. Dr. h.c. mult. Paul Steffens" };

    @Test
    public void testSplit()
    {
        for (String input : this.names)
        {
            Kontakt kontakt = this.splitter.split(input);
            System.out.println(kontakt);
        }
    }
}
