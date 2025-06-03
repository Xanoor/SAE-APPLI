package vue;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import modele.DonneesScenarios;
import modele.Transaction;

public class ScenarioEditor extends GridPane {
    private ComboBox<String> nomVendeur;
    private ComboBox<String> nomAcheteur;
    private Label labelTransaction;
    private Transaction currentTransaction;

    public ScenarioEditor(Controleur controleur) {
        super();
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.TOP_CENTER); // Centre tout le contenu

        labelTransaction = new Label("Transaction sélectionnée: aucune");
        this.add(labelTransaction, 0, 0, 3, 1);

        this.add(new Label("Vendeur"), 0, 1);
        this.add(new Label("Acheteur"), 1, 1);

        nomVendeur = new ComboBox<>();
        nomAcheteur = new ComboBox<>();

        for (String item : DonneesScenarios.convertirVilles().keySet()) {
            nomVendeur.getItems().add(item);
            nomAcheteur.getItems().add(item);
        }

        this.add(nomVendeur, 0, 2);
        this.add(nomAcheteur, 1, 2);

        Button addButton = new Button("_Ajouter");
        addButton.setMnemonicParsing(true);
        addButton.setUserData("ajouter");
        addButton.addEventHandler(ActionEvent.ACTION, controleur);

        Button updateButton = new Button("_Modifier");
        updateButton.setMnemonicParsing(true);
        updateButton.setUserData("modifier");
        updateButton.addEventHandler(ActionEvent.ACTION, controleur);

        Button removeButton = new Button("_Supprimer");
        removeButton.setMnemonicParsing(true);
        removeButton.setUserData("supprimer");
        removeButton.addEventHandler(ActionEvent.ACTION, controleur);

        Button saveButton = new Button("S_auvegarder");
        saveButton.setMnemonicParsing(true);
        saveButton.setUserData("sauvegarder");
        saveButton.addEventHandler(ActionEvent.ACTION, controleur);

        this.add(removeButton, 2, 2);
        this.add(addButton, 0, 3);
        this.add(updateButton, 1, 3);
        this.add(saveButton, 2, 3);
    }

    /**
     * Met à jour l'affichage des comboBox et des labels ainsi que le champ currentTransaction.
     *
     * @param transaction La transaction séléctionnée.
     */
    public void update(Transaction transaction) {
        if (nomVendeur.getItems().contains(transaction.getVendeur())) {
            nomVendeur.setValue(transaction.getVendeur());
        }
        if (nomAcheteur.getItems().contains(transaction.getAcheteur())) {
            nomAcheteur.setValue(transaction.getAcheteur());
        }
        currentTransaction = transaction;
        labelTransaction.setText("Transaction sélectionnée: " + transaction);
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public String getNomVendeur() {
        return nomVendeur.getValue();
    }

    public String getNomAcheteur() {
        return nomAcheteur.getValue();
    }
}
