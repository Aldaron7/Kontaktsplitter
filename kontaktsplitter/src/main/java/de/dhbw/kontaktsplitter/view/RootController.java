package de.dhbw.kontaktsplitter.view;

import de.dhbw.kontaktsplitter.Splitter;
import de.dhbw.kontaktsplitter.model.Geschlecht;
import de.dhbw.kontaktsplitter.model.Kontakt;
import de.dhbw.kontaktsplitter.model.Kontakte;
import de.dhbw.kontaktsplitter.model.Land;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class RootController
{
    @FXML
    private TextField             eingabe;
    @FXML
    private TextField             anrede;
    @FXML
    private ListView<String>      titel;
    @FXML
    private TextField             vorname;
    @FXML
    private TextField             nachname;
    @FXML
    private ChoiceBox<Geschlecht> geschlecht;
    @FXML
    private ChoiceBox<Land>       landkennung;
    @FXML
    private TextField             briefanrede;

    private Kontakt               kontakt;
    private Splitter              splitter               = new Splitter();
    @SuppressWarnings("rawtypes")
    private ChangeListener        regenerateKontaktdaten = (obs, o, n) -> {
                                                             this.regenerateKontaktdaten();
                                                         };

    private void regenerateKontaktdaten()
    {
        this.kontakt.generateAnrede();
        this.kontakt.generateGrussformel();
        this.kontakt.generateBriefanrede();
    }

    @FXML
    @SuppressWarnings("unchecked")
    private void initialize()
    {
        this.kontakt = new Kontakt();
        this.bind(this.kontakt);
        this.geschlecht.getItems().setAll(Geschlecht.values());
        this.geschlecht.getSelectionModel().selectedItemProperty().addListener(this.regenerateKontaktdaten);
        this.landkennung.getItems().setAll(Land.values());
        this.landkennung.getSelectionModel().selectedItemProperty().addListener(this.regenerateKontaktdaten);
        this.eingabe.focusedProperty().addListener((ob, o, n) -> {
            if (n == false)
            {
                this.handleSplitten();
            }
        });
    }

    @FXML
    private void handleSplitten()
    {
        Kontakt kontakt = this.splitter.split(this.eingabe.getText());
        this.unbind(this.kontakt);
        this.kontakt = kontakt;
        this.bind(this.kontakt);
    }

    @FXML
    private void handleKorrektur()
    {
        this.kontakt.generateBriefanrede();
    }

    @FXML
    private void handleTitelToLeft()
    {
        int selectedIndex = this.titel.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0)
        {
            ObservableList<String> items = this.titel.getItems();
            String item = items.remove(selectedIndex);
            items.add(selectedIndex - 1, item);
            this.kontakt.generateBriefanrede();
        }
    }

    @FXML
    private void handleTitelToRight()
    {
        int selectedIndex = this.titel.getSelectionModel().getSelectedIndex();
        ObservableList<String> items = this.titel.getItems();
        if (selectedIndex < items.size() - 1)
        {
            String item = items.remove(selectedIndex);
            items.add(selectedIndex + 1, item);
            this.kontakt.generateBriefanrede();
        }

    }

    @FXML
    private void handleSpeichern()
    {
        Kontakte kontakte = new Kontakte();
        kontakte.load();
        kontakte.add(this.kontakt);
        kontakte.save();
    }

    private <T> void bind(Kontakt k)
    {
        Bindings.bindBidirectional(this.eingabe.textProperty(), k.inputProperty());
        Bindings.bindBidirectional(this.anrede.textProperty(), k.anredeProperty());
        Bindings.bindBidirectional(this.titel.itemsProperty(), k.titelProperty());
        Bindings.bindBidirectional(this.vorname.textProperty(), k.vornameProperty());
        Bindings.bindBidirectional(this.nachname.textProperty(), k.nachnameProperty());
        Bindings.bindBidirectional(this.geschlecht.valueProperty(), k.geschlechtProperty());
        Bindings.bindBidirectional(this.landkennung.valueProperty(), k.landProperty());
        Bindings.bindBidirectional(this.briefanrede.textProperty(), k.briefanredeProperty());
    }

    private <T> void unbind(Kontakt k)
    {
        Bindings.unbindBidirectional(this.eingabe.textProperty(), k.inputProperty());
        Bindings.unbindBidirectional(this.anrede.textProperty(), k.anredeProperty());
        Bindings.unbindBidirectional(this.titel.itemsProperty(), k.titelProperty());
        Bindings.unbindBidirectional(this.vorname.textProperty(), k.vornameProperty());
        Bindings.unbindBidirectional(this.nachname.textProperty(), k.nachnameProperty());
        Bindings.unbindBidirectional(this.geschlecht.valueProperty(), k.geschlechtProperty());
        Bindings.unbindBidirectional(this.landkennung.valueProperty(), k.landProperty());
        Bindings.unbindBidirectional(this.briefanrede.textProperty(), k.briefanredeProperty());

    }
}
