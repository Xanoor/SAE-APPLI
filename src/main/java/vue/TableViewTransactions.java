package vue;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import modele.Transaction;

import java.util.List;

public class TableViewTransactions extends VBox {
    private TableView<Transaction> tableView;

    public TableViewTransactions(Controleur controleur) {
        super();

        tableView = new TableView<>();
        this.getChildren().add(tableView);

        // Colonne Vendeur
        TableColumn<Transaction, String> vendeurColumn = new TableColumn<>("Vendeur");
        vendeurColumn.setCellValueFactory(new PropertyValueFactory<>("vendeur"));

        // Colonne Acheteur
        TableColumn<Transaction, String> acheteurColumn = new TableColumn<>("Acheteur");
        acheteurColumn.setCellValueFactory(new PropertyValueFactory<>("acheteur"));

        tableView.getColumns().addAll(vendeurColumn, acheteurColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setPrefSize(400, 400);
        tableView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        vendeurColumn.setReorderable(false);
        acheteurColumn.setReorderable(false);

        // Gestion du clic
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Transaction selected = tableView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    tableView.setUserData(selected);
                    ActionEvent actionEvent = new ActionEvent(tableView, tableView);
                    controleur.handle(actionEvent);
                }
            }
        });

        update(VBoxRoot.getScenario().getTransactions());
    }

    public void update(List<Transaction> transaction) {
        Controleur controleur = VBoxRoot.getControleur();
        Boolean isFirst = true;
        tableView.getItems().clear();
        for (int i = 0; i < transaction.size(); i++) {
            tableView.getItems().add(transaction.get(i));
            if ((isFirst) && VBoxRoot.getHBoxContainer() != null) {
                tableView.getSelectionModel().select(i);

                Transaction selected = tableView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    tableView.setUserData(selected);
                    ActionEvent actionEvent = new ActionEvent(tableView, tableView);
                    controleur.handle(actionEvent);
                }

                isFirst = false;
            }
        }
    }
}