package modele;

import vue.VBoxRoot;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class LectureScenarios {

    public static Scenario lectureScenarios (File fichier) throws IOException {
        Scenario scenario = VBoxRoot.getScenario();
        Scanner scanner = new Scanner(fichier);
        Scanner scannerLine;

        scenario.clear();
        scenario.setNomScenario(fichier.getName());

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            scannerLine = new Scanner(line).useDelimiter(" ");
            String vendeur = scannerLine.next();

            scannerLine.next();
            String acheteur = scannerLine.next();

            scenario.addTransaction(vendeur, acheteur);
            System.out.println(vendeur + " " + acheteur);
            scannerLine.close();
        }
        scanner.close();
        return scenario;
    }
}