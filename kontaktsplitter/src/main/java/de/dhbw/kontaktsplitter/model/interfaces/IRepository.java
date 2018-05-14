package de.dhbw.kontaktsplitter.model.interfaces;

import java.util.Collection;
import java.util.Optional;

import de.dhbw.kontaktsplitter.model.Geschlecht;
import de.dhbw.kontaktsplitter.model.Land;

public interface IRepository<T>
{
    /**
     * Gibt die Datenwerte aus dem Repository zurueck.
     * 
     * @return Collection der Datenwerte
     */
    public Collection<T> getData();

    /**
     * Gibt die String Repraesentationen der Daten des Repositories zurueck.
     * 
     * @return Collection der String Werte der Daten
     */
    public Collection<String> getValues();

    /**
     * Checkt, ob ein Wert im Repository vorhanden ist anhand seiner String Repraesentation
     * 
     * @param value
     *            String Repraesentation des Datensatzes
     * @return true, wenn der Wert in den Daten vorhanden ist
     */
    public boolean contains(String value);

    /**
     * Gibt das Land zu der String Repraesentation des Datensatzes zurueck
     * 
     * @param value
     *            String Repraesentation des Datensatzes
     * @return Optional mit dem Land des Datensatzes, falls vorhanden, ansonten ein leeres Optional
     */
    public Optional<Land> getLand(String value);

    /**
     * Gibt das Geschlecht zu der String Repraesentation des Datensatzes zurueck
     * 
     * @param value
     *            String Repraesentation des Datensatzes
     * @return Optional mit dem Geschlecht des Datensatzes, falls vorhanden, ansonsten ein leeres Optional
     */
    public Optional<Geschlecht> getGeschlecht(String value);
}
