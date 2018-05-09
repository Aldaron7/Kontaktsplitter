package de.dhbw.kontaktsplitter.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Assert;
import org.junit.Test;

import de.dhbw.kontaktsplitter.repository.TitelGenerator;
import de.dhbw.kontaktsplitter.repository.TitelRepository;

public class TestTitelToXml
{
    @Test
    public void testToXml() throws URISyntaxException
    {
        Path path = Paths.get(ClassLoader.getSystemResource("titel.xml").toURI());
        System.out.println(path.toAbsolutePath());
        this.readFile(path);

        // this.writeFile(path);

    }

    @SuppressWarnings("unused")
    private void writeFile(Path path)
    {
        // geschrieben immer in target/test-classes/titel.xml
        try (BufferedWriter writer = Files.newBufferedWriter(path))
        {
            JAXBContext context = org.eclipse.persistence.jaxb.JAXBContext.newInstance(TitelRepository.class);
            System.out.println("jaxbContext is=" + context.toString());
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            TitelRepository titel = TitelGenerator.getTitel();
            System.out.println(titel.getData());
            m.marshal(titel, writer);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (JAXBException e)
        {
            e.printStackTrace();
        }
    }

    private void readFile(Path path)
    {
        // Executed als JUnit Test path = ...target/test-classes/titel.xml
        // Executed als maven build path = ...src/test/resources/titel.xml
        TitelRepository titel = null;
        try (BufferedReader reader = Files.newBufferedReader(path))
        {
            // reader.lines().forEach(System.out::println);
            // System.out.println();
            JAXBContext context = JAXBContext.newInstance(TitelRepository.class);
            System.out.println("jaxbContext is=" + context.toString());
            Unmarshaller um = context.createUnmarshaller();
            titel = (TitelRepository) um.unmarshal(reader);
            System.out.println(titel.getData());
        }
        catch (IOException | JAXBException e1)
        {
            e1.printStackTrace();
        }
        Assert.assertNotNull(titel);
    }
}
