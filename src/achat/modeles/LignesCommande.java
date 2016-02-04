package achat.modeles;

public class LignesCommande {
	private String refProduit, nomproduit, categorieProduit;
	private int qte;
	private float d;
	private double pHT, total;
	
	public LignesCommande(String refProduit, String nomProduit, String categorieProduit, double pHT, int qte, double total){
		this.refProduit = refProduit;
		this.nomproduit = nomProduit;
		this.categorieProduit = categorieProduit;
		this.pHT = pHT;
		this.qte = qte;
		this.total = total;
	}

	public String getRefProduit() {
		return refProduit;
	}

	public void setRefProduit(String refProduit) {
		this.refProduit = refProduit;
	}

	public String getNomproduit() {
		return nomproduit;
	}

	public void setNomproduit(String nomproduit) {
		this.nomproduit = nomproduit;
	}

	public String getCategorieProduit() {
		return categorieProduit;
	}

	public void setCategorieProduit(String categorieProduit) {
		this.categorieProduit = categorieProduit;
	}

	public double getpHT() {
		return pHT;
	}

	public void setpHT(int pHT) {
		this.pHT = pHT;
	}

	public int getQte() {
		return qte;
	}

	public void setQte(int qte) {
		this.qte = qte;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
