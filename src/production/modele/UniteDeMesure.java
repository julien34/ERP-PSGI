package production.modele;

public class UniteDeMesure {
	private int code;
	private String nom;
	
	public UniteDeMesure(int code, String nom) {
		super();
		this.code = code;
		this.nom = nom;
	}
	public int getCode() {
		return code;
	}
	public String getNom() {
		return nom;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
}