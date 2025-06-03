package modele;

import java.io.File;
import java.util.*;

public class DonneesScenarios {
    public static Map<String, String> tableConversion = new HashMap<>();
    public static List<List<Integer>> tableDistances = new ArrayList<>();
    private static List<String> villes = new ArrayList<>();
    private static List<String> CONST_SCENARIOS = importScenarios();

    /**
     * Méthode permettant de récupérer la liste des scénarios.
     *
     * @return Liste contenant le noms des scénarios.
     */
    private static List<String> importScenarios() {
        try {
            List<String> scenarios = new ArrayList<>();

            for (File file : new File("data/scenarios").listFiles()) {
                scenarios.add(file.getName());
            }
            return scenarios;
        } catch (Exception e) {
            System.out.println("Erreur lors de l'importation de scenarios (noms)");
            throw new RuntimeException(e);
        }
    }

    /**
     * Permet de convertir les distances dans le fichier distances.txt en matrice.
     * Ajoute également toutes les villes du fichier dans la liste "villes".
     */
    public static void convertirDistances() {
        try {
            Scanner scanner = new Scanner(new File("data/distances.txt"));
            while (scanner.hasNextLine()) {
                villes.add(scanner.next());
                List<Integer> distances = new ArrayList<>();
                while (scanner.hasNextInt()) {
                    distances.add(scanner.nextInt());
                }
                tableDistances.add(distances);
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de l'importation des distances");
            throw new RuntimeException(e);
        }
    }

    /**
     * Méthode permettant de récupérer la distance entre deux villes.
     * @param ville Ville numéro 1
     * @param ville2 Ville numéro 2
     * @return Retourne la distance entre les deux villes ou 0 si une des deux villes est inconnue (ou que la matrice des distances est vide).
     */
    public static Integer getDistance(String ville, String ville2) {
        if (villes.contains(ville) && villes.contains(ville2) && !tableDistances.isEmpty()) {
            return tableDistances.get(villes.indexOf(ville)).get(villes.indexOf(ville2));
        }
        return 0;
    }

    /**
     * Créer une hashMap avec le nom de chaque pokémon correspondant a une ville.
     * @return Retourne une Map de conversion (pokémon = ville)
     */
    public static Map<String, String> convertirVilles () {
        try {
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
        } catch (Exception e) {
            System.out.println("Erreur lors de l'importation des villes (conversion nom pokémon -> nom ville)");
            throw new RuntimeException(e);
        }

    }

    /**
     * Permet de récupérer la ville où réside un pokémon.
     * @param pokemon Le nom du pokémon.
     * @return Retourne la ville du pokémon où son nom si aucune ville est trouvée.
     */
    public static String convertirPokemon (String pokemon) {
        if (tableConversion.containsKey(pokemon)) return tableConversion.get(pokemon);
        else return pokemon;
    }

    /**
     * Permet de mettre à jour la liste de des noms des scénarios (lors de la créations de nouveaux
     * scénarios par exemple).
     */
    public static void updateScenarios() {
        CONST_SCENARIOS = importScenarios();
    }

    public static List<String> getScenarios() {
        return CONST_SCENARIOS;
    }
}