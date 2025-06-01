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

    /**
     * Permet de convertir le nom du pokémon acheteur en le nom de sa ville.
     * @return Le nom de la ville où réside le pokémon acheteur.
     */
    public String getVilleAcheteur() {
        return ConversionVilles.convertirPokemon(acheteur);
    }

    /**
     * Permet de convertir le nom du pokémon vendeur en le nom de sa ville.
     * @return Le nom de la ville où réside le pokémon vendeur.
     */
    public String getVilleVendeur() {
        return ConversionVilles.convertirPokemon(vendeur);
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
