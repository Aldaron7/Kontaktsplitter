package de.dhbw.kontaktsplitter.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import de.dhbw.kontaktsplitter.model.Geschlecht;
import de.dhbw.kontaktsplitter.model.Kennung;
import de.dhbw.kontaktsplitter.model.Land;
import de.dhbw.kontaktsplitter.model.interfaces.IKennung;
import de.dhbw.kontaktsplitter.model.interfaces.IRepository;

public abstract class Repository<E> implements IRepository<E>
{

    private Map<Kennung, String> data;

    protected Repository()
    {
        this.data = new HashMap<>();
        this.generateData();
    }

    protected abstract void generateData();

    protected void generateData(Land land, Geschlecht geschlecht, String grussformel)
    {
        this.data.put(new Kennung(land, geschlecht), grussformel);
    }

    @Override
    public abstract Collection<E> getData();

    protected <T extends IKennung> Collection<T> getData(Class<T> cls)
    {
        // Collection<T> result = new ArrayList<>();
        // for (Kennung k : this.data.keySet())
        // {
        // T instance;
        // try
        // {
        // instance = cls.newInstance();
        // instance.setKennung(k);
        // instance.setValue(this.data.get(k));
        // result.add(instance);
        // }
        // catch (InstantiationException | IllegalAccessException e)
        // {
        // e.printStackTrace();
        // }
        // }
        // return result;
        return this.data.keySet().stream().map(k -> {
            T instance = null;
            try
            {
                instance = cls.newInstance();
                instance.setKennung(k);
                instance.setValue(this.data.get(k));
            }
            catch (InstantiationException | IllegalAccessException e)
            {
                e.printStackTrace();
            }
            return instance;
        }).collect(Collectors.toList());
    }

    @Override
    public Collection<String> getValues()
    {
        return this.data.values();
    }

    public Set<Kennung> getKeySet()
    {
        return this.data.keySet();
    }

    public Optional<String> getValue(Kennung kennung)
    {
        String value = this.data.get(kennung);
        return value == null ? Optional.empty() : Optional.of(value);
    }

    public Optional<String> getValue(Land land, Geschlecht geschlecht)
    {
        return this.getValue(new Kennung(land, geschlecht));
    }

    @Override
    public boolean contains(String value)
    {
        return this.data.values().contains(value);
    }

    public Optional<Kennung> getKennung(String value)
    {
        return this.data.keySet().stream().filter(kennung -> this.data.get(kennung).equals(value)).findAny();
    }

    @Override
    public Optional<Land> getLand(String value)
    {
        return this.data.keySet().stream().filter(k -> this.data.get(k).equals(value)).map(Kennung::getLand).findAny();
    }

    @Override
    public Optional<Geschlecht> getGeschlecht(String value)
    {
        return this.data.keySet().stream().filter(k -> this.data.get(k).equals(value)).map(Kennung::getGeschlecht).findAny();
    }

}
