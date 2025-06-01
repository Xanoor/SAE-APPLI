package vue;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;


public class MyJavaFX extends Application {

    @Override
    public void start(Stage stage) {
        VBox root = new VBoxRoot();

        Scene scene = new Scene(root, 1180, 580);
        File[] fichiersCss = new File("css").listFiles();
        for (File fichier : fichiersCss) {
            scene.getStylesheets().add(fichier.toURI().toString());
        }

        stage.setScene(scene);
        stage.setTitle("SAE APPLI - Théo Peyronnet / Mohamed Boulakhras");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}
