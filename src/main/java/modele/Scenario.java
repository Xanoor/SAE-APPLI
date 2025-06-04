package modele;

import java.util.ArrayList;
import java.util.List;


public class Scenario {
    private static String nomScenario;
    private static List<Transaction> transactions = new ArrayList<>();
    private static List<String> path = new ArrayList<>();
    private static List<ScenarioBruteForce.Solution> kpath = new ArrayList<>();
    private static int distance = 0;

    public void clear(){
        transactions.clear();
    }

    public void addTransaction(String vendeur, String acheteur) {
        transactions.add(new Transaction(vendeur, acheteur));
    }

    public void setNomScenario(String nomScenario) {
        this.nomScenario = nomScenario;
    }

    public String getNomScenario() {
        return this.nomScenario;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    public void setKpath(List<ScenarioBruteForce.Solution> kpath) {
        this.kpath = kpath;
    }

    public List<ScenarioBruteForce.Solution> getkPath() {
        return this.kpath;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public List<String> getPath() {
        return this.path;
    }

    public int getDistance() {
        return this.distance;
    }


    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction);
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public String toString() {
        return transactions.toString();
    }
}
