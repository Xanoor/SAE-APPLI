package modele;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class ConstValues {
    private static final List<String> CONST_SCENARIOS = importScenarios();

    private static List<String> importScenarios() {
        List<String> scenarios = new ArrayList<>();

        for (File file : new File("data/scenarios").listFiles()) {
            scenarios.add(file.getName());
        }
        return scenarios;
    }

    public static List<String> getScenarios() {
        return CONST_SCENARIOS;
    }

}
