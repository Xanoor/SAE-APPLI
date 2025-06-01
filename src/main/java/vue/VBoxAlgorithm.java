package vue;

import controleur.Controleur;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import modele.ScenarioVille;

import java.util.List;


public class VBoxAlgorithm extends VBox {
    private Label labelChemin;
    private Label labelDistance;

    public VBoxAlgorithm(Controleur controleur) {
        super();
        this.setSpacing(15);
        this.setPrefWidth(330);
        this.setPadding(new Insets(15));
        this.setAlignment(Pos.TOP_LEFT);

        labelChemin = new Label("Chemin trouvé: \nSélectionnez un algorithme pour trouver un chemin !");
        labelChemin.setWrapText(true);
        labelChemin.setMaxWidth(350);
        labelDistance = new Label("Distance totale : x");

        this.getChildren().addAll(labelChemin, labelDistance);
    }

    /**
     * Met à jour le texte des labels afin d'afficher le chemin trouvé et la distance totale.
     *
     * @param chemin Liste contenant toutes les villes parcourues (dans l'ordre).
     * @param distance Distance totale parcourue.
     */
    public void update(List<String> chemin, int distance) {
        labelChemin.setText("Chemin trouvé: \n" + chemin.toString().replace("[", "").replace("]", ""));
        labelDistance.setText("Distance : " + distance + "km");

        labelChemin.applyCss();
        labelChemin.layout();
    }

    /**
     * Permet de réinitialiser les labels.
     */
    public void clear() {
        labelChemin.setText("Chemin trouvé: \nSélectionnez un algorithme pour trouver un chemin !");
        labelDistance.setText("Distance totale : x");
    }
}
