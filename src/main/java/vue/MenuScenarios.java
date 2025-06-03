package vue;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import modele.DonneesScenarios;

public class MenuScenarios extends MenuBar {
    private Menu menuScenario;
    private Menu menuAlgorithme;
    private Controleur controleur;

    public MenuScenarios(Controleur controleur) {
        this.controleur = controleur;
        this.setPrefWidth(330);
        menuScenario = new Menu("Scénarios");
        menuAlgorithme = new Menu("Algorithmes");

        ToggleGroup groupAlgorithmes = new ToggleGroup();
        for (String item : new String[]{"Topologique", "Glouton", "Brute force"}) {
            RadioMenuItem menuItem = new RadioMenuItem(item);
            menuItem.setToggleGroup(groupAlgorithmes);
            menuItem.setUserData(item);
            menuItem.setId("algorithme");
            menuAlgorithme.getItems().add(menuItem);
            menuItem.addEventHandler(ActionEvent.ACTION, controleur);
        }

        this.getMenus().addAll(menuScenario, menuAlgorithme);
        createMenu("");
    }

    /**
     * Permet de créer/mettre à jour le menu de la menu bar.
     *
     * @param nomScenario nom du scénario actuel.
     */
    public void createMenu(String nomScenario) {
        menuScenario.getItems().clear();
        ToggleGroup groupScenarios = new ToggleGroup();
        Boolean isFirst = true;

        for (String item : DonneesScenarios.getScenarios()) {
            RadioMenuItem menuItem = new RadioMenuItem(item);
            menuItem.setToggleGroup(groupScenarios);
            menuItem.setUserData(item);
            menuScenario.getItems().add(menuItem);
            menuItem.addEventHandler(ActionEvent.ACTION, controleur);

            if (isFirst || item.equals(nomScenario+".txt")) {
                menuItem.setSelected(true);
                isFirst = false;
            }
        }

        RadioMenuItem menuItem = new RadioMenuItem("Créer scenario");
        menuItem.setToggleGroup(groupScenarios);
        menuItem.setUserData("creer_scenario");
        menuScenario.getItems().add(menuItem);
        menuItem.addEventHandler(ActionEvent.ACTION, controleur);
    }
}
