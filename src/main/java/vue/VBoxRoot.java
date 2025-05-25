package vue;

import controleur.Controleur;
import javafx.scene.layout.VBox;
import modele.LectureScenarios;
import modele.Scenario;

import java.io.File;
import java.io.IOException;

public class VBoxRoot extends VBox {
    private static Controleur controleur;
    private static MenuScenarios menuBarScenarios;
    private static HBoxContainer hBoxContainer;
    private static Scenario scenario;

    public VBoxRoot() {
        super(20);
        controleur = new Controleur();
        scenario = new Scenario();
        menuBarScenarios = new MenuScenarios(controleur);
        hBoxContainer = new HBoxContainer(controleur);


        this.getChildren().addAll(menuBarScenarios, hBoxContainer);
    }

    public static Controleur getControleur() {
        return controleur;
    }
    public static HBoxContainer getHBoxContainer() {
        return hBoxContainer;
    }

    public static Scenario getScenario() {
        return scenario;
    }



}
