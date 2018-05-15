package de.dhbw.kontaktsplitter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.dhbw.kontaktsplitter.model.Geschlecht;
import de.dhbw.kontaktsplitter.model.Kontakt;
import de.dhbw.kontaktsplitter.model.Land;
import de.dhbw.kontaktsplitter.repository.AnredeRepository;
import de.dhbw.kontaktsplitter.repository.NamenszusatzRepository;
import de.dhbw.kontaktsplitter.repository.TitelRepository;

/**
 * @author mvr Splittet einen Eingabestring in die Bestandteile eines Kontaktes
 *
 */
public class Splitter
{
    private Land       land;
    private Geschlecht geschlecht;

    /**
     * Splitted den Eingabestring falls moeglich in Anrede, Titel, Vorname und Nachname. Erkennt falls moeglich das
     * Geschlecht und das Land aus den Daten und generiert die Grussformel und die Briefanrede.
     * 
     * @param input
     *            Eingabestring
     * @param onFail
     *            Consumer fuer die Fehlermeldung
     * @return Einen neuen Kontakt, generiert aus dem Eingabestring
     */
    public Kontakt split(String input, Consumer<String> onFail)
    {
        StringBuilder sbErrorMessage = new StringBuilder();
        Kontakt kontakt = new Kontakt();
        kontakt.setInput(input);
        if (this.validateInput(input, sbErrorMessage::append))
        {
            kontakt.setTitel(this.recognizeTitel(input));
            this.recognizeAnrede(input).ifPresent(kontakt::setAnrede);
            this.recognizeNachname(input).ifPresent(kontakt::setNachname);
            this.recognizeVorname(input).ifPresent(kontakt::setVorname);

            kontakt.setGeschlecht(this.geschlecht);
            kontakt.setLand(this.land);
            kontakt.generateBriefanrede(sbErrorMessage::append);

            this.geschlecht = null;
            this.land = null;
        }
        if (sbErrorMessage.length() > 0)
        {
            onFail.accept(sbErrorMessage.toString());
        }
        return kontakt;
    }

    /**
     * Validiert den Eingabestring
     * 
     * @param input
     *            Eingabestring
     * @param onFail
     *            Consumer fuer die Fehlermeldungen
     * @return true, wenn der Eingabestring valide ist
     */
    public boolean validateInput(String input, Consumer<String> onFail)
    {
        if (input == null || input.length() == 0)
        {
            return false;
        }
        if (input.matches(".*\\d.*"))
        {
            onFail.accept("Es sind keine Ziffern erlaubt.\n");
            return false;
        }
        if (input.matches(".*,.*"))
        {
            onFail.accept("Mit Komma separierte Namen werden evtl. nicht korrekt erkannt.\n");
        }
        return true;
    }

    /**
     * Erkennt die im Eingabestring vorkommenden Titel durch vergleich mit den persistierten Titeln.
     * 
     * @param input
     *            Eingabestring
     * @return Eine Liste aus den erkannten Titeln
     */
    public List<String> recognizeTitel(String input)
    {
        List<String> titelListe = new ArrayList<>();
        TitelRepository titelRepo = TitelRepository.instance();
        Iterator<String> iterator = titelRepo.getValues().iterator();
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

    /**
     * Erkennt die Anrede aus dem Eingabestring falls moeglich
     * 
     * @param input
     *            Eingabestring
     * @return Optional mit der Anrede, falls diese erkannt werden konnte, ansonsten einen leeren Optinal
     */
    public Optional<String> recognizeAnrede(String input)
    {
        AnredeRepository anredeRepo = AnredeRepository.instance();
        for (String anrede : anredeRepo.getValues())
        {
            String regex = ".*\\b" + anrede.replaceAll("\\.", "\\\\.") + "\\b.*";
            if (input.matches(regex))
            {
                anredeRepo.getLand(anrede).ifPresent(this::setLand);
                anredeRepo.getGeschlecht(anrede).ifPresent(this::setGeschlecht);
                return Optional.of(anrede);
            }
        }
        return Optional.empty();
    }

    /**
     * Erkennt den Vornamen aus dem Eingabestring falls moeglich
     * 
     * @param input
     *            Eingabestring
     * @return Optional mit dem erkannten Vornamen
     */
    public Optional<String> recognizeVorname(String input)
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

    /**
     * Erkennt den Namenszusatz aus dem Eingabestring, falls dieser im Eingabestring und in den persisterten
     * Namenszusaetzen vorhanden ist.
     * 
     * @param input
     *            Eingabestring
     * @return Optional mit dem Namenszusatz, falls dieser erkannt wurd, ansonsten ein leeres Optional
     */
    public Optional<String> recognizeNamenszusatz(String input)
    {
        for (String namenszusatz : NamenszusatzRepository.instance().getValues())
        {
            String regex = ".*\\b" + namenszusatz.replaceAll("\\.", "\\\\.") + "\\b.*";
            if (input.matches(regex))
            {
                return Optional.of(namenszusatz);
            }
        }
        return Optional.empty();
    }

    /**
     * Erkennt den Nachnamen aus dem Eingabestring falls moeglich.
     * 
     * @param input
     *            Eingabestring
     * @return Optional mit dem erkannten Nachnamen
     */
    public Optional<String> recognizeNachname(String input)
    {
        StringBuilder sb = new StringBuilder();
        this.recognizeNamenszusatz(input).ifPresent(nz -> sb.append(nz).append(" "));

        String[] split = input.split("\\s");
        sb.append(split[split.length - 1]);

        return Optional.of(sb.toString());
    }

    /**
     * Entfernt alle bekannten Titel aus dem Eingabestring
     * 
     * @param input
     *            Eingabestring
     * @return Eingabestring ohne erkannte Titel
     */
    public String removeAllTitel(String input)
    {
        Collection<String> titel = TitelRepository.instance().getValues();
        for (String t : titel)
        {
            input = input.replaceAll(t, "");
        }
        return input;
    }

    /**
     * Entfernt alle bekannten Anreden aus dem Eingabestring.
     * 
     * @param input
     *            Eingabestring
     * @return Eingabestring ohne die erkannten Anreden
     */
    public String removeAllAnreden(String input)
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
