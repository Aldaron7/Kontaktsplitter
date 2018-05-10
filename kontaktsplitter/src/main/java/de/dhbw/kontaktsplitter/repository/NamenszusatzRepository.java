package de.dhbw.kontaktsplitter.repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import de.dhbw.kontaktsplitter.model.Geschlecht;
import de.dhbw.kontaktsplitter.model.Land;
import de.dhbw.kontaktsplitter.model.interfaces.IRepository;

public class NamenszusatzRepository implements IRepository<String>
{
    private static NamenszusatzRepository instance;

    public static NamenszusatzRepository instance()
    {
        if (NamenszusatzRepository.instance == null)
        {
            NamenszusatzRepository.instance = new NamenszusatzRepository();
        }
        return NamenszusatzRepository.instance;
    }

    private String[] namenszusaetze = new String[] { "von", "van", "de", "ten", "tom", "zu", "von und zu", "vom", "zum", "vom und zum", "von der", "von dem",
                                                     "v." };

    private NamenszusatzRepository()
    {
    }

    @Override
    public Collection<String> getData()
    {
        Arrays.sort(this.namenszusaetze, (s1, s2) -> s2.compareTo(s1));
        return Arrays.asList(this.namenszusaetze);
    }

    @Override
    public Collection<String> getValues()
    {
        return this.getData();
    }

    @Override
    public boolean contains(String value)
    {
        return this.getData().contains(value);
    }

    @Override
    public Optional<Land> getLand(String value)
    {
        return Optional.empty();
    }

    @Override
    public Optional<Geschlecht> getGeschlecht(String value)
    {
        return Optional.empty();
    }

}
