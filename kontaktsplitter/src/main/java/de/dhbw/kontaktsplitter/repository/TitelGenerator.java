package de.dhbw.kontaktsplitter.repository;

import java.util.Arrays;

import de.dhbw.kontaktsplitter.model.Geschlecht;
import de.dhbw.kontaktsplitter.model.Kennung;
import de.dhbw.kontaktsplitter.model.Land;
import de.dhbw.kontaktsplitter.model.Titel;

public class TitelGenerator
{
    private static TitelRepository TITEL;

    public static TitelRepository getTitel()
    {
        if (TitelGenerator.TITEL == null)
        {
            TitelGenerator.TITEL = TitelRepository.instance();
            Arrays.asList(Geschlecht.values()).forEach(g -> TitelGenerator.TITEL.add(TitelGenerator.generateTitel(Land.DE, g, "Dr.")));
            Arrays.asList(Geschlecht.values()).forEach(g -> TitelGenerator.TITEL.add(TitelGenerator.generateTitel(Land.DE, g, "Prof.")));
            Arrays.asList(Geschlecht.values()).forEach(g -> TitelGenerator.TITEL.add(TitelGenerator.generateTitel(Land.EN, g, "Ph.D.")));
            Arrays.asList(Geschlecht.values()).forEach(g -> TitelGenerator.TITEL.add(TitelGenerator.generateTitel(Land.EN, g, "Prof.")));
        }
        return TitelGenerator.TITEL;
    }

    private static Titel generateTitel(Land land, Geschlecht geschlecht, String titel)
    {
        return new Titel(new Kennung(land, geschlecht), titel);
    }
}
