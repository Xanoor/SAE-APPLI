package vue;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modele.ConstValues;

import javax.swing.*;

public class MenuScenarios extends MenuBar {

    public MenuScenarios(Controleur controleur) {
        Menu menu = new Menu("Scénarios");

        this.getMenus().add(menu);

        ToggleGroup groupScenarios = new ToggleGroup();
        Boolean isFirst = true;
        for (String item : ConstValues.getScenarios()) {
            RadioMenuItem menuItem = new RadioMenuItem(item);
            menuItem.setToggleGroup(groupScenarios);
            menuItem.setUserData(item);
            menu.getItems().add(menuItem);
            menuItem.addEventHandler(ActionEvent.ACTION, controleur);

            if (isFirst) {
                menuItem.setSelected(true);
                isFirst = false;
            }
        }


    }
}
