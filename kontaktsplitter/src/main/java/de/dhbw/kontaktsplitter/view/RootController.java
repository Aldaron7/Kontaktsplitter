package de.dhbw.kontaktsplitter.view;

import java.util.Optional;
import java.util.function.Consumer;

import de.dhbw.kontaktsplitter.Splitter;
import de.dhbw.kontaktsplitter.model.Geschlecht;
import de.dhbw.kontaktsplitter.model.Kontakt;
import de.dhbw.kontaktsplitter.model.Kontakte;
import de.dhbw.kontaktsplitter.model.Land;
import de.dhbw.kontaktsplitter.model.Titel;
import de.dhbw.kontaktsplitter.repository.TitelRepository;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Window;

public class RootController
{
    @FXML
    private TextField                      eingabe;
    @FXML
    private TextField                      anrede;
    @FXML
    private ListView<String>               titel;
    @FXML
    private TextField                      vorname;
    @FXML
    private TextField                      nachname;
    @FXML
    private ChoiceBox<Geschlecht>          geschlecht;
    @FXML
    private ChoiceBox<Land>                landkennung;
    @FXML
    private TextField                      briefanrede;

    private Window                         owner;
    private Kontakt                        kontakt;
    private Splitter                       splitter               = new Splitter();
    @SuppressWarnings("rawtypes")
    private ChangeListener                 regenerateKontaktdaten = (obs, o, n) -> {
                                                                      this.regenerateKontaktdaten();
                                                                  };
    private EventHandler<? super KeyEvent> korrekturListener      = (key) -> {
                                                                      if (key.getCode().equals(KeyCode.TAB))
                                                                      {
                                                                          this.handleKorrektur();
                                                                      }
                                                                  };
    private EventHandler<? super KeyEvent> splittenListener       = (key) -> {
                                                                      if (key.getCode().equals(KeyCode.TAB))
                                                                      {
                                                                          this.handleSplitten();
                                                                      }
                                                                  };

    public void setOwner(Window owner)
    {
        this.owner = owner;
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

        this.eingabe.setOnKeyPressed(this.splittenListener);
        this.vorname.setOnKeyPressed(this.korrekturListener);
        this.nachname.setOnKeyPressed(this.korrekturListener);
    }

    @FXML
    private void handleSplitten()
    {
        Kontakt kontakt = this.splitter.split(this.eingabe.getText(), this::handleError);
        this.unbind(this.kontakt);
        this.kontakt = kontakt;
        this.bind(this.kontakt);
    }

    @FXML
    private void handleKorrektur()
    {
        this.kontakt.generateBriefanrede(this::handleError);
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
            this.kontakt.generateBriefanrede(this::handleError);
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
            this.kontakt.generateBriefanrede(this::handleError);
        }
    }

    @FXML
    private void handleSpeichern()
    {
        StringBuilder sb = new StringBuilder();
        if (this.validateKontakt(sb::append))
        {
            this.saveKontakt();
        }
        else
        {
            sb.append("Trotzdem Speichern?");
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setContentText(sb.toString());
            alert.initOwner(this.owner);
            alert.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK)
                {
                    this.saveKontakt();
                }
            });
        }
    }

    private void saveKontakt()
    {
        Kontakte kontakte = new Kontakte();
        kontakte.load();
        kontakte.add(this.kontakt);
        kontakte.save();
    }

    @FXML
    private void handleAddTitel()
    {
        TitelRepository titelRepo = TitelRepository.instance();
        AddTitelDialog addTitel = new AddTitelDialog();
        addTitel.initOwner(this.owner);
        Optional<Titel> result = addTitel.showAndWait();
        if (result.isPresent())
        {
            Titel titel = result.get();
            if (!titelRepo.contains(titel))
            {
                titelRepo.load();
                titelRepo.add(titel);
                titelRepo.save();
            }
            else
            {
                this.handleError("Der Titel " + titel + " ist bereits vorhanden.");
            }
        }
    }

    private void handleError(String message)
    {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setHeaderText("Achtung");
        alert.setContentText(message);
        alert.initOwner(this.owner);
        alert.getDialogPane().setPrefWidth(500);
        alert.showAndWait();
    }

    private void regenerateKontaktdaten()
    {
        this.kontakt.generateAnrede();
        this.kontakt.generateGrussformel();
        this.kontakt.generateBriefanrede(this::handleError);
    }

    private boolean validateKontakt(Consumer<String> onFail)
    {
        StringBuilder sb = new StringBuilder();
        if (this.kontakt.getInput() == null || this.kontakt.getInput().trim().equals(""))
        {
            sb.append("Input konnte nicht ermittelt werden.\n");
        }
        if (this.kontakt.getNachname() == null || this.kontakt.getNachname().trim().equals(""))
        {
            sb.append("Nachname konnte nicht erkannt werden.\n");
        }
        if (this.kontakt.getVorname() == null || this.kontakt.getVorname().trim().equals(""))
        {
            sb.append("Vorname konnte nicht erkannt werden.\n");
        }
        if (this.kontakt.getAnrede() == null || this.kontakt.getAnrede().trim().equals(""))
        {
            sb.append("Anrede konnte nicht erkannt oder erzeugt werden.\n");
        }
        if (this.kontakt.getGrussformel() == null || this.kontakt.getGrussformel().trim().equals(""))
        {
            sb.append("Grussformel konnte nicht erzeugt werden.\n");
        }
        if (this.kontakt.getGeschlecht() == null)
        {
            sb.append("Geschlecht konnte nicht erkannt werden.\n");
        }
        if (this.kontakt.getLand() == null)
        {
            sb.append("Land konnte nicht erkannt werden.\n");
        }
        if (this.kontakt.getBriefanrede() == null || this.kontakt.getBriefanrede().trim().equals(""))
        {
            sb.append("Briefanrede konnte nicht erzeugt werden.\n");
        }
        if (sb.length() > 0)
        {
            onFail.accept(sb.toString());
            return false;
        }
        return true;

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
