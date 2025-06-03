package modele;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScenarioTest {
    private Scenario scenario;

    @BeforeEach
    void setUp() {
        scenario = new Scenario();
    }

    @Order(3)
    @Test
    void clear() {
        scenario.addTransaction("a", "b");
        scenario.addTransaction("c", "d");

        scenario.clear();
        assertEquals(0, scenario.getTransactions().size(), "Erreur lors de la suppression des transactions.");
    }

    @Order(0)
    @Test
    void addTransaction() {
        scenario.addTransaction("a", "b");

        assertEquals(1, scenario.getTransactions().size(), "Erreur lors de l'ajout d'une transaction.");
        assertSame("a", scenario.getTransactions().getFirst().getVendeur(), "Erreur dans l'ajout du vendeur pour la transaction.");
        assertSame("b", scenario.getTransactions().getFirst().getAcheteur(), "Erreur dans l'ajout de l'acheteur pour la transaction.");
    }

    @Order(2)
    @Test
    void setNomScenario() {
        scenario.setNomScenario("scenario0");
        assertEquals("scenario0", scenario.getNomScenario(), "Le nom du scénario est mal attribué !");
    }

    @Order(1)
    @Test
    void removeTransaction() {
        int taille = scenario.getTransactions().size();

        scenario.removeTransaction(scenario.getTransactions().getFirst());
        assertEquals(scenario.getTransactions().size(), taille - 1, "Problème lors de la suppression d'une transaction.");
    }

}