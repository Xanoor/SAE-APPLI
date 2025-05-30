package modele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class GrapheVilles {
    private Map<String, Map<String, Integer>> arcs = new HashMap<>();
    private List<String> villes = new ArrayList<>();

    // Lecture du fichier distances.txt
    public void chargerDistances(String cheminFichier) throws IOException {
        List<String[]> lignes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                if (ligne.trim().isEmpty() || ligne.startsWith("//")) continue;
                String[] parts = ligne.trim().split(" +");
                villes.add(parts[0]);
                lignes.add(parts);
            }
        }
        // Remplir la map arcs correctement
        for (int i = 0; i < villes.size(); i++) {
            String ville = villes.get(i);
            Map<String, Integer> voisins = new HashMap<>();
            String[] parts = lignes.get(i);
            for (int j = 1; j < parts.length; j++) {
                String autreVille = villes.get(j - 1);
                int dist = Integer.parseInt(parts[j]);
                voisins.put(autreVille, dist);
            }
            arcs.put(ville, voisins);
        }
    }

    // Plus court chemin (Dijkstra) entre deux villes
    public List<String> plusCourtChemin(String depart, String arrivee) {
        try {
            if (ConversionVilles.tableConversion.isEmpty()) ConversionVilles.convertirVilles();
        } catch (Exception e) {
            e.printStackTrace();
        }

        depart = ConversionVilles.convertirPokemon(depart) ;
        arrivee = ConversionVilles.convertirPokemon(arrivee);

        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        PriorityQueue<Noeud> file = new PriorityQueue<>(Comparator.comparingInt(n -> n.dist));
        for (String v : villes) dist.put(v, Integer.MAX_VALUE);
        dist.put(depart, 0);
        file.add(new Noeud(depart, 0));
        while (!file.isEmpty()) {
            Noeud courant = file.poll();
            if (!arcs.containsKey(courant.nom)) {
                System.err.println("Ville inconnue dans le graphe : " + courant.nom);
                System.err.println("Villes disponibles : " + villes);
                return new ArrayList<>();
            }
            if (courant.nom.equals(arrivee)) break;
            for (Map.Entry<String, Integer> arc : arcs.get(courant.nom).entrySet()) {
                int alt = dist.get(courant.nom) + arc.getValue();
                if (alt < dist.get(arc.getKey())) {
                    dist.put(arc.getKey(), alt);
                    prev.put(arc.getKey(), courant.nom);
                    file.add(new Noeud(arc.getKey(), alt));
                }
            }
        }
        List<String> chemin = new ArrayList<>();
        String courant = arrivee;
        if (prev.get(courant) == null) return chemin;
        while (courant != null) {
            chemin.add(0, courant);
            courant = prev.get(courant);
        }
        return chemin;
    }

    public int longueurChemin(List<String> chemin) {
        int total = 0;
        for (int i = 0; i < chemin.size() - 1; i++) {
            String from = chemin.get(i);
            String to = chemin.get(i + 1);
            total += arcs.get(from).get(to);
        }
        return total;
    }

    static class Noeud {
        String nom;
        int dist;
        Noeud(String nom, int dist) { this.nom = nom; this.dist = dist; }
    }
}
