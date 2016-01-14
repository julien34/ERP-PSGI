package achat;

public class Fournisseur {
	String ref, nom, siret, tel, adresse, categorie;
	
	
	public Fournisseur(String ref, String nom, String siret, String tel, String adresse, String categorie){
		this.ref = ref;
		this.nom = nom;
		this.siret = siret;
		this.tel = tel;
		this.adresse = adresse;
		this.categorie = categorie;
	}
	
	public Fournisseur(String ref, String nom){
		this.ref = ref;
		this.nom = nom;
	}
	
	public Fournisseur(){
		this.ref = "";
		this.nom = "";
	}
	
	
	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setSiret(String siret) {
		this.siret = siret;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getRef() {
		return ref;
	}

	public String getNom() {
		return nom;
	}

	public String getSiret() {
		return siret;
	}

	public String getTel() {
		return tel;
	}

	public String getAdresse() {
		return adresse;
	}
	
}
