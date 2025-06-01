package modele;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ConversionVilles {
    public static Map<String, String> tableConversion = new HashMap<>();
    public static List<List<Integer>> tableDistances = new ArrayList<>();
    private static List<String> villes = new ArrayList<>();

    /**
     * Met à jour l'affichage des comboBox et des labels ainsi que le champ currentTransaction.
     *
     * @param transaction La transaction séléctionnée.
     * @return Le nombre de voisins sortants de ce sommet.
     *         Retourne 0 si le sommet n'existe pas dans le graphe.
     */
    public static void convertirDistances() throws IOException {
        Scanner scanner = new Scanner(new File("data/distances.txt"));
        while (scanner.hasNextLine()) {
            villes.add(scanner.next());
            List<Integer> distances = new ArrayList<>();
            while (scanner.hasNextInt()) {
                distances.add(scanner.nextInt());
            }
            tableDistances.add(distances);
        }
        System.out.println(tableDistances);
        System.out.println(getDistance("Grenoble", "Paris"));
        System.out.println(convertVillesToInt());
    }

    public static Integer getDistance(String ville, String ville2) {
        if (villes.contains(ville) && villes.contains(ville2)) {
            return tableDistances.get(villes.indexOf(ville)).get(villes.indexOf(ville2));
        }
        return 0;
    }

    public static Map<String, Integer> convertVillesToInt() {
        Map<String, Integer> villeToInt = new HashMap<>();
        for (int i = 0; i < villes.size(); i++) {
            villeToInt.put(villes.get(i), i);
        }

        return villeToInt;
    }

    public static Map<String, String> convertirVilles () throws IOException {
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