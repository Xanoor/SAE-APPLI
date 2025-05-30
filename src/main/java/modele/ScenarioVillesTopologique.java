package modele;

import vue.VBoxRoot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ScenarioVillesTopologique {
    public ScenarioVillesTopologique() throws Exception {
        // Chargement du mapping
        Map<String, String> mapping = ConversionVilles.convertirVilles();
        // Préparation des ventes (arêtes du graphe)
        List<Transaction> ventes = VBoxRoot.getScenario().getTransactions();

        // Construction du graphe (adjacence) et calcul des degrés entrants
        Map<String, List<String>> adj = new HashMap<>();
        Map<String, Integer> degreEntrant = new HashMap<>();
        Set<String> villes = new HashSet<>();
        for (Transaction v : ventes) {
            adj.computeIfAbsent(v.getVendeur(), k -> new ArrayList<>()).add(v.getAcheteur());
            degreEntrant.put(v.getAcheteur(), degreEntrant.getOrDefault(v.getAcheteur(), 0) + 1);
            villes.add(v.getVendeur());
            villes.add(v.getAcheteur());
        }
        for (String ville : villes) degreEntrant.putIfAbsent(ville, 0);
        // File des villes à degré entrant 0
        List<String> villesRestantes = new ArrayList<>(villes);
        List<String> ordreLivraison = new ArrayList<>();
        while (!villesRestantes.isEmpty()) {
            // Chercher la ville avec le plus petit degré entrant (et non traitée)
            String minVille = null;
            int minDegre = Integer.MAX_VALUE;
            for (String ville : villesRestantes) {
                int deg = degreEntrant.getOrDefault(ville, 0);
                if (deg < minDegre) {
                    minDegre = deg;
                    minVille = ville;
                }
            }
            if (minVille == null) break;
            ordreLivraison.add(minVille);
            villesRestantes.remove(minVille);
            // Mettre à jour les degrés entrants des voisins
            if (adj.containsKey(minVille)) {
                for (String voisin : adj.get(minVille)) {
                    degreEntrant.put(voisin, degreEntrant.get(voisin) - 1);
                }
            }
        }
        System.out.println("Ordre de livraison (par plus petit degré entrant) : " + ordreLivraison);
        // Affichage détaillé des livraisons avec distances
        GrapheVilles g = new GrapheVilles();
        g.chargerDistances("data/distances.txt");
        String villeCourante = "Velizy";
        int total = 0;
        List<Transaction> ventesRestantes = new ArrayList<>(ventes);
        while (!ventesRestantes.isEmpty()) {
            final String villeActuelle = villeCourante;
            Transaction prochaineVente = ventesRestantes.stream()
                .min(Comparator.comparingInt(v -> g.longueurChemin(g.plusCourtChemin(villeActuelle, v.getVendeur()))))
                .orElse(null);

            // Aller au vendeur (uniquement si on n'y est pas déjà)
            if (!villeCourante.equals(prochaineVente.getVendeur())) {
                List<String> cheminVendeur = g.plusCourtChemin(villeCourante, prochaineVente.getVendeur());
                int distVendeur = g.longueurChemin(cheminVendeur);
                System.out.println(villeCourante + " -> " + prochaineVente.getVendeur() + " : " + distVendeur + " km");
                total += distVendeur;
                villeCourante = prochaineVente.getVendeur();
            }

            // Aller à l'acheteur (uniquement si on n'y est pas déjà)
            if (!villeCourante.equals(prochaineVente.getAcheteur())) {
                List<String> cheminAcheteur = g.plusCourtChemin(prochaineVente.getVendeur(), prochaineVente.getAcheteur());
                int distAcheteur = g.longueurChemin(cheminAcheteur);
                System.out.println(prochaineVente.getVendeur() + " -> " + prochaineVente.getAcheteur() + " : " + distAcheteur + " km");
                total += distAcheteur;
                villeCourante = prochaineVente.getAcheteur();
            }
            ventesRestantes.remove(prochaineVente);
        }
        // Retour à Velizy
        List<String> cheminRetour = g.plusCourtChemin(villeCourante, "Velizy");
        int distRetour = g.longueurChemin(cheminRetour);
        System.out.println(villeCourante + " -> Velizy : " + distRetour + " km");
        total += distRetour;
        System.out.println("Distance totale (livraison heuristique plus proche vendeur) : " + total + " km");
    }
}
