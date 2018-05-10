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
import de.dhbw.kontaktsplitter.model.Kennung;
import de.dhbw.kontaktsplitter.model.Land;
import de.dhbw.kontaktsplitter.model.Titel;
import de.dhbw.kontaktsplitter.model.interfaces.IRepository;
import de.dhbw.kontaktsplitter.util.Pfade;

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

    public Optional<List<Kennung>> getKennung(String value)
    {
        List<Kennung> kennungen = this.getData().stream().filter(t -> t.getValue().equals(value)).map(Titel::getKennung).collect(Collectors.toList());
        return kennungen.isEmpty() ? Optional.empty() : Optional.of(kennungen);
    }

    @Override
    public Optional<Land> getLand(String value)
    {
        List<Land> laender = this.getData().stream().filter(t -> t.getValue().equals(value)).map(t -> t.getKennung().getLand()).distinct()
                        .collect(Collectors.toList());
        return laender.size() != 1 ? Optional.empty() : Optional.of(laender.get(0));
    }

    @Override
    public Optional<Geschlecht> getGeschlecht(String value)
    {
        List<Geschlecht> geschlechter = this.getData().stream().filter(t -> t.getValue().equals(value)).map(t -> t.getKennung().getGeschlecht()).distinct()
                        .collect(Collectors.toList());
        return geschlechter.size() != 1 ? Optional.empty() : Optional.of(geschlechter.get(0));
    }

    @Override
    public Collection<String> getValues()
    {
        return this.getData().stream().map(Titel::getValue).collect(Collectors.toSet());
    }

}
