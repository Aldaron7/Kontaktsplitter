package de.dhbw.kontaktsplitter.model;

import de.dhbw.kontaktsplitter.model.interfaces.IKennung;

public class Grussformel implements IKennung
{
    private Kennung kennung;
    private String  grussformel;

    public Grussformel(Kennung kennung, String grussformel)
    {
        this.kennung = kennung;
        this.grussformel = grussformel;
    }

    @Override
    public String getValue()
    {
        return this.grussformel;
    }

    @Override
    public void setValue(String grussformel)
    {
        this.grussformel = grussformel;
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
        return this.grussformel;
    }

}
