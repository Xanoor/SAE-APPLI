package modele;

public class Transaction {
    private String vendeur;
    private String acheteur;

    public Transaction(String vendeur, String acheteur) {
        this.vendeur = vendeur;
        this.acheteur = acheteur;
    }

    public String getVendeur() {
        return vendeur;
    }

    public String getAcheteur() {
        return acheteur;
    }

    public void setVendeur(String vendeur) {
        this.vendeur = vendeur;
    }

    public void setAcheteur(String acheteur) {
        this.acheteur = acheteur;
    }

    @Override
    public String toString() {
        return vendeur + "=" + acheteur;
    }
}
