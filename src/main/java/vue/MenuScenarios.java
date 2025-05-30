package vue;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import modele.ConstValues;

public class MenuScenarios extends MenuBar {
    private Menu menu;
    private Controleur controleur;

    public MenuScenarios(Controleur controleur) {
        this.controleur = controleur;
        menu = new Menu("Scénarios");

        this.getMenus().add(menu);
        createMenu("scenario_0");
    }

    public void createMenu(String nomScenario) {
        menu.getItems().clear();
        ToggleGroup groupScenarios = new ToggleGroup();
        Boolean isFirst = true;

        for (String item : ConstValues.getScenarios()) {
            RadioMenuItem menuItem = new RadioMenuItem(item);
            menuItem.setToggleGroup(groupScenarios);
            menuItem.setUserData(item);
            menu.getItems().add(menuItem);
            menuItem.addEventHandler(ActionEvent.ACTION, controleur);

            if (isFirst || item.equals(nomScenario+".txt")) {
                menuItem.setSelected(true);
                isFirst = false;
            }
        }

        RadioMenuItem menuItem = new RadioMenuItem("Créer scenario");
        menuItem.setToggleGroup(groupScenarios);
        menuItem.setUserData("creer_scenario");
        menu.getItems().add(menuItem);
        menuItem.addEventHandler(ActionEvent.ACTION, controleur);
    }
}
