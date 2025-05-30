package modele;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class ConstValues {
    private static List<String> CONST_SCENARIOS = importScenarios();

    private static List<String> importScenarios() {
        List<String> scenarios = new ArrayList<>();

        for (File file : new File("data/scenarios").listFiles()) {
            scenarios.add(file.getName());
        }
        return scenarios;
    }

    public static List<String> updateScenarios() {
        CONST_SCENARIOS = importScenarios();
        return CONST_SCENARIOS;
    }

    public static List<String> getScenarios() {
        return CONST_SCENARIOS;
    }

}
