package de.dhbw.kontaktsplitter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import de.dhbw.kontaktsplitter.model.Geschlecht;
import de.dhbw.kontaktsplitter.model.Kontakt;
import de.dhbw.kontaktsplitter.model.Land;
import de.dhbw.kontaktsplitter.repository.AnredeRepository;
import de.dhbw.kontaktsplitter.repository.TitelRepository;

public class Splitter
{
    private Land       land;
    private Geschlecht geschlecht;

    public Kontakt split(String input)
    {
        Kontakt kontakt = new Kontakt();
        kontakt.setInput(input);
        if (this.validateInput(input))
        {
            kontakt.setTitel(this.recognizeTitel(input));
            this.recognizeAnrede(input).ifPresent(kontakt::setAnrede);
            this.recognizeNachname(input).ifPresent(kontakt::setNachname);
            this.recognizeVorname(input).ifPresent(kontakt::setVorname);

            kontakt.setGeschlecht(this.geschlecht);
            kontakt.setLand(this.land);
            kontakt.generateBriefanrede();

            this.geschlecht = null;
            this.land = null;
        }
        return kontakt;
    }

    private boolean validateInput(String input)
    {
        return true;
    }

    private List<String> recognizeTitel(String input)
    {
        List<String> titel = new ArrayList<>();
        TitelRepository titelRepo = TitelRepository.instance();
        for (String t : titelRepo.getValues())
        {
            String regex = t;
            int splitcount = input.split(regex).length;
            for (int i = 0; i < splitcount - 1; i++)
            {
                titelRepo.getLand(t).ifPresent(this::setLand);
                titelRepo.getGeschlecht(t).ifPresent(this::setGeschlecht);
                titel.add(t);
            }
        }
        return titel;
    }

    private Optional<String> recognizeAnrede(String input)
    {
        AnredeRepository anredeRepo = AnredeRepository.instance();
        for (String anrede : anredeRepo.getValues())
        {
            if (input.contains(anrede))
            {
                anredeRepo.getLand(anrede).ifPresent(this::setLand);
                anredeRepo.getGeschlecht(anrede).ifPresent(this::setGeschlecht);
                return Optional.of(anrede);
            }
        }
        return Optional.empty();
    }

    private Optional<String> recognizeVorname(String input)
    {
        input = this.removeAllAnreden(input).trim();
        input = this.removeAllTitel(input).trim();
        String[] split = input.split("\\s");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length - 1; i++)
        {
            String s = split[i].trim();
            if (s.matches("[A-Z].*"))
            {
                sb.append(s).append(" ");
            }
        }
        return Optional.of(sb.toString().trim());
    }

    private Optional<String> recognizeNachname(String input)
    {
        String[] split = input.split("\\s");
        return Optional.of(split[split.length - 1]);
    }

    private String removeAllTitel(String input)
    {
        Collection<String> titel = TitelRepository.instance().getValues();
        for (String t : titel)
        {
            input = input.replaceAll(t, "");
        }
        return input;
    }

    private String removeAllAnreden(String input)
    {
        Collection<String> anreden = AnredeRepository.instance().getValues();
        for (String anrede : anreden)
        {
            input = input.replaceAll(anrede, "");
        }
        return input;
    }

    public void setLand(Land land)
    {
        this.land = land;
    }

    public void setGeschlecht(Geschlecht geschlecht)
    {
        this.geschlecht = geschlecht;
    }

}
