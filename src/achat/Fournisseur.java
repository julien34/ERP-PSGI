package achat;

public class Fournisseur {
	String ref, nom, siret, tel, adresse;
	
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

	public Fournisseur(String ref, String nom, String siret, String tel, String adresse){
		this.ref = ref;
		this.nom = nom;
		this.siret = siret;
		this.tel = tel;
		this.adresse = adresse;
	}
	
	
}
