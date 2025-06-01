package modele;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class ConstValues {
    private static List<String> CONST_SCENARIOS = importScenarios();

    /**
     * Méthode permettant de récupérer la liste des scénarios.
     *
     * @return Liste contenant le noms des scénarios.
     */
    private static List<String> importScenarios() {
        List<String> scenarios = new ArrayList<>();

        for (File file : new File("data/scenarios").listFiles()) {
            scenarios.add(file.getName());
        }
        return scenarios;
    }

    /**
     * Permet de mettre à jour la liste de des noms des scénarios (lors de la créations de nouveaux
     * scénarios par exemple).
     */
    public static void updateScenarios() {
        CONST_SCENARIOS = importScenarios();
    }

    public static List<String> getScenarios() {
        return CONST_SCENARIOS;
    }

}
