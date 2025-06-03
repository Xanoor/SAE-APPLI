package modele;

import vue.VBoxRoot;

import java.util.*;

import static modele.DonneesScenarios.getDistance;

public class ScenarioGlouton {
    public ScenarioGlouton() {
        Scenario scenario = VBoxRoot.getScenario();

        List<String> acheteur = new ArrayList<>();
        Set<String> villesSet = new HashSet<>();
        List<String> vendeurs = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        // Récupération villes
        for (Transaction transaction : scenario.getTransactions()) {
            String vendeur = transaction.getVilleVendeur();
            String Acheteur = transaction.getVilleAcheteur();
            villesSet.add(vendeur);
            villesSet.add(Acheteur);
            vendeurs.add(vendeur);
        }

        String actuelle = "Velizy";
        List<String> chemin = new ArrayList<>();
        chemin.add("Velizy"); // Ville de départ
        while (visited.size() < villesSet.size()) {
            int meilleureDistance = 10000;
            if (visited.isEmpty()) {
                for (Transaction transaction : scenario.getTransactions()) {
                    String vendeur = transaction.getVilleVendeur();
                    int distance = getDistance("Velizy", vendeur);

                    if (distance < meilleureDistance) {
                        meilleureDistance = distance;
                        actuelle = vendeur;
                        acheteur.add(transaction.getVilleAcheteur());
                    }
                }
                visited.add(actuelle);
                chemin.add(actuelle);
                System.out.println("Départ vers : " + actuelle);
                continue;
            }
            meilleureDistance = 10000;
            String prochaineVille = null;
            boolean versAcheteur = false;
            for (String Acheteur : acheteur) {
                for (String Vendeur : vendeurs) {
                    if (!visited.contains(Vendeur)) {
                        int distanceVendeur = getDistance(actuelle, Vendeur);
                        int distanceAcheteur = getDistance(actuelle, Acheteur);

                        if (distanceVendeur < distanceAcheteur && distanceVendeur < meilleureDistance) {
                            meilleureDistance = distanceVendeur;
                            prochaineVille = Vendeur;
                            versAcheteur = false;
                        } else if (distanceAcheteur <= distanceVendeur && distanceAcheteur < meilleureDistance && !visited.contains(Acheteur)) {
                            meilleureDistance = distanceAcheteur;
                            prochaineVille = Acheteur;
                            versAcheteur = true;
                        }
                    }
                }
            }
            if (prochaineVille != null) {
                actuelle = prochaineVille;
                visited.add(actuelle);
                chemin.add(actuelle);

                if (!versAcheteur) {
                    for (Transaction t : scenario.getTransactions()) {
                        if (t.getVilleVendeur().equals(actuelle)) {
                            acheteur.add(t.getVilleAcheteur());
                            break;
                        }
                    }
                }
            } else {
                System.out.println("Aucune ville trouvée, boucle arrêtée");
                break;
            }
        }
        chemin.add("Velizy");
        System.out.println("Chemin :");
        int total=0;
        for (int i = 0; i<chemin.size()-1;i++) {
            System.out.println(chemin.get(i) + " -> " + chemin.get(i + 1) + " distance : " + getDistance(chemin.get(i), chemin.get(i + 1)) + " km");
            total+=getDistance(chemin.get(i), chemin.get(i + 1));
        }
        System.out.println("distance totale est de : "+total+" km");

        scenario.setPath(chemin);
        scenario.setDistance(total);
    }
}