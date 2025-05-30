package modele;

import java.util.ArrayList;
import java.util.List;


public class Scenario {
    private static String nomScenario;
    private static List<Transaction> transactions = new ArrayList<>();
    private static List<String> vendeurs = new ArrayList<>();
    private static List<String> acheteurs = new ArrayList<>();

    public void clear(){
        transactions.clear();
        vendeurs.clear();
        acheteurs.clear();
    }

    public void addTransaction(String vendeur, String acheteur) {
        transactions.add(new Transaction(vendeur, acheteur));
        vendeurs.add(vendeur);
        acheteurs.add(acheteur);
    }

    public void setNomScenario(String nomScenario) {
        this.nomScenario = nomScenario;
    }

    public String getNomScenario() {
        return this.nomScenario;
    }


    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void removeTransaction(Transaction transaction) {
        acheteurs.remove(transaction.getAcheteur());
        vendeurs.remove(transaction.getVendeur());
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
