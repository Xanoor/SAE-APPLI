package controleur;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TableView;
import modele.*;
import vue.*;

import java.io.File;
import java.io.IOException;

import static modele.DonneesScenarios.getScenarios;

public class Controleur implements EventHandler {

    @Override
    public void handle(Event event) {
        Scenario scenario = VBoxRoot.getScenario();
        HBoxContainer hBoxContainer = VBoxRoot.getHBoxContainer();
        VBoxAlgorithm vBoxAlgorithm = hBoxContainer.getVBoxAlgorithm();

        //la source de event est le bouton "Enregistrer" du formulaire de réservation
        if (event.getSource() instanceof RadioMenuItem) {
            String buttonId = ((RadioMenuItem) event.getSource()).getId();
            if (buttonId != null && buttonId.equals("algorithme")) {
                String algorithme = (String)((RadioMenuItem) event.getSource()).getUserData();
                if (algorithme == "Topologique") {
                    try {
                        new ScenarioVille();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if (algorithme == "Glouton") {
                    try {
                        new ScenarioGlouton();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                vBoxAlgorithm.update(scenario.getPath(), scenario.getDistance());

            } else {
                String RadioMenuItemName = ((RadioMenuItem) event.getSource()).getUserData().toString();
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
                vBoxAlgorithm.clear();
            }
        }
        System.out.println(event.getSource());
        if (event.getSource() instanceof TableView<?>) {
            hBoxContainer.getScenarioEditor().update((Transaction) ((TableView<?>) event.getSource()).getUserData());
            System.out.println(((TableView<?>) event.getSource()).getUserData());
        }

        if (event.getSource() instanceof Button) {
            ScenarioEditor scenarioEditor = hBoxContainer.getScenarioEditor();
            TableViewTransactions tableViewTransactions = hBoxContainer.getTableViewTransactions();
            if (((Button) event.getSource()).getUserData() == "enregistrer") {
                System.out.println(scenario.toString());
            } else if (((Button) event.getSource()).getUserData() == "modifier") {
                scenarioEditor.getCurrentTransaction().setAcheteur(scenarioEditor.getNomAcheteur());
                scenarioEditor.getCurrentTransaction().setVendeur(scenarioEditor.getNomVendeur());
                tableViewTransactions.update(scenario.getTransactions());
            } else if (((Button) event.getSource()).getUserData() == "supprimer") {
                scenario.removeTransaction(scenarioEditor.getCurrentTransaction());
                tableViewTransactions.update(scenario.getTransactions());
            } else if (((Button) event.getSource()).getUserData() == "ajouter") {
                scenario.addTransaction(new Transaction(scenarioEditor.getNomVendeur(), scenarioEditor.getNomAcheteur()));
                tableViewTransactions.update(scenario.getTransactions());
            } else if (((Button) event.getSource()).getUserData() == "sauvegarder") {
                try {
                    EcritureScenario.ecrireScenario(scenario.getNomScenario());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

