package vue;

import controleur.Controleur;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import modele.LectureScenarios;
import modele.Scenario;

import java.io.File;
import java.io.IOException;

public class HBoxContainer extends HBox {
    private Scenario scenario;
    private TableViewTransactions tableViewTransactions;
    private ScenarioEditor scenarioEditor;

    public HBoxContainer(Controleur controleur) {
        scenario = VBoxRoot.getScenario();
        File fichier = new File("data/scenarios/scenario_0.txt");
        try {
            scenario = LectureScenarios.lectureScenarios(fichier);
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture scenario");
        }

        //On créer la table après avoir chargé le premier scénario.
        scenarioEditor = new ScenarioEditor(controleur);
        tableViewTransactions = new TableViewTransactions(controleur);

        this.getChildren().addAll(tableViewTransactions, scenarioEditor);
    }

    public TableViewTransactions getTableViewTransactions() {
        return tableViewTransactions;
    }

    public ScenarioEditor getScenarioEditor() {
        return scenarioEditor;
    }
}
