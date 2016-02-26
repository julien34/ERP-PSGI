/**
 * 
 */
package vente.model;

/**
 * @author Simon
 *
 */
public class LignesCommande {
	private String idProduit, nomproduit, categorieProduit;
	private int qte;
	private float d;
	private double pHT, total;
	
	public LignesCommande(String refProduit, String nomProduit, String categorieProduit, double pHT, int qte, double total){
		this.idProduit = refProduit;
		this.nomproduit = nomProduit;
		this.categorieProduit = categorieProduit;
		this.pHT = pHT;
		this.qte = qte;
		this.total = total;
	}
	/**
	 * @return the idProduit
	 */
	public String getIdProduit() {
		return idProduit;
	}

	/**
	 * @param idProduit the idProduit to set
	 */
	public void setIdProduit(String idProduit) {
		this.idProduit = idProduit;
	}

	/**
	 * @return the nomproduit
	 */
	public String getNomproduit() {
		return nomproduit;
	}

	/**
	 * @param nomproduit the nomproduit to set
	 */
	public void setNomproduit(String nomproduit) {
		this.nomproduit = nomproduit;
	}

	/**
	 * @return the categorieProduit
	 */
	public String getCategorieProduit() {
		return categorieProduit;
	}

	/**
	 * @param categorieProduit the categorieProduit to set
	 */
	public void setCategorieProduit(String categorieProduit) {
		this.categorieProduit = categorieProduit;
	}

	/**
	 * @return the qte
	 */
	public int getQte() {
		return qte;
	}

	/**
	 * @param qte the qte to set
	 */
	public void setQte(int qte) {
		this.qte = qte;
	}

	/**
	 * @return the pHT
	 */
	public double getpHT() {
		return pHT;
	}

	/**
	 * @param pHT the pHT to set
	 */
	public void setpHT(double pHT) {
		this.pHT = pHT;
	}

	/**
	 * @return the total
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(double total) {
		this.total = total;
	}

}
