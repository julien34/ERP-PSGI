package vente.model;

import java.sql.Date;
import java.util.ArrayList;

public class Commande {

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Commande [idCommande=" + idCommande + ", idClient=" + idClient + ", nomClient=" + nomClient
				+ ", etatCommande=" + etatCommande + ", montantTotal=" + montantTotal + ", typePaiement=" + typePaiement
				+ ", date=" + date + ", tauxTVA=" + tauxTVA + ", remise=" + remise + ", lignesCommande="
				+ lignesCommande + "]";
	}

	private String idCommande, idClient, nomClient, etatCommande, montantTotal, typePaiement;
	private Date date;
	private float tauxTVA, remise;
	private ArrayList<LignesCommande> lignesCommande;
	
	public Commande(String refCommande,String refClient,String nomClient, Date date, String etatCommande, String montantTotal,float txTVA, float remis, String typePaiement){
		this.idCommande = refCommande;
		this.idClient = refClient;
		this.nomClient = nomClient;
		this.montantTotal = montantTotal;
		this.etatCommande = etatCommande;
		this.typePaiement = typePaiement;
		this.date = date;
		this.tauxTVA = txTVA;
		this.remise = remis;
	} 

	public Commande(){
		this.idCommande = null;
		this.idClient = null;
		this.montantTotal = null;
		this.etatCommande = null;
		this.typePaiement = null;
		this.date = null;
		this.tauxTVA = 0;
		this.remise = 0;
	}
	/**
	 * @return the idCommande
	 */
	public String getIdCommande() {
		return idCommande;
	}

	/**
	 * @param idCommande the idCommande to set
	 */
	public void setIdCommande(String idCommande) {
		this.idCommande = idCommande;
	}

	/**
	 * @return the idClient
	 */
	public String getIdClient() {
		return idClient;
	}

	/**
	 * @param idClient the idClient to set
	 */
	public void setIdClient(String idClient) {
		this.idClient = idClient;
	}

	/**
	 * @return the tauxTVA
	 */
	public float getTauxTVA() {
		return tauxTVA;
	}

	/**
	 * @param tauxTVA the tauxTVA to set
	 */
	public void setTauxTVA(float tauxTVA) {
		this.tauxTVA = tauxTVA;
	}

	/**
	 * @return the remise
	 */
	public float getRemise() {
		return remise;
	}

	/**
	 * @param remise the remise to set
	 */
	public void setRemise(float remise) {
		this.remise = remise;
	}

	public String getMontantTotal() {
		return montantTotal;
	}

	public void setMontantTotal(String montantTotal) {
		this.montantTotal = montantTotal;
	}
	
	public String getRefCommande() {
		return idCommande;
	}

	public void setRefCommande(String refCommande) {
		this.idCommande = refCommande;
	}

	public String getRefClient() {
		return idClient;
	}

	public void setRefClient(String refClient) {
		this.idClient = refClient;
	}
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getEtatCommande(){
		return this.etatCommande;
	}

	public String getTypePaiement() {
		return typePaiement;
	}

	public void setTypePaiement(String typePaiement) {
		this.typePaiement = typePaiement;
	}


	public void setEtatCommande(String etatCommande) {
		this.etatCommande = etatCommande;
	}

	/**
	 * @return the lignesCommande
	 */
	public ArrayList<LignesCommande> getLignesCommande() {
		return lignesCommande;
	}

	/**
	 * @param lignesCommande the lignesCommande to set
	 */
	public void setLignesCommande(ArrayList<LignesCommande> lignesCommande) {
		this.lignesCommande = lignesCommande;
	}
	
	
}


