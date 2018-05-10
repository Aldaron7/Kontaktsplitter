package de.dhbw.kontaktsplitter.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.dhbw.kontaktsplitter.util.Pfade;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Kontakte
{
    @XmlElement(name = "kontakt")
    private List<Kontakt> kontakte = new ArrayList<>();

    public Kontakte()
    {
    }

    public List<Kontakt> getKontakte()
    {
        return this.kontakte;
    }

    public void setKontakte(List<Kontakt> kontakte)
    {
        this.kontakte = kontakte;
    }

    public void add(Kontakt kontakt)
    {
        this.kontakte.removeIf(kontakt::equals);
        this.kontakte.removeIf(k -> k.getInput().equals(kontakt.getInput()));
        this.kontakte.add(kontakt);
    }

    public void remove(Kontakt kontakt)
    {
        this.kontakte.remove(kontakt);
    }

    public void save()
    {
        try (BufferedWriter writer = Files.newBufferedWriter(Pfade.KONTAKTE))
        {
            JAXBContext context = JAXBContext.newInstance(Kontakte.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(this, writer);
        }
        catch (JAXBException | IOException e)
        {
            e.printStackTrace();
        }
    }

    public void load()
    {
        if (Files.exists(Pfade.KONTAKTE))
        {
            try (BufferedReader reader = Files.newBufferedReader(Pfade.KONTAKTE))
            {
                JAXBContext context = JAXBContext.newInstance(Kontakte.class);
                Unmarshaller um = context.createUnmarshaller();
                Kontakte kontakte = (Kontakte) um.unmarshal(reader);
                this.kontakte = kontakte.getKontakte();
            }
            catch (JAXBException | IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}
