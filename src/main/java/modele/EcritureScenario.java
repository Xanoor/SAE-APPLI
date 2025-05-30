package modele;

import vue.VBoxRoot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EcritureScenario {

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
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void creerScenario (String fichier) throws IOException {
        try {
            FileWriter writer = new FileWriter("data/scenarios/"+fichier);
            writer.close();

            File nouveau_fichier= new File("data/scenarios/"+fichier);
            LectureScenarios.lectureScenarios(nouveau_fichier);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}