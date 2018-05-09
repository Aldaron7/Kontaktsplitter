package de.dhbw.kontaktsplitter.test;

import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;

import de.dhbw.kontaktsplitter.repository.TitelRepository;

public class TestTitelRepository
{
    @Test
    public void testLoadTitel() throws URISyntaxException
    {
        TitelRepository titelRepository = TitelRepository.instance();
        Assert.assertTrue(titelRepository.getData().size() > 0);
        System.out.println(titelRepository.getData());
        System.out.println();
        titelRepository.save();

    }
}
