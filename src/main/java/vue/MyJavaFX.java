package vue;

import controleur.Controleur;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;


public class MyJavaFX extends Application {

    @Override
    public void start(Stage stage) {
        VBoxRoot root = new VBoxRoot();
        Controleur controleur = VBoxRoot.getControleur();

        Scene scene = new Scene(root, 1180, 580);
        File[] fichiersCss = new File("css").listFiles();
        for (File fichier : fichiersCss) {
            System.out.println(fichier);
            scene.getStylesheets().add(fichier.toURI().toString());
        }

        stage.setOnCloseRequest(controleur::closeWindow);

        stage.setScene(scene);
        stage.setTitle("SAE APPLI - Théo Peyronnet / Mohamed Boulakhras");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}
