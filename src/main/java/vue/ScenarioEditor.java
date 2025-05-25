package vue;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import modele.Transaction;

public class ScenarioEditor extends GridPane {
    private TextField nomVendeur;
    private TextField nomAcheteur;
    private Label labelTransaction;
    private Transaction currentTransaction;


    public ScenarioEditor(Controleur controleur) {
        super();
//        this.setGridLinesVisible(true);
        this.setHgap(10);
        this.setVgap(10);

        labelTransaction = new Label("Transaction sélectionnée: aucune");
        this.add(labelTransaction, 0, 0, 2, 1);

        this.add(new Label("Vendeur"), 0, 1);
        this.add(new Label("Acheteur"), 1, 1);

        nomVendeur = new TextField("Vendeur");
        nomAcheteur = new TextField("Acheteur");
        this.add(nomVendeur, 0, 2);
        this.add(nomAcheteur, 1, 2);

        Button b = new Button("_Test");
        b.setMnemonicParsing(true);
        b.setUserData("enregistrer");
        b.addEventHandler(ActionEvent.ACTION, controleur);
        this.add(b, 0, 5);

        Button addButton = new Button("_Ajouter");
        addButton.setMnemonicParsing(true);
        addButton.setUserData("ajouter");
        addButton.addEventHandler(ActionEvent.ACTION, controleur);
        this.add(addButton, 0, 4);

        Button updateButton = new Button("_Modifier");
        updateButton.setMnemonicParsing(true);
        updateButton.setUserData("modifier");
        updateButton.addEventHandler(ActionEvent.ACTION, controleur);
        this.add(updateButton, 1, 4);

        Button removeButton = new Button("_Supprimer");
        removeButton.setMnemonicParsing(true);
        removeButton.setUserData("supprimer");
        removeButton.addEventHandler(ActionEvent.ACTION, controleur);
        this.add(removeButton, 2, 0);

        Button saveButton = new Button("S_auvegarder");
        saveButton.setMnemonicParsing(true);
        saveButton.setUserData("sauvegarder");
        saveButton.addEventHandler(ActionEvent.ACTION, controleur);
        this.add(saveButton, 0, 6);
    }

    public void update(Transaction transaction) {
        nomVendeur.setText(transaction.getVendeur());
        nomAcheteur.setText(transaction.getAcheteur());
        currentTransaction = transaction;
        labelTransaction.setText("Transaction sélectionnée: " + transaction);
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public String getNomVendeur() {
        return nomVendeur.getText();
    }

    public String getNomAcheteur() {
        return nomAcheteur.getText();
    }
}
