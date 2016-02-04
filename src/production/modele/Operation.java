package production.modele;

public class Operation {
	private int code;
	private String nom;
	private int sequence;
	private float nombre_cycle;
	private float nombre_heure;
	private Gamme gamme;
	private CentreDeTravail centre_de_travail;
	
	public Operation(int code, String nom, int sequence, float nombre_cycle, float nombre_heure, Gamme gamme,
			CentreDeTravail centre_de_travail) {
		super();
		this.code = code;
		this.nom = nom;
		this.sequence = sequence;
		this.nombre_cycle = nombre_cycle;
		this.nombre_heure = nombre_heure;
		this.gamme = gamme;
		this.centre_de_travail = centre_de_travail;
	}
	
	public int getCode() {
		return code;
	}
	public String getNom() {
		return nom;
	}
	public int getSequence() {
		return sequence;
	}
	public float getNombreCycle() {
		return nombre_cycle;
	}
	public float getNombreHeure() {
		return nombre_heure;
	}
	public Gamme getGamme() {
		return gamme;
	}
	public CentreDeTravail getCentreDeTravail() {
		return centre_de_travail;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public void setNombreDeCycle(float nombre_cycle) {
		this.nombre_cycle = nombre_cycle;
	}
	public void setNombreHeure(float nombre_heure) {
		this.nombre_heure = nombre_heure;
	}
	public void setGamme(Gamme gamme) {
		this.gamme = gamme;
	}
	public void setCentreDeTravail(CentreDeTravail centre_de_travail) {
		this.centre_de_travail = centre_de_travail;
	}
	
	
}
