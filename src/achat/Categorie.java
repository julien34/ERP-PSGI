package achat;

public class Categorie {
	private String id, nom;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Categorie(String id, String nom){
		this.nom = nom;
		this.id = id;
	}
}
