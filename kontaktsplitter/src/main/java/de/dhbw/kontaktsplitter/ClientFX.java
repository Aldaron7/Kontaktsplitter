package de.dhbw.kontaktsplitter;

import de.dhbw.kontaktsplitter.view.RootController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ClientFX extends Application
{
    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClientFX.class.getResource("view/RootView.fxml"));
        AnchorPane root = loader.load();
        RootController controller = loader.getController();
        controller.setOwner(primaryStage);

        primaryStage.setTitle("Kontaktsplitter");
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
