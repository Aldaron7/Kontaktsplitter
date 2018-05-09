package de.dhbw.kontaktsplitter.model;

public class Kennung
{
    private Land       land;
    private Geschlecht geschlecht;

    public Kennung()
    {
    }

    public Kennung(Land land, Geschlecht geschlecht)
    {
        this.land = land;
        this.geschlecht = geschlecht;
    }

    public Land getLand()
    {
        return this.land;
    }

    public void setLand(Land land)
    {
        this.land = land;
    }

    public Geschlecht getGeschlecht()
    {
        return this.geschlecht;
    }

    public void setGeschlecht(Geschlecht geschlecht)
    {
        this.geschlecht = geschlecht;
    }

    @Override
    public String toString()
    {
        return this.land + "_" + this.geschlecht;
    }

    public static Kennung valueOf(String value)
    {
        String[] split = value.split("_");
        Land land = Land.valueOf(split[0]);
        Geschlecht geschlecht = Geschlecht.valueOf(split[1]);
        return new Kennung(land, geschlecht);
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.geschlecht == null) ? 0 : this.geschlecht.hashCode());
        result = prime * result + ((this.land == null) ? 0 : this.land.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (this.getClass() != obj.getClass())
        {
            return false;
        }
        Kennung other = (Kennung) obj;
        if (this.geschlecht != other.geschlecht)
        {
            return false;
        }
        if (this.land != other.land)
        {
            return false;
        }
        return true;
    }

}
