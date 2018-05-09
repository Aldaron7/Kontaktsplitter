package de.dhbw.kontaktsplitter.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

import de.dhbw.kontaktsplitter.model.interfaces.IKennung;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Titel implements IKennung
{
    @XmlPath(".")
    private Kennung kennung;
    private String  titel;

    public Titel()
    {
    }

    public Titel(Kennung kennung, String titel)
    {
        this.kennung = kennung;
        this.titel = titel;
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
    public String getValue()
    {
        return this.titel;
    }

    @Override
    public void setValue(String titel)
    {
        this.titel = titel;
    }

    @Override
    public boolean hasSameKennung(IKennung other)
    {
        return this.kennung.equals(other.getKennung());
    }

    @Override
    public String toString()
    {
        return this.kennung + "[" + this.titel + "]";
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.kennung == null) ? 0 : this.kennung.hashCode());
        result = prime * result + ((this.titel == null) ? 0 : this.titel.hashCode());
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
        Titel other = (Titel) obj;
        if (this.kennung == null)
        {
            if (other.kennung != null)
            {
                return false;
            }
        }
        else
            if (!this.kennung.equals(other.kennung))
            {
                return false;
            }
        if (this.titel == null)
        {
            if (other.titel != null)
            {
                return false;
            }
        }
        else
            if (!this.titel.equals(other.titel))
            {
                return false;
            }
        return true;
    }

}
