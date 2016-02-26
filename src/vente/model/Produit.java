package vente.model;

public class Produit {
	private String numProduit,nomProduit , prixProduit, prixHorsTaxe;
	private int quantiteProduit;
	

	public Produit(String numProduit, String nomProduit,int quantiteProduit, String prixProduit, String prixHorsTaxe){
		this.numProduit = numProduit;
		this.nomProduit = nomProduit;
		this.quantiteProduit = quantiteProduit;
		this.prixProduit = prixProduit;
		this.prixHorsTaxe = prixHorsTaxe;
	}


	public String getnumProduit() {
		return numProduit;
	}


	public void setnumProduit(String numProduit) {
		this.numProduit = numProduit;
	}


	public String getnomProduit() {
		return nomProduit;
	}


	public void setnomProduit(String nomProduit) {
		this.nomProduit = nomProduit;
	}


	public int quantiteProduit() {
		return quantiteProduit;
	}


	public void setquantiteProduit(int quantiteProduit) {
		this.quantiteProduit = quantiteProduit;
	}


	public String getprixProduit() {
		return prixProduit;
	}


	public void setprixProduit(String prixProduit) {
		this.prixProduit = prixProduit;
	}


	public String getprixHorsTaxe() {
		return prixHorsTaxe;
	}


	public void setPrix(String prixHorsTaxe) {
		this.prixHorsTaxe= prixHorsTaxe;
	}
	
}