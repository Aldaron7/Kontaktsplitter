package de.dhbw.kontaktsplitter.view;

import java.io.IOException;

import de.dhbw.kontaktsplitter.ClientFX;
import de.dhbw.kontaktsplitter.model.Geschlecht;
import de.dhbw.kontaktsplitter.model.Kennung;
import de.dhbw.kontaktsplitter.model.Land;
import de.dhbw.kontaktsplitter.model.Titel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

public class AddTitelDialog extends Dialog<Titel>
{
    public AddTitelDialog()
    {
        try
        {
            DialogPane dialogPane = FXMLLoader.load(ClientFX.class.getResource("view/AddTitelView.fxml"));

            dialogPane.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);

            final TextField titel = (TextField) dialogPane.lookup("#titel");
            titel.setPromptText("Titel");

            @SuppressWarnings("unchecked")
            final ChoiceBox<Geschlecht> geschlecht = (ChoiceBox<Geschlecht>) dialogPane.lookup("#geschlecht");
            geschlecht.setItems(FXCollections.observableArrayList(Geschlecht.values()));
            geschlecht.getItems().add(0, null);

            @SuppressWarnings("unchecked")
            final ChoiceBox<Land> land = (ChoiceBox<Land>) dialogPane.lookup("#land");
            land.setItems(FXCollections.observableArrayList(Land.values()));
            land.getItems().add(0, null);

            Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
            okButton.setDisable(true);
            okButton.setText("Speichern");

            Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
            cancelButton.setText("Abbrechen");

            titel.textProperty().addListener((obs, o, n) -> okButton.setDisable(n.trim().isEmpty()));

            Platform.runLater(() -> titel.requestFocus());

            this.setResultConverter((button) -> {
                ButtonData data = button == null ? null : button.getButtonData();
                return data == ButtonData.OK_DONE ? new Titel(new Kennung(land.getSelectionModel().getSelectedItem(),
                                                                          geschlecht.getSelectionModel().getSelectedItem()),
                                                              titel.getText())
                                                  : null;
            });

            this.setDialogPane(dialogPane);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
