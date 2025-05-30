package modele;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConversionVilles {
    public static Map<String, String> tableConversion = new HashMap<>();

    public static Map<String, String> convertirVilles () throws IOException {
        System.out.println("APPPPELLLLL");
        tableConversion.clear();
        Map<String, String> conversionVilles = new HashMap<>();
        Scanner scanner = new Scanner(new File("data/membres_APPLI.txt"));
        Scanner scannerLine;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            scannerLine = new Scanner(line).useDelimiter(" ");
            String pokemon = scannerLine.next();
            String ville = scannerLine.next();

            conversionVilles.put(pokemon, ville);
            scannerLine.close();
        }

        scanner.close();

        tableConversion = conversionVilles;
        return conversionVilles;
    }

    public static String convertirPokemon (String pokemon) {
        if (tableConversion.containsKey(pokemon)) return tableConversion.get(pokemon);
        else return pokemon;
    }
}