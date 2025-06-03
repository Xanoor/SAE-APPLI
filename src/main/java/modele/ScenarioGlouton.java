package modele;

import vue.VBoxRoot;

import java.util.*;

import static modele.DonneesScenarios.getDistance;

public class ScenarioGlouton {

    public ScenarioGlouton() {
        Scenario scenario = VBoxRoot.getScenario();
        List<String[]> arcs = new ArrayList<>();
        Set<String> villes = new HashSet<>();

        // Création des arcs et récupération des villes
        for (Transaction t : scenario.getTransactions()) {
            String vendeur = t.getVilleVendeur() + " +";
            String acheteur = t.getVilleAcheteur() + " -";
            arcs.add(new String[]{vendeur, acheteur});
            villes.add(vendeur);
            villes.add(acheteur);
        }

        // Liste ordonnée des villes
        List<String> listeVilles = new ArrayList<>(villes);
        Collections.sort(listeVilles);

        // Construction du graphe
        Map<String, List<String>> graphe = new HashMap<>();
        for (String ville : listeVilles) {
            graphe.put(ville, new ArrayList<>());
        }

        for (String[] arc : arcs) {
            graphe.get(arc[0]).add(arc[1]);
        }

        GrapheOriente g = new GrapheOriente(graphe);
        List<String> ordre = triGlouton(g);

        ordre.add(0, "Velizy +");
        ordre.add("Velizy -");

        // Nettoyage et création du chemin
        List<String> chemin = new ArrayList<>();
        for (String ville : ordre) {
            chemin.add(nettoyer(ville));
        }

        // Calcul de la distance
        int total = 0;
        for (int i = 0; i < chemin.size() - 1; i++) {
            String a = chemin.get(i);
            String b = chemin.get(i + 1);
            int d = getDistance(a, b);
            total += d;
            System.out.println(a + " -> " + b + " : " + d + " km");
        }

        System.out.println("Distance totale : " + total + " km");

        scenario.setPath(chemin);
        scenario.setDistance(total);
    }

    /** Tri glouton, cherche un chemin optimal en calculant les distances les plus proches entre les villes.
     *
     * @param g Graphe orienté
     * @return Retourne un chemin.
     */
    private List<String> triGlouton(GrapheOriente g) {
        List<String> resultat = new ArrayList<>();
        Map<String, Set<String>> voisins = new TreeMap<>();
        String depart = "Velizy +";
        for (Map.Entry<String, Set<String>> entry : g.getVoisinsSortants().entrySet()) {
            voisins.put(entry.getKey(), new HashSet<>(entry.getValue()));
        }

        Map<String, Integer> degres = g.getDegreEntrant();
        TreeSet<String> dispo = new TreeSet<>();
        for (String ville : degres.keySet()) {
            if (degres.get(ville) == 0) {
                dispo.add(ville);
            }
        }

        String actuel = depart;

        while (!dispo.isEmpty()) {
            String suivant = plusProche(actuel, dispo);
            if (suivant == null) break;

            resultat.add(suivant);
            dispo.remove(suivant);

            for (String v : voisins.getOrDefault(suivant, new HashSet<>())) {
                degres.put(v, degres.get(v) - 1);
                if (degres.get(v) == 0) {
                    dispo.add(v);
                }
            }

            actuel = suivant;
        }

        return resultat;
    }

    /**
     * Calcul le chemin le plus proche entre une ville et un ensemble de villes.
     * @param from Ville de départ
     * @param villes Ensemble de ville
     * @return La ville la plus proche
     */
    private String plusProche(String from, Set<String> villes) {
        String proche = null;
        int min = Integer.MAX_VALUE;

        for (String v : villes) {
            int d = getDistance(nettoyer(from), nettoyer(v));
            if (d < min) {
                min = d;
                proche = v;
            }
        }

        return proche;
    }

    /**
     * Permet de supprimer le "+" et "-" à la fin du nom des villes.
     * @param nom Nom de la ville.
     * @return Nom de la ville nettoyé.
     */
    private String nettoyer(String nom) {
        if (nom.endsWith("+") || nom.endsWith("-")) {
            return nom.substring(0, nom.length() - 2).trim();
        }
        return nom;
    }
}
