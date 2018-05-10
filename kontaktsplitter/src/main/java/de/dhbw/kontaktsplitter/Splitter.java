package de.dhbw.kontaktsplitter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.dhbw.kontaktsplitter.model.Geschlecht;
import de.dhbw.kontaktsplitter.model.Kontakt;
import de.dhbw.kontaktsplitter.model.Land;
import de.dhbw.kontaktsplitter.repository.AnredeRepository;
import de.dhbw.kontaktsplitter.repository.NamenszusatzRepository;
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
        // TODO
        return true;
    }

    private List<String> recognizeTitel(String input)
    {
        List<String> titelListe = new ArrayList<>();
        TitelRepository titelRepo = TitelRepository.instance();
        Iterator<String> iterator = titelRepo.getValues().stream().distinct().sorted((s1, s2) -> s2.compareTo(s1)).iterator();
        StringBuilder regex = new StringBuilder("(");
        while (iterator.hasNext())
        {
            regex.append(iterator.next().replaceAll("\\.", "\\\\."));
            if (iterator.hasNext())
            {
                regex.append("|");
            }
        }
        regex.append(")");
        Pattern pattern = Pattern.compile(regex.toString());
        Matcher matcher = pattern.matcher(input);
        while (matcher.find())
        {
            String titel = matcher.group();
            titelListe.add(titel);
            titelRepo.getLand(titel).ifPresent(this::setLand);
            titelRepo.getGeschlecht(titel).ifPresent(this::setGeschlecht);
        }
        return titelListe;
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

    private Optional<String> recognizeNamenszusatz(String input)
    {
        for (String namenszusatz : NamenszusatzRepository.instance().getValues())
        {
            if (input.contains(namenszusatz))
            {
                return Optional.of(namenszusatz);
            }
        }
        return Optional.empty();
    }

    private Optional<String> recognizeNachname(String input)
    {
        StringBuilder sb = new StringBuilder();
        this.recognizeNamenszusatz(input).ifPresent(nz -> sb.append(nz).append(" "));

        String[] split = input.split("\\s");
        sb.append(split[split.length - 1]);

        return Optional.of(sb.toString());
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
