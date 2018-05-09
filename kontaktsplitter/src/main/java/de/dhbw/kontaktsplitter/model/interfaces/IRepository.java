package de.dhbw.kontaktsplitter.model.interfaces;

import java.util.Collection;
import java.util.Optional;

import de.dhbw.kontaktsplitter.model.Geschlecht;
import de.dhbw.kontaktsplitter.model.Land;

public interface IRepository<T>
{
    public Collection<T> getData();

    public Collection<String> getValues();

    public boolean contains(String value);

    public Optional<Land> getLand(String value);

    public Optional<Geschlecht> getGeschlecht(String value);
}
