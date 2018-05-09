package de.dhbw.kontaktsplitter.model.interfaces;

import de.dhbw.kontaktsplitter.model.Kennung;

public interface IKennung
{
    public void setKennung(Kennung kennung);

    public Kennung getKennung();

    public boolean hasSameKennung(IKennung other);

    public void setValue(String value);

    public String getValue();
}
