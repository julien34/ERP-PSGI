package achat.modeles;

public class Produit {
	private String code, description, codeCat, nomCat;
	private Double prix;
	
	
	/**
	 * Constructeur d'un produit.
	 * @param code, le code du produit.
	 * @param desc, la description du produit.
	 * @param prix, le prix du produit.
	 * @param codeCat, le code de sa catégorie.
	 * @param nomCat, le nom de sa catégorie.
	 */
	public Produit(String code, String desc, Double prix, String codeCat, String nomCat){
		this.code = code;
		this.description = desc;
		this.prix = prix;
		this.codeCat = codeCat;
		this.nomCat = nomCat;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getCodeCat() {
		return codeCat;
	}


	public void setCodeCat(String codeCat) {
		this.codeCat = codeCat;
	}


	public String getNomCat() {
		return nomCat;
	}


	public void setNomCat(String nomCat) {
		this.nomCat = nomCat;
	}


	public Double getPrix() {
		return prix;
	}


	public void setPrix(Double prix) {
		this.prix = prix;
	}
	
}
