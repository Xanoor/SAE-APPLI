package modele;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DonneesScenariosTest {

    @Test
    void convertirDistances() {
        DonneesScenarios.convertirDistances();

        assertFalse(DonneesScenarios.tableDistances.isEmpty(), "tableDistances est vide !");
    }

    @Test
    void getDistance() {
        DonneesScenarios.convertirDistances();
        DonneesScenarios.convertirDistances();

        assertEquals(0, DonneesScenarios.getDistance("Paris", "Paris"), "Mauvaise distance pour la même ville (Paris-Paris).");
        assertEquals(862, DonneesScenarios.getDistance("Amiens", "Biarritz"), "Mauvaise distance pour Amiens-Biarritz.");
        assertEquals(0, DonneesScenarios.getDistance("Calais", "xxx"), "Distance entre une ville existante et une ville inexistante fausse.");
    }

    @Test
    void convertirVilles() {
        DonneesScenarios.convertirVilles();

        assertFalse(DonneesScenarios.tableDistances.isEmpty(), "La HashMap tableDistance est vide !");
        assertEquals("Nancy", DonneesScenarios.tableConversion.get("Élektek"), "Erreur lors de la conversion des pokémons en ville.");
        assertEquals(384, DonneesScenarios.tableConversion.size(), "La HashMap tableDistance ne contient pas le bon nombre de \"conversions\".");
    }

    @Test
    void convertirPokemon() {
        DonneesScenarios.convertirVilles();

        assertEquals("Amiens", DonneesScenarios.convertirPokemon("Rayquaza"), "Erreur lors de la récupération d'une ville avec le nom d'un pokémon.");
        assertEquals("NonExistant", DonneesScenarios.convertirPokemon("NonExistant"), "Erreur lors de la conversion d'un pokémon inconnu.");
    }
}