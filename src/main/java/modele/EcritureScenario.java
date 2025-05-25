package modele;

import vue.VBoxRoot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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
}