package controleur;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.WindowEvent;
import modele.*;
import vue.*;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static modele.DonneesScenarios.getScenarios;

public class Controleur implements EventHandler {

    @Override
    public void handle(Event event) {
        Scenario scenario = VBoxRoot.getScenario();
        HBoxContainer hBoxContainer = VBoxRoot.getHBoxContainer();
        VBoxAlgorithm vBoxAlgorithm = hBoxContainer.getVBoxAlgorithm();

        //Evenements liés à la menu bar.
        if (event.getSource() instanceof RadioMenuItem) {

            //On utilise un id pour différencier les items liés au menu algorithme du menu scénarios
            String buttonId = ((RadioMenuItem) event.getSource()).getId();
            if (buttonId != null && buttonId.equals("algorithme")) {
                String algorithme = (String)((RadioMenuItem) event.getSource()).getUserData();
                if (algorithme == "Topologique") {
                    try {
                        new ScenarioVille();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    vBoxAlgorithm.update(scenario.getPath(), scenario.getDistance());
                } else if (algorithme == "Glouton") {
                    try {
                        new ScenarioGlouton();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    vBoxAlgorithm.update(scenario.getPath(), scenario.getDistance());
                } else if (algorithme == "Brute force") {
                    try {
                        ScenarioBruteForce bruteForce = new ScenarioBruteForce(hBoxContainer.getScenarioEditor().getKSolutions());
                        scenario.setKpath(bruteForce.getSolutions());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    vBoxAlgorithm.update(scenario.getkPath());
                }

            } else { // Partie menu scénario
                String RadioMenuItemName = ((RadioMenuItem) event.getSource()).getUserData().toString();

                //Si le bouton est le bouton créer_scenario, alors on ne fait le même script que pour les scénarios
                if (RadioMenuItemName.equals("creer_scenario")) {
                    MenuScenarios menu = VBoxRoot.getMenuBarScenarios();
                    String fileName = "scenario_"+getScenarios().size();
                    try {
                        EcritureScenario.creerScenario(fileName+".txt");
                        DonneesScenarios.updateScenarios();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    menu.createMenu(fileName);
                } else {
                    File fichier = new File("data/scenarios/"+RadioMenuItemName);
                    try {
                        scenario = LectureScenarios.lectureScenarios(fichier);
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }

                hBoxContainer.getTableViewTransactions().update(scenario.getTransactions());
                vBoxAlgorithm.clear(); //On nettoie l'affichage des chemins et distances car nous ne sommes plus sur le même scénario
            }
        }

        //Evenements liés à la tableview.
        if (event.getSource() instanceof TableView<?>) {
            //On update l'affichage de la classe ScenarioEditor afin d'afficher l'élément cliqué dans la tableView.
            hBoxContainer.getScenarioEditor().update((Transaction) ((TableView<?>) event.getSource()).getUserData());
        }

        //Evenements liés à un bouton
        if (event.getSource() instanceof Button) {
            ScenarioEditor scenarioEditor = hBoxContainer.getScenarioEditor();
            TableViewTransactions tableViewTransactions = hBoxContainer.getTableViewTransactions();

            if (((Button) event.getSource()).getUserData() == "modifier") { //Bouton modifier
                scenarioEditor.getCurrentTransaction().setAcheteur(scenarioEditor.getNomAcheteur());
                scenarioEditor.getCurrentTransaction().setVendeur(scenarioEditor.getNomVendeur());
                tableViewTransactions.update(scenario.getTransactions());
            } else if (((Button) event.getSource()).getUserData() == "supprimer") { //Bouton supprimer
                scenario.removeTransaction(scenarioEditor.getCurrentTransaction());
                tableViewTransactions.update(scenario.getTransactions());
            } else if (((Button) event.getSource()).getUserData() == "ajouter") { //Bouton ajouter
                scenario.addTransaction(new Transaction(scenarioEditor.getNomVendeur(), scenarioEditor.getNomAcheteur()));
                tableViewTransactions.update(scenario.getTransactions());
            } else if (((Button) event.getSource()).getUserData() == "sauvegarder") { //Bouton sauvegarder
                //Demande de confirmation
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Sauvegarder");
                alert.setHeaderText("Souhaitez-vous sauvegarder toutes modifications de ce scénario ?");

                File fichierCss = new File("css/alert.css");
                alert.getDialogPane().getStylesheets().add(fichierCss.toURI().toString());

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) { //Confirmation validée
                    try {
                        EcritureScenario.ecrireScenario(scenario.getNomScenario());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    //Evenement appelé lors de la fermeture de la fenêtre
    public void closeWindow(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Fermeture");
        alert.setHeaderText("Souhaitez-vous quitter l'application ?");

        File fichierCss = new File("css/alert.css");
        alert.getDialogPane().getStylesheets().add(fichierCss.toURI().toString());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            event.consume(); // annule la fermeture
        }
    }

}

