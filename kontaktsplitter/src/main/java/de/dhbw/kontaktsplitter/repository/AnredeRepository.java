package de.dhbw.kontaktsplitter.repository;

import java.util.Collection;

import de.dhbw.kontaktsplitter.model.Anrede;
import de.dhbw.kontaktsplitter.model.Geschlecht;
import de.dhbw.kontaktsplitter.model.Land;

public class AnredeRepository extends Repository<Anrede>
{
    private static AnredeRepository instance;

    public static AnredeRepository instance()
    {
        if (AnredeRepository.instance == null)
        {
            AnredeRepository.instance = new AnredeRepository();
        }
        return AnredeRepository.instance;
    }

    private AnredeRepository()
    {
        super();
    }

    @Override
    protected void generateData()
    {
        this.generateData(Land.DE, Geschlecht.MALE, "Herr");
        this.generateData(Land.DE, Geschlecht.FEMALE, "Frau");
        this.generateData(Land.DE, Geschlecht.NONE, "Damen und Herren");
        this.generateData(Land.EN, Geschlecht.MALE, "Mr");
        this.generateData(Land.EN, Geschlecht.FEMALE, "Mrs");
        this.generateData(Land.EN, Geschlecht.NONE, "Sirs");
    }

    @Override
    public Collection<Anrede> getData()
    {
        return super.getData(Anrede.class);
    }

}
