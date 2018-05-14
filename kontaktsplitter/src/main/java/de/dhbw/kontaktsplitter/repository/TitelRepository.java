package de.dhbw.kontaktsplitter.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.dhbw.kontaktsplitter.model.Geschlecht;
import de.dhbw.kontaktsplitter.model.Land;
import de.dhbw.kontaktsplitter.model.Titel;
import de.dhbw.kontaktsplitter.model.interfaces.IRepository;
import de.dhbw.kontaktsplitter.util.Pfade;

/**
 * @author mvr Regelt den Zugriff auf die persistierten Titel
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TitelRepository implements IRepository<Titel>
{
    private static TitelRepository instance;

    public static TitelRepository instance()
    {
        if (TitelRepository.instance == null)
        {
            TitelRepository.instance = new TitelRepository();
        }
        return TitelRepository.instance;
    }

    @XmlElement
    private Collection<Titel> titel = new ArrayList<>();

    private TitelRepository()
    {
    }

    /**
     * Laedt die Titel aus einer xml Datei
     */
    public void load()
    {
        try (BufferedReader reader = Files.newBufferedReader(Pfade.TITEL))
        {
            JAXBContext context = JAXBContext.newInstance(TitelRepository.class);
            Unmarshaller um = context.createUnmarshaller();
            TitelRepository titelRepository = (TitelRepository) um.unmarshal(reader);
            this.titel = titelRepository.getData();
        }
        catch (JAXBException | IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Speichert die Titel in einer xml Datei
     */
    public void save()
    {
        try (BufferedWriter writer = Files.newBufferedWriter(Pfade.TITEL))
        {
            JAXBContext context = JAXBContext.newInstance(TitelRepository.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(this, writer);
        }
        catch (JAXBException | IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Titel> getData()
    {
        if (this.titel.isEmpty())
        {
            this.load();
        }
        return this.titel;
    }

    public boolean add(Titel titel)
    {
        return this.getData().add(titel);
    }

    public boolean remove(Titel titel)
    {
        return this.getData().remove(titel);
    }

    @Override
    public boolean contains(String value)
    {
        return this.getData().stream().map(Titel::getValue).anyMatch(value::equals);
    }

    public boolean contains(Titel titel)
    {
        return this.getData().contains(titel);
    }

    @Override
    public Optional<Land> getLand(String value)
    {
        List<Land> laender = this.getData().stream().filter(t -> t.getValue().equals(value)).map(t -> t.getKennung().getLand()).distinct()
                        .filter(l -> l != null).collect(Collectors.toList());
        return laender.size() != 1 ? Optional.empty() : Optional.of(laender.get(0));
    }

    @Override
    public Optional<Geschlecht> getGeschlecht(String value)
    {
        List<Geschlecht> geschlechter = this.getData().stream().filter(t -> t.getValue().equals(value)).map(t -> t.getKennung().getGeschlecht()).distinct()
                        .filter(g -> g != null).collect(Collectors.toList());
        return geschlechter.size() != 1 ? Optional.empty() : Optional.of(geschlechter.get(0));
    }

    @Override
    public Collection<String> getValues()
    {
        return this.getData().stream().map(Titel::getValue).distinct().sorted((s1, s2) -> s2.compareTo(s1)).collect(Collectors.toList());
    }

}
