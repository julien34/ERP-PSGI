package production.modele;

public class Produit {
	private int code;
	private String nom;
	private Categorie categorie;
	private float prix_vente;
	private float prix_achat;
	private UniteDeMesure unite_de_mesure;
	private String disponibilite;
	
	public Produit(int code, String nom, Categorie categorie, float prix_achat, float prix_vente,
			UniteDeMesure unite_de_mesure, String disponibilite) {
		super();
		this.code = code;
		this.nom = nom;
		this.categorie = categorie;
		this.prix_vente = prix_vente;
		this.prix_achat = prix_achat;
		this.unite_de_mesure = unite_de_mesure;
		this.disponibilite = disponibilite;
	}
	
	public int getCode() {
		return code;
	}
	public String getNom() {
		return nom;
	}
	public Categorie getCategorie() {
		return categorie;
	}
	public float getPrixVente() {
		return prix_vente;
	}
	public float getPrixAchat() {
		return prix_achat;
	}
	public UniteDeMesure getUniteDeMesure() {
		return unite_de_mesure;
	}
	public String getDisponibilite() {
		return disponibilite;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	public void setPrixVente(float prix_vente) {
		this.prix_vente = prix_vente;
	}
	public void setPrixAchat(float prix_achat) {
		this.prix_achat = prix_achat;
	}
	public void setUniteDeMesure(UniteDeMesure unite_de_mesure) {
		this.unite_de_mesure = unite_de_mesure;
	}
	public void setDisponibilite(String disponibilite) {
		this.disponibilite = disponibilite;
	}	
}
