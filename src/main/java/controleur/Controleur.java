package controleur;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TableView;
import modele.EcritureScenario;
import modele.LectureScenarios;
import modele.Scenario;
import modele.Transaction;
import vue.HBoxContainer;
import vue.ScenarioEditor;
import vue.TableViewTransactions;
import vue.VBoxRoot;

import java.io.File;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class Controleur implements EventHandler {

    @Override
    public void handle(Event event) {
        Scenario scenario = VBoxRoot.getScenario();
        HBoxContainer hBoxContainer = VBoxRoot.getHBoxContainer();

        //la source de event est le bouton "Enregistrer" du formulaire de réservation
        if (event.getSource() instanceof RadioMenuItem) {
            System.out.println(((RadioMenuItem) event.getSource()).getUserData());
            File fichier = new File("data/scenarios/"+((RadioMenuItem) event.getSource()).getUserData());
            try {
                scenario = LectureScenarios.lectureScenarios(fichier);
            } catch (IOException e) {
                System.out.println(e);
            }

            hBoxContainer.getTableViewTransactions().update(scenario.getTransactions());
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

