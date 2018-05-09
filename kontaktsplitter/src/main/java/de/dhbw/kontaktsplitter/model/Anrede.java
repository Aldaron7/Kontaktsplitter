package de.dhbw.kontaktsplitter.model;

import de.dhbw.kontaktsplitter.model.interfaces.IKennung;

public class Anrede implements IKennung
{
    private Kennung kennung;
    private String  anrede;

    public Anrede(Kennung kennung, String anrede)
    {
        this.kennung = kennung;
        this.anrede = anrede;
    }

    @Override
    public String getValue()
    {
        return this.anrede;
    }

    @Override
    public void setValue(String anrede)
    {
        this.anrede = anrede;
    }

    @Override
    public Kennung getKennung()
    {
        return this.kennung;
    }

    @Override
    public void setKennung(Kennung kennung)
    {
        this.kennung = kennung;
    }

    @Override
    public boolean hasSameKennung(IKennung other)
    {
        return this.kennung.equals(other.getKennung());
    }

    @Override
    public String toString()
    {
        return this.anrede;
    }

}
