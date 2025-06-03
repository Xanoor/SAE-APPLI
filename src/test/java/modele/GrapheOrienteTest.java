package modele;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GrapheOrienteTest {
    private GrapheOriente grapheOriente;
    private GrapheOriente grapheOriente2;
    private GrapheOriente grapheOriente3;
    private Map<String, List<String>> graphe1;
    private Map<String, List<String>> graphe2;
    private Map<String, List<String>> graphe3;

    @BeforeEach
    void setUp() {
        graphe1 = new HashMap<>();
        graphe2 = new HashMap<>();
        graphe3 = new HashMap<>();

        graphe1.put("a", List.of("b", "c"));
        graphe1.put("b", List.of("a"));
        graphe1.put("c", List.of("d", "a", "b"));
        graphe1.put("d", List.of());

        graphe3.put("a", List.of("b"));
        graphe3.put("b", List.of("c"));
        graphe3.put("d", List.of("b"));

        grapheOriente = new GrapheOriente(graphe1);
        grapheOriente2 = new GrapheOriente(graphe2);
        grapheOriente3 = new GrapheOriente(graphe3);
    }

    @Test
    void listeSommets() {
        assertEquals(List.of("a", "b", "c", "d"), grapheOriente.listeSommets(), "Erreur lors de la récupération des sommets pour un graphe non vide.");
        assertEquals(List.of(), grapheOriente2.listeSommets(), "Erreur lors de la récupération des sommet pour un graphe vide.");
    }

    @Test
    void ordre() {
        assertEquals(4, grapheOriente.ordre(), "Erreur lors de la récupération de l'ordre d'un graphe non vide.");
        assertEquals(0, grapheOriente2.ordre(), "Erreur lors de la récupération de l'ordre d'un graphe vide.");
    }

    @Test
    void degre() {
        assertEquals(0, grapheOriente.degre("z"), "Erreur lors de la récupération du nombre de degrés pour un sommet inexistant.");
        assertEquals(2, grapheOriente.degre("a"), "Erreur lors de la récupération du nombre de degrés pour un sommet existant et d'un degré non nul.");
        assertEquals(0, grapheOriente.degre("d"), "Erreur lors de la récupération du nombre de degrés pour un sommet existant et d'un degré nul.");
    }

    @Test
    void taille() {
        assertEquals(0, grapheOriente2.taille(), "Erreur lors de la récupération de la taille d'un graphe vide.");
        assertEquals(6, grapheOriente.taille(), "Erreur lors de la récupération de la taille d'un graphe non vide.");
    }

    @Test
    void degreMinimal() {
        assertEquals(0, grapheOriente.degreMinimal(), "Erreur lors de la récupération du nombre de degré minimal pour un graphe non vide et de degré minimal 0..");
        assertEquals(0, grapheOriente2.degreMinimal(), "Erreur lors de la récupération du nombre de degré minimal pour un graphe vide");
        assertEquals(1, grapheOriente3.degreMinimal(), "Erreur lors de la récupération du nombre de degré minimal pour un graphe non vide et de degré minimal non nul.");
    }

    @Test
    void degreMaximal() {
        assertEquals(3, grapheOriente.degreMaximal(), "Erreur lors de la récupération du nombre de degré maximal pour un graphe non vide.");
        assertEquals(0, grapheOriente2.degreMaximal(), "Erreur lors de la récupération du nombre de degré maximal pour un graphe vide.");
    }

    @Test
    void triTopologiqueSimple() {
        assertEquals(List.of("a", "d", "b", "c"), grapheOriente3.triTopologiqueSimple(), "Erreur lors d'un tri topologique possible sur un graphe non vide.");
        assertEquals(List.of(), grapheOriente2.triTopologiqueSimple(), "Erreur lors d'un tri topologique impossible sur un graphe vide.");
        assertEquals(List.of(), grapheOriente.triTopologiqueSimple(), "Erreur lors d'un tri topologique impossible sur un graphe non vide.");
    }
}