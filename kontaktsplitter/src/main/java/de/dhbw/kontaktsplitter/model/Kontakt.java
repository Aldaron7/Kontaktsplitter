package de.dhbw.kontaktsplitter.model;

import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import de.dhbw.kontaktsplitter.repository.AnredeRepository;
import de.dhbw.kontaktsplitter.repository.GrussformelRepository;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Kontakt
{
    private UID                        uid;
    private StringProperty             input       = new SimpleStringProperty();
    private StringProperty             vorname     = new SimpleStringProperty();
    private StringProperty             nachname    = new SimpleStringProperty();
    private StringProperty             anrede      = new SimpleStringProperty();
    private ListProperty<String>       titel       = new SimpleListProperty<>();
    private ObjectProperty<Geschlecht> geschlecht  = new SimpleObjectProperty<>();
    private ObjectProperty<Land>       land        = new SimpleObjectProperty<>();
    private StringProperty             grussformel = new SimpleStringProperty();
    private StringProperty             briefanrede = new SimpleStringProperty();

    public Kontakt()
    {
        this.uid = new UID();
        this.titel.set(FXCollections.observableArrayList(new ArrayList<>()));
    }

    public Kontakt(String nachname)
    {
        this.uid = new UID();
        this.setNachname(nachname);
        this.titel.set(FXCollections.observableArrayList(new ArrayList<>()));
    }

    public void generateBriefanrede()
    {
        this.setDefaultKennung();
        if (this.getGrussformel() == null)
        {
            this.generateGrussformel();
        }
        if (this.getAnrede() == null)
        {
            this.generateAnrede();
        }

        StringBuilder sb = new StringBuilder();
        if (this.getGrussformel() != null && this.getAnrede() != null)
        {
            sb.append(this.getGrussformel()).append(" ").append(this.getAnrede()).append(" ");
            if (this.getGeschlecht() != Geschlecht.NONE)
            {
                for (String t : this.titel)
                {
                    sb.append(t).append(" ");
                }
            }
        }
        this.setBriefanrede(sb.toString().trim());
    }

    public void generateAnrede()
    {
        AnredeRepository.instance().getValue(this.getLand(), this.getGeschlecht()).ifPresent(this::setAnrede);
    }

    public void generateGrussformel()
    {
        GrussformelRepository.instance().getValue(this.getLand(), this.getGeschlecht()).ifPresent(this::setGrussformel);
    }

    private void setDefaultKennung()
    {
        this.setLand(this.getLand() == null ? Land.DE : this.getLand());
        this.setGeschlecht(this.getGeschlecht() == null ? Geschlecht.NONE : this.getGeschlecht());
    }

    public UID getUid()
    {
        return this.uid;
    }

    public StringProperty inputProperty()
    {
        return this.input;
    }

    public String getInput()
    {
        return this.inputProperty().get();
    }

    public void setInput(final String input)
    {
        this.inputProperty().set(input);
    }

    public StringProperty vornameProperty()
    {
        return this.vorname;
    }

    public String getVorname()
    {
        return this.vornameProperty().get();
    }

    public void setVorname(final String vorname)
    {
        this.vornameProperty().set(vorname);
    }

    public StringProperty nachnameProperty()
    {
        return this.nachname;
    }

    public String getNachname()
    {
        return this.nachnameProperty().get();
    }

    public void setNachname(final String nachname)
    {
        this.nachnameProperty().set(nachname);
    }

    public StringProperty anredeProperty()
    {
        return this.anrede;
    }

    public String getAnrede()
    {
        return this.anredeProperty().get();
    }

    public void setAnrede(final String anrede)
    {
        this.anredeProperty().set(anrede);
    }

    public ListProperty<String> titelProperty()
    {
        return this.titel;
    }

    public ObservableList<String> getTitel()
    {
        return this.titelProperty().get();
    }

    public void setTitel(final List<String> titel)
    {
        this.titelProperty().set(FXCollections.observableArrayList(titel));
    }

    public ObjectProperty<Geschlecht> geschlechtProperty()
    {
        return this.geschlecht;
    }

    public Geschlecht getGeschlecht()
    {
        return this.geschlechtProperty().get();
    }

    public void setGeschlecht(final Geschlecht geschlecht)
    {
        this.geschlechtProperty().set(geschlecht);
    }

    public ObjectProperty<Land> landProperty()
    {
        return this.land;
    }

    public Land getLand()
    {
        return this.landProperty().get();
    }

    public void setLand(final Land land)
    {
        this.landProperty().set(land);
    }

    public StringProperty grussformelProperty()
    {
        return this.grussformel;
    }

    public String getGrussformel()
    {
        return this.grussformelProperty().get();
    }

    public void setGrussformel(final String grussformel)
    {
        this.grussformelProperty().set(grussformel);
    }

    public StringProperty briefanredeProperty()
    {
        return this.briefanrede;
    }

    public String getBriefanrede()
    {
        return this.briefanredeProperty().get();
    }

    public void setBriefanrede(final String briefanrede)
    {
        this.briefanredeProperty().set(briefanrede);
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Kontakt [");
        // if (this.uid != null)
        // {
        // builder.append("uid=").append(this.uid).append(", ");
        // }
        if (this.input != null)
        {
            builder.append("input=").append(this.getInput()).append(", ");
        }
        if (this.vorname != null)
        {
            builder.append("vorname=").append(this.getVorname()).append(", ");
        }
        if (this.nachname != null)
        {
            builder.append("nachname=").append(this.getNachname()).append(", ");
        }
        if (this.anrede != null)
        {
            builder.append("anrede=").append(this.getAnrede()).append(", ");
        }
        if (this.titel != null)
        {
            builder.append("titel=").append(this.getTitel()).append(", ");
        }
        if (this.geschlecht != null)
        {
            builder.append("geschlecht=").append(this.getGeschlecht()).append(", ");
        }
        if (this.land != null)
        {
            builder.append("land=").append(this.getLand()).append(", ");
        }
        if (this.grussformel != null)
        {
            builder.append("grussformel=").append(this.getGrussformel()).append(", ");
        }
        if (this.briefanrede != null)
        {
            builder.append("briefanrede=").append(this.getBriefanrede());
        }
        builder.append("]");
        return builder.toString();
    }

}
