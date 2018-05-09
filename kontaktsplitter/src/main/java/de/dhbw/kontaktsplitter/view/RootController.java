package de.dhbw.kontaktsplitter.view;

import de.dhbw.kontaktsplitter.model.Geschlecht;
import de.dhbw.kontaktsplitter.model.Land;
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

    @FXML
    private void initialize()
    {
        this.geschlecht.getItems().setAll(Geschlecht.values());
        this.landkennung.getItems().setAll(Land.values());
    }

    @FXML
    private void handleSplitten()
    {

    }

    @FXML
    private void handleSpeichern()
    {

    }
}
