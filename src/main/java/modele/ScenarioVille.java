package modele;

import vue.VBoxRoot;

import java.util.*;

import static modele.ConversionVilles.getDistance;

public class ScenarioVille {
    public ScenarioVille() {
        Scenario scenario = VBoxRoot.getScenario();
        List<String[]> arcs = new ArrayList<>();
        Set<String> villesSet = new HashSet<>();

        // Récupération des arcs et villes
        for (Transaction transaction : scenario.getTransactions()) {
            String vendeur = transaction.getVilleVendeur();
            String acheteur = transaction.getVilleAcheteur();
            String vendeurPlus = vendeur + " +";
            String acheteurMoins = acheteur +" -";
            arcs.add(new String[]{vendeurPlus, acheteurMoins});
            villesSet.add(vendeurPlus);
            villesSet.add(acheteurMoins);
        }

        // Création d'une liste ordonnée des villes
        List<String> villes = new ArrayList<>(villesSet);
        Collections.sort(villes); // (optionnel mais permet d'avoir un ordre défini)

        // Création de la liste d'adjacence avec noms
        Map<String, List<String>> adjMap = new HashMap<>();
        for (String ville : villes) {
            adjMap.put(ville, new ArrayList<>());
        }

        for (String[] arc : arcs) {
            String from = arc[0];
            String to = arc[1];
            adjMap.get(from).add(to);
        }

        // Création et affichage du graphe
        GrapheOriente graphe = new GrapheOriente(adjMap);
        List<String> ordre = graphe.trieTopologiqueSimple();
        System.out.println("Ordre topologique : " + ordre);

        ordre.addFirst("Velizy +");
        ordre.addLast("Velizy -");

        // Création du chemin (version propre)
        List<String> Chemin = new ArrayList<>();

        for (String ville : ordre) {
            Chemin.add(nettoyerVille(ville));
        }

        // Calcul total de la distance
        int total = 0;
        for (int i = 0; i < Chemin.size() - 1; i++) {
            String villeA = nettoyerVille(Chemin.get(i));
            String villeB = nettoyerVille(Chemin.get(i + 1));
            int distance = getDistance(villeA, villeB);
            total += distance;

            System.out.println(Chemin.get(i) + " -> " + Chemin.get(i + 1) + " " + distance);
        }
        System.out.println("La distance total parcourus est de : " + total);

        scenario.setPath(Chemin);
        scenario.setDistance(total);
    }


    private String nettoyerVille(String nom) {
        if (nom.endsWith("+") || nom.endsWith("-")) {
            return nom.substring(0, nom.length() - 2);
        }
        return nom;
    }
}

