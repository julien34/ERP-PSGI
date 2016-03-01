package vente.model;

public class Produit {
	private String numProduit,nomProduit , prixProduit, prixHorsTaxe;
	private int quantiteProduit;
	

	/**
	 * @param numProduit
	 * @param nomProduit
	 * @param quantiteProduit
	 * @param prixProduit
	 * @param prixHorsTaxe
	 */
	public Produit(String numProduit, String nomProduit,int quantiteProduit, String prixProduit, String prixHorsTaxe){
		this.numProduit = numProduit;
		this.nomProduit = nomProduit;
		this.quantiteProduit = quantiteProduit;
		this.prixProduit = prixProduit;
		this.prixHorsTaxe = prixHorsTaxe;
	}


	/**
	 * @return numProduit
	 */
	public String getnumProduit() {
		return numProduit;
	}


	/**
	 * @param numProduit
	 */
	public void setnumProduit(String numProduit) {
		this.numProduit = numProduit;
	}


	/**
	 * @return nomProduit
	 */
	public String getnomProduit() {
		return nomProduit;
	}


	/**
	 * @param nomProduit
	 */
	public void setnomProduit(String nomProduit) {
		this.nomProduit = nomProduit;
	}


	/**
	 * @return quantiteProduit
	 */
	public int quantiteProduit() {
		return quantiteProduit;
	}


	/**
	 * @param quantiteProduit
	 */
	public void setquantiteProduit(int quantiteProduit) {
		this.quantiteProduit = quantiteProduit;
	}

	/**
	 * @return prixProduit
	 */
	public String getprixProduit() {
		return prixProduit;
	}

	/**
	 * @param prixProduit
	 */
	public void setprixProduit(String prixProduit) {
		this.prixProduit = prixProduit;
	}

	/**
	 * @return prixHorsTaxe
	 */
	public String getprixHorsTaxe() {
		return prixHorsTaxe;
	}

	/**
	 * @param prixHorsTaxe
	 */
	public void setPrix(String prixHorsTaxe) {
		this.prixHorsTaxe= prixHorsTaxe;
	}
	
}