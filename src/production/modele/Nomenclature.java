package production.modele;

public class Nomenclature {
	private int code;
	private String nom;
	private float quantite;
	private String type;
	private Gamme gamme;
	private Produit produit;
	
	public Nomenclature(int code, String nom, float quantite, String type, Gamme gamme, Produit produit) {
		super();
		this.code = code;
		this.nom = nom;
		this.quantite = quantite;
		this.type = type;
		this.gamme = gamme;
		this.produit = produit;
	}

	public int getCode() {
		return code;
	}
	
	public String getNom() {
		return nom;
	}
	
	public float getQuantite() {
		return quantite;
	}

	public String getType() {
		return type;
	}

	public Gamme getGamme() {
		return gamme;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public void setQuantite(float quantite) {
		this.quantite = quantite;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setGamme(Gamme gamme) {
		this.gamme = gamme;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}	
}
