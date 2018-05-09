package de.dhbw.kontaktsplitter.repository;

import java.util.Collection;

import de.dhbw.kontaktsplitter.model.Geschlecht;
import de.dhbw.kontaktsplitter.model.Grussformel;
import de.dhbw.kontaktsplitter.model.Land;

public class GrussformelRepository extends Repository<Grussformel>
{
    private static GrussformelRepository instance;

    public static GrussformelRepository instance()
    {
        if (GrussformelRepository.instance == null)
        {
            GrussformelRepository.instance = new GrussformelRepository();
        }
        return GrussformelRepository.instance;
    }

    private GrussformelRepository()
    {
        super();
    }

    @Override
    protected void generateData()
    {
        this.generateData(Land.DE, Geschlecht.MALE, "Sehr geehrter");
        this.generateData(Land.DE, Geschlecht.FEMALE, "Sehr geehrte");
        this.generateData(Land.DE, Geschlecht.NONE, "Sehr geehrte");
        this.generateData(Land.EN, Geschlecht.MALE, "Dear");
        this.generateData(Land.EN, Geschlecht.FEMALE, "Dear");
        this.generateData(Land.EN, Geschlecht.NONE, "Dear");
    }

    @Override
    public Collection<Grussformel> getData()
    {
        return super.getData(Grussformel.class);
    }
}
