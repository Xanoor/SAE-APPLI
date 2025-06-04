package modele;

import vue.VBoxRoot;
import java.util.*;


public class ScenarioBruteForce {
    private Scenario scenario;
    private List<String> meilleursChemins;
    private List<Solution> listeSolutions;
    private int k;
    private int meilleureDistance = Integer.MAX_VALUE;

    /**
     * Construit et lance la recherche des k meilleurs chemins pour le scénario courant.
     * @param k nombre de solutions à conserver
     */
    public ScenarioBruteForce (int k) {
        this.scenario = VBoxRoot.getScenario();
        this.k = k;
        this.meilleursChemins = new ArrayList<>();

        KMeilleursChemins();

        System.out.println("=== " + k + " MEILLEURS CHEMINS ===");
        for (String chemin : meilleursChemins) {
            System.out.println(chemin);
        }
    }

    /**
     * Lance la recherche des k meilleurs chemins par backtracking.
     */
    private void KMeilleursChemins() {
        List<String> chemin = new ArrayList<>();
        chemin.add("Velizy");
        Set<Integer> transactionsEffectuees = new HashSet<>();

        backtrack("Velizy", chemin, 0, transactionsEffectuees, new ArrayList<>());

        if (meilleursChemins.isEmpty()) {
            System.out.println("Aucun chemin trouvé.");
        }
    }

    /**
     * Backtracking récursif pour explorer tous les chemins réalisant toutes les transactions.
     * @param villeActuelle ville courante
     * @param chemin chemin courant
     * @param distance distance accumulée
     * @param transactionsEffectuees transactions déjà réalisées
     * @param solutions liste des solutions trouvées
     */
    private void backtrack(String villeActuelle, List<String> chemin, int distance,
                           Set<Integer> transactionsEffectuees, List<Solution> solutions) {

        // Si toutes les transactions sont faites
        if (transactionsEffectuees.size() == scenario.getTransactions().size()) {
            int distanceRetour = getDistance(villeActuelle, "Velizy");
            int distanceTotale = distance + distanceRetour;

            List<String> cheminComplet = new ArrayList<>(chemin);
            cheminComplet.add("Velizy");

            solutions.add(new Solution(cheminComplet, distanceTotale));

            // Garder seulement les k meilleures solutions
            solutions.sort(Comparator.comparingInt(s -> s.distance));
            if (solutions.size() > k) {
                solutions.remove(solutions.size() - 1);
            }

            listeSolutions = solutions;

            // Mettre à jour meilleursChemins avec les meilleures solutions actuelles
            meilleursChemins.clear();
            for (Solution sol : solutions) {
                meilleursChemins.add(sol.chemin + " → Distance: " + sol.distance + " km");
            }

            return;
        }

        // Élagage si la distance dépasse déjà la pire des k solutions
        if (!solutions.isEmpty() && solutions.size() == k &&
                distance >= solutions.get(solutions.size() - 1).distance) {
            return;
        }

        // Essayer toutes les transactions possibles dans la ville actuelle
        List<Integer> transactionsPossibles = getTransactionsPossibles(villeActuelle, transactionsEffectuees);

        if (!transactionsPossibles.isEmpty()) {
            // Essayer chaque transaction
            for (int transactionId : transactionsPossibles) {
                Transaction t = scenario.getTransactions().get(transactionId);
                String acheteur = t.getVilleAcheteur();
                int distanceTransaction = getDistance(villeActuelle, acheteur);

                // Faire la transaction
                chemin.add(acheteur);
                transactionsEffectuees.add(transactionId);

                backtrack(acheteur, chemin, distance + distanceTransaction,
                        transactionsEffectuees, solutions);

                // Annuler (backtrack)
                chemin.remove(chemin.size() - 1);
                transactionsEffectuees.remove(transactionId);
            }
        } else {
            // Se déplacer vers une ville avec des transactions à faire
            Set<String> villesAvecTransactions = getVillesAvecTransactions(transactionsEffectuees);
            villesAvecTransactions.remove(villeActuelle); // Ne pas rester sur place

            for (String nouvelleVille : villesAvecTransactions) {
                int distanceDeplacement = getDistance(villeActuelle, nouvelleVille);

                chemin.add(nouvelleVille);
                backtrack(nouvelleVille, chemin, distance + distanceDeplacement,
                        transactionsEffectuees, solutions);
                chemin.remove(chemin.size() - 1);
            }
        }
    }

    /**
     * Retourne la liste des indices de transactions réalisables depuis la ville courante.
     * @param ville ville actuelle
     * @param transactionsEffectuees index des transactions déja effectuées
     * @return liste des index des villes lorsque la transaction est possible.
     */
    private List<Integer> getTransactionsPossibles(String ville, Set<Integer> transactionsEffectuees) {
        List<Integer> possibles = new ArrayList<>();
        for (int i = 0; i < scenario.getTransactions().size(); i++) {
            if (!transactionsEffectuees.contains(i)) {
                Transaction t = scenario.getTransactions().get(i);
                if (t.getVilleVendeur().equals(ville)) {
                    possibles.add(i);
                }
            }
        }
        return possibles;
    }

    /**
     * Retourne l'ensemble des villes où il reste des transactions à faire.
     * @param transactionsEffectuees Idnex des transactions déja effectuées
     * @return Ville où il reste des transactions.
     */
    private Set<String> getVillesAvecTransactions(Set<Integer> transactionsEffectuees) {
        Set<String> villes = new HashSet<>();
        for (int i = 0; i < scenario.getTransactions().size(); i++) {
            if (!transactionsEffectuees.contains(i)) {
                Transaction t = scenario.getTransactions().get(i);
                villes.add(t.getVilleVendeur());
            }
        }
        return villes;
    }

    /**
     * Retourne la distance entre deux villes.
     * @param ville1 ville a
     * @param ville2 ville b
     * @return Distance entre ville a (ville1) et ville b (ville2).
     */
    private int getDistance(String ville1, String ville2) {
        return DonneesScenarios.getDistance(ville1, ville2);
    }

    /**
     * Retourne la liste des meilleures solutions trouvées (chemin + distance).
     */
    public List<Solution> getSolutions() {
        return listeSolutions;
    }

    /**
     * Classe simple pour stocker une solution (chemin + distance)
     */
    public static class Solution {
        String chemin;
        int distance;
        Solution(List<String> chemin, int distance) {
            this.chemin = String.join(" -> ", chemin);
            this.distance = distance;
        }
        public String getChemin() { return chemin; }
        public int getDistance() { return distance; }
    }
}
