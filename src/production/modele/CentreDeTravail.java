package production.modele;

public class CentreDeTravail {
	private String nom;
	private int code;
	private String type;
	private float capacite_cycle;
	private float temps_cycle;
	
	public CentreDeTravail(int code, String nom, String type, float capacite_cycle, float temps_cycle) {
		super();
		this.nom = nom;
		this.code = code;
		this.type = type;
		this.capacite_cycle = capacite_cycle;
		this.temps_cycle = temps_cycle;
	}
	
	public String getNom() {
		return nom;
	}
	public int getCode() {
		return code;
	}
	public String getType() {
		return type;
	}
	public float getCapaciteCycle() {
		return capacite_cycle;
	}
	public float getTempsCycle() {
		return temps_cycle;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setCapaciteCycle(float capacite_cycle) {
		this.capacite_cycle = capacite_cycle;
	}
	public void setTempsCycle(float temps_cycle) {
		this.temps_cycle = temps_cycle;
	}	
}
