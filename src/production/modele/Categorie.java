package production.modele;

public class Categorie {
	private int code;
	private String nom;
	private Categorie parent;
	
	public Categorie(int code, String nom, Categorie parent) {
		super();
		this.code = code;
		this.nom = nom;
		this.parent = parent;
	}
	
	public int getCode() {
		return code;
	}
	public String getNom() {
		return nom;
	}
	public Categorie getParent() {
		return parent;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public void setParent(Categorie parent) {
		this.parent = parent;
	}

	
}
