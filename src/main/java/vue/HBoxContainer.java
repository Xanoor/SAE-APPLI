package vue;

import controleur.Controleur;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import modele.LectureScenarios;
import modele.Scenario;

import java.io.File;
import java.io.IOException;

public class HBoxContainer extends HBox {
    private Scenario scenario;
    private TableViewTransactions tableViewTransactions;
    private ScenarioEditor scenarioEditor;
    private VBoxAlgorithm vBoxAlgorithm;

    public HBoxContainer(Controleur controleur) {
        this.setSpacing(20); // espace entre les enfants
        this.setPadding(new Insets(20)); // marges intérieures
        this.setAlignment(Pos.TOP_CENTER);

        scenario = VBoxRoot.getScenario();
        File fichier = new File("data/scenarios/scenario_0.txt");
        try {
            scenario = LectureScenarios.lectureScenarios(fichier);
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture scenario 0");
        }

        scenarioEditor = new ScenarioEditor(controleur);
        tableViewTransactions = new TableViewTransactions(controleur);
        vBoxAlgorithm = new VBoxAlgorithm(controleur);

        this.getChildren().addAll(tableViewTransactions, scenarioEditor, vBoxAlgorithm);
    }

    public TableViewTransactions getTableViewTransactions() {
        return tableViewTransactions;
    }

    public ScenarioEditor getScenarioEditor() {
        return scenarioEditor;
    }

    public VBoxAlgorithm getVBoxAlgorithm() {
        return vBoxAlgorithm;
    }
}
