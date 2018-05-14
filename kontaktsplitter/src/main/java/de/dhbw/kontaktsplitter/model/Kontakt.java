package de.dhbw.kontaktsplitter.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import de.dhbw.kontaktsplitter.repository.AnredeRepository;
import de.dhbw.kontaktsplitter.repository.GrussformelRepository;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { "input", "vorname", "nachname", "anrede", "titel", "geschlecht", "land", "grussformel", "briefanrede" })
public class Kontakt
{
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
        this.titel.set(FXCollections.observableArrayList(new ArrayList<>()));
    }

    public Kontakt(String nachname)
    {
        this.setNachname(nachname);
        this.titel.set(FXCollections.observableArrayList(new ArrayList<>()));
    }

    public void generateBriefanrede(Consumer<String> onFail)
    {
        StringBuilder sbFail = new StringBuilder();
        this.setDefaultKennung(sbFail::append);
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
                if (!"".equals(this.getVorname()))
                {
                    sb.append(this.getVorname()).append(" ");
                }
                sb.append(this.getNachname());
            }
        }
        this.setBriefanrede(sb.toString().trim());
        if (sbFail.length() > 0)
        {
            onFail.accept(sbFail.toString());
        }
    }

    public void generateAnrede()
    {
        AnredeRepository.instance().getValue(this.getLand(), this.getGeschlecht()).ifPresent(this::setAnrede);
    }

    public void generateGrussformel()
    {
        GrussformelRepository.instance().getValue(this.getLand(), this.getGeschlecht()).ifPresent(this::setGrussformel);
    }

    private void setDefaultKennung(Consumer<String> onFail)
    {
        StringBuilder sb = new StringBuilder();
        if (this.getLand() == null)
        {
            sb.append("Land konnte nicht erkannt werden. Default DE wird gesetzt.\n");
            this.setLand(Land.DE);
        }
        if (this.getGeschlecht() == null)
        {
            sb.append("Geschlecht konnte nicht erkannt werden. Default NONE wird gesetzt.\n");
            this.setGeschlecht(Geschlecht.NONE);
        }
        if (sb.length() > 0)
        {
            onFail.accept(sb.toString());
        }
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

    public List<String> getTitel()
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

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.getAnrede() == null) ? 0 : this.getAnrede().hashCode());
        result = prime * result + ((this.getGeschlecht() == null) ? 0 : this.getGeschlecht().hashCode());
        result = prime * result + ((this.getLand() == null) ? 0 : this.getLand().hashCode());
        result = prime * result + ((this.getNachname() == null) ? 0 : this.getNachname().hashCode());
        result = prime * result + ((this.getTitel() == null) ? 0 : this.getTitel().hashCode());
        result = prime * result + ((this.getVorname() == null) ? 0 : this.getVorname().hashCode());
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
        Kontakt other = (Kontakt) obj;
        if (this.getAnrede() == null)
        {
            if (other.getAnrede() != null)
            {
                return false;
            }
        }
        else
            if (!this.getAnrede().equals(other.getAnrede()))
            {
                return false;
            }
        if (this.getGeschlecht() == null)
        {
            if (other.getGeschlecht() != null)
            {
                return false;
            }
        }
        else
            if (!this.getGeschlecht().equals(other.getGeschlecht()))
            {
                return false;
            }
        if (this.getLand() == null)
        {
            if (other.getLand() != null)
            {
                return false;
            }
        }
        else
            if (!this.getLand().equals(other.getLand()))
            {
                return false;
            }
        if (this.getNachname() == null)
        {
            if (other.getNachname() != null)
            {
                return false;
            }
        }
        else
            if (!this.getNachname().equals(other.getNachname()))
            {
                return false;
            }
        if (this.getTitel() == null)
        {
            if (other.getTitel() != null)
            {
                return false;
            }
        }
        else
            if (!this.getTitel().equals(other.getTitel()))
            {
                return false;
            }
        if (this.getVorname() == null)
        {
            if (other.getVorname() != null)
            {
                return false;
            }
        }
        else
            if (!this.getVorname().equals(other.getVorname()))
            {
                return false;
            }
        return true;
    }

}
