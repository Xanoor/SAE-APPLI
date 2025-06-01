package modele;

import vue.VBoxRoot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EcritureScenario {

    /**
     * Fonction permettant d'écrire un scénario (lors de la modification d'un scénario par exemple)/
     * @param fichier Nom du fichier du scénario
     * @throws IOException Exception si un problème survient lors de la lecture/écriture du fichier.
     */
    public static void ecrireScenario (String fichier) throws IOException {
        Scenario scenario = VBoxRoot.getScenario();

        try {
            FileWriter writer = new FileWriter("data/scenarios/"+fichier);
            for (Transaction transaction : scenario.getTransactions()) {
                writer.write(transaction.getVendeur() + " -> " + transaction.getAcheteur() + "\n");
                System.out.println(transaction);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Exception lors de la lecture/écriture du scenario.");
            e.printStackTrace();
        }
    }

    /**
     * Permet de créer un nouveau fichier de scénario.
     * @param fichier Nom du fichier du scénario.
     * @throws IOException Exception levée lors d'un problème de lecture/écriture/création du fichier.
     */
    public static void creerScenario (String fichier) throws IOException {
        try {
            FileWriter writer = new FileWriter("data/scenarios/"+fichier);
            writer.close();

            File nouveau_fichier= new File("data/scenarios/"+fichier);
            LectureScenarios.lectureScenarios(nouveau_fichier);
        } catch (IOException e) {
            System.out.println("Exception lors de la lecture/écriture/création du fichier scenario.");
            e.printStackTrace();
        }
    }
}