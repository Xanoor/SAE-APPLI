package modele;

import java.util.*;

public class GrapheOriente {
    private final TreeMap<String, Set<String>> voisinsSortant;

    public GrapheOriente(Map<String, List<String>> graph) {
        voisinsSortant = new TreeMap<>();
        for (String ville : graph.keySet()) {
            Set<String> voisins = new TreeSet<>();
            List<String> voisinsList = graph.get(ville);
            if (voisinsList != null) {
                for (String voisin : voisinsList) {
                    voisins.add(voisin);
                }
            }
            voisinsSortant.put(ville, voisins);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ordre : ").append(ordre()).append("\n");
        sb.append("Taille : ").append(taille()).append("\n");
        sb.append("DegreMin : ").append(degreMinimal()).append("\n");
        sb.append("DegreMax : ").append(degreMaximal()).append("\n");
        for (String ville : listeSommets()) {
            sb.append("sommet ").append(ville)
                    .append(" degre=").append(degre(ville))
                    .append(" voisins sortant: ").append(voisinsSortant.get(ville))
                    .append("\n");
        }
        return sb.toString();
    }

    /**
     * Permet de récupérer la liste des sommets.
     * @return l'ensemble des sommets.
     */
    public List<String> listeSommets() {
        return new ArrayList<String>(voisinsSortant.keySet());
    }

    /**
     * Permet de récupérer l'ordre d'un graphe.
     * @return renvoie l'ordre du graphe (int).
     */
    public int ordre() {
        return voisinsSortant.size();
    }

    /**
     * Permet de récupérer les degrés d'un sommet.
     * @param sommet Somme dont on veut récupérer l'ordre.
     * @return Renvoie l'ordre du sommet (int)
     */
    public int degre(String sommet) {
        if (voisinsSortant.containsKey(sommet)) {
            return voisinsSortant.get(sommet).size();
        }
        return 0;
    }

    /**
     * Permet de récupérer la taille d'un graphe.
     * @return Renvoie la taille du graphe.
     */
    public int taille() {
        int taille = 0;
        for (String ville : voisinsSortant.keySet()) {
            taille += degre(ville);
        }
        return taille;
    }

    /**
     * Permet de récupérer le degré minimum du graphe (sommet avec le plus petit degré).
     * @return Renvoie le degré minimal.
     */
    public int degreMinimal() {
        int min = Integer.MAX_VALUE;
        for (String ville : voisinsSortant.keySet()) {
            int d = degre(ville);
            if (d < min) {
                min = d;
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }

    /**
     * Permet de récupérer le degré maximal du graphe (sommet avec le plus grand degré)
     * @return Renvoie le degré maximal.
     */
    public int degreMaximal() {
        int max = 0;
        for (String ville : voisinsSortant.keySet()) {
            int d = degre(ville);
            if (d > max) {
                max = d;
            }
        }
        return max;
    }

    /**
     * Fonction de tri topologique (étudié en cours).
     * Permet d’ordonner les sommets du graphe en sélectionnant à chaque étape un sommet de degré entrant nul.
     * @return Renvoie l’ordre topologique sous forme de liste.
     */

    public List<String> triTopologiqueSimple() {
        List<String> ordre = new ArrayList<String>();
        Map<String, Integer> degreEntrant = getDegreEntrant();
        Queue<String> file = new LinkedList<String>();

        for (String v : voisinsSortant.keySet()) {
            if (degreEntrant.containsKey(v) && degreEntrant.get(v) == 0) {
                file.add(v);
            }
        }

        while (!file.isEmpty()) {
            String courant = file.poll();
            ordre.add(courant);

            Set<String> voisins = voisinsSortant.containsKey(courant) ? voisinsSortant.get(courant) : new HashSet<String>();
            for (String voisin : voisins) {
                int degre = degreEntrant.get(voisin);
                degreEntrant.put(voisin, degre - 1);
                if (degreEntrant.get(voisin) == 0) {
                    file.add(voisin);
                }
            }
        }

        return ordre;
    }

    private Map<String, Integer> getDegreEntrant() {
        Map<String, Integer> degreEntrant = new TreeMap<String, Integer>();
        for (String ville : voisinsSortant.keySet()) {
            degreEntrant.put(ville, 0);
        }

        for (String ville : voisinsSortant.keySet()) {
            Set<String> voisins = voisinsSortant.get(ville);
            for (String voisin : voisins) {
                int degre = degreEntrant.containsKey(voisin) ? degreEntrant.get(voisin) : 0;
                degreEntrant.put(voisin, degre + 1);
            }
        }

        return degreEntrant;
    }
}
