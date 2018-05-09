package de.dhbw.kontaktsplitter.model;

import java.rmi.server.UID;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Kontakt
{
    private UID                         uid;
    private StringProperty              input       = new SimpleStringProperty();
    private StringProperty              vorname     = new SimpleStringProperty();
    private StringProperty              nachname    = new SimpleStringProperty();
    private ObjectProperty<Anrede>      anrede      = new SimpleObjectProperty<>();
    private ListProperty<Titel>         titel       = new SimpleListProperty<>();
    private ObjectProperty<Geschlecht>  geschlecht  = new SimpleObjectProperty<>();
    private ObjectProperty<Land>        land        = new SimpleObjectProperty<>();
    private ObjectProperty<Grussformel> grussformel = new SimpleObjectProperty<>();
    private StringProperty              briefanrede = new SimpleStringProperty();

    public Kontakt(String nachname)
    {
        this.uid = new UID();
        this.setNachname(nachname);
        this.titel.set(FXCollections.observableArrayList(new ArrayList<>()));
    }

    public UID getUid()
    {
        return this.uid;
    }

    public final StringProperty inputProperty()
    {
        return this.input;
    }

    public final String getInput()
    {
        return this.inputProperty().get();
    }

    public final void setInput(final String input)
    {
        this.inputProperty().set(input);
    }

    public final StringProperty vornameProperty()
    {
        return this.vorname;
    }

    public final String getVorname()
    {
        return this.vornameProperty().get();
    }

    public final void setVorname(final String vorname)
    {
        this.vornameProperty().set(vorname);
    }

    public final StringProperty nachnameProperty()
    {
        return this.nachname;
    }

    public final String getNachname()
    {
        return this.nachnameProperty().get();
    }

    public final void setNachname(final String nachname)
    {
        this.nachnameProperty().set(nachname);
    }

    public final ObjectProperty<Anrede> anredeProperty()
    {
        return this.anrede;
    }

    public final Anrede getAnrede()
    {
        return this.anredeProperty().get();
    }

    public final void setAnrede(final Anrede anrede)
    {
        this.anredeProperty().set(anrede);
    }

    public final ListProperty<Titel> titelProperty()
    {
        return this.titel;
    }

    public final ObjectProperty<Geschlecht> geschlechtProperty()
    {
        return this.geschlecht;
    }

    public final Geschlecht getGeschlecht()
    {
        return this.geschlechtProperty().get();
    }

    public final void setGeschlecht(final Geschlecht geschlecht)
    {
        this.geschlechtProperty().set(geschlecht);
    }

    public final ObjectProperty<Land> landProperty()
    {
        return this.land;
    }

    public final Land getLand()
    {
        return this.landProperty().get();
    }

    public final void setLand(final Land land)
    {
        this.landProperty().set(land);
    }

    public final ObjectProperty<Grussformel> grussformelProperty()
    {
        return this.grussformel;
    }

    public final Grussformel getGrussformel()
    {
        return this.grussformelProperty().get();
    }

    public final void setGrussformel(final Grussformel grussformel)
    {
        this.grussformelProperty().set(grussformel);
    }

    public final StringProperty briefanredeProperty()
    {
        return this.briefanrede;
    }

    public final String getBriefanrede()
    {
        return this.briefanredeProperty().get();
    }

    public final void setBriefanrede(final String briefanrede)
    {
        this.briefanredeProperty().set(briefanrede);
    }

}
