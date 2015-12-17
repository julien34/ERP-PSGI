package vente;


public class Commande {
	String description, categorie, prixVente, prixAchat,udm;
	

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrixVente(String prixVente) {
		this.prixVente = prixVente;
	}

	public void setPrixAchat(String prixAchat) {
		this.prixAchat = prixAchat;
	}

	public void setUdm(String udm) {
		this.udm = udm;
	}
	
	public String getCategorie() {
		return categorie;
	}
	
	public String getDescription() {
		return description;
	}

	public String getPrixVente() {
		return prixVente;
	}

	public String getPrixAchat() {
		return prixAchat;
	}

	public String getUdm() {
		return udm;
	}

	public  Commande(String description,String categorie,String prixVente,String prixAchat,String udm){
		this.description = description;
		this.categorie = categorie;
		this.prixVente = prixVente;
		this.prixAchat = prixAchat;
		this.udm = udm;

	}
	
	
}

