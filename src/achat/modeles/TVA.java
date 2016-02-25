package achat.modeles;

public class TVA {
	private String refTVA, nomTVA;
	private double tauxTVA; 
	
	public TVA(String ref, String nom, double taux) {
		this.refTVA = ref;
		this.nomTVA = nom;
		this.tauxTVA = taux;
	}

	/**
	 * @return the refTVA
	 */
	public String getRefTVA() {
		return refTVA;
	}

	/**
	 * @param refTVA the refTVA to set
	 */
	public void setRefTVA(String refTVA) {
		this.refTVA = refTVA;
	}

	/**
	 * @return the nomTVA
	 */
	public String getNomTVA() {
		return nomTVA;
	}

	/**
	 * @param nomTVA the nomTVA to set
	 */
	public void setNomTVA(String nomTVA) {
		this.nomTVA = nomTVA;
	}

	/**
	 * @return the tauxTVA
	 */
	public double getTauxTVA() {
		return tauxTVA;
	}

	/**
	 * @param tauxTVA the tauxTVA to set
	 */
	public void setTauxTVA(double tauxTVA) {
		this.tauxTVA = tauxTVA;
	}
}
