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
	private Double tauxTVA, remise;
	private ArrayList<LignesCommande> lignesCommande;
	
	public Commande(String refCommande,String refClient,String nomClient, Date date, String etatCommande, String montantTotal,double tauxTva2, double remise2, String typePaiement){
		this.idCommande = refCommande;
		this.idClient = refClient;
		this.nomClient = nomClient;
		this.montantTotal = montantTotal;
		this.etatCommande = etatCommande;
		this.typePaiement = typePaiement;
		this.date = date;
		this.tauxTVA = tauxTva2;
		this.remise = remise2;
	} 

	public Commande(){
		this.idCommande = null;
		this.idClient = null;
		this.montantTotal = null;
		this.etatCommande = null;
		this.typePaiement = null;
		this.date = null;
		this.tauxTVA = 0.0;
		this.remise = 0.0;
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
	public Double getTauxTVA() {
		return tauxTVA;
	}

	/**
	 * @param tauxTVA the tauxTVA to set
	 */
	public void setTauxTVA(Double tauxTVA) {
		this.tauxTVA = tauxTVA;
	}

	/**
	 * @return the remise
	 */
	public Double getRemise() {
		return remise;
	}

	/**
	 * @param remise the remise to set
	 */
	public void setRemise(Double remise) {
		this.remise = remise;
	}

	/**
	 * @return the nomClient
	 */
	public String getNomClient() {
		return nomClient;
	}

	/**
	 * @param nomClient the nomClient to set
	 */
	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}

	/**
	 * @return the etatCommande
	 */
	public String getEtatCommande() {
		return etatCommande;
	}

	/**
	 * @param etatCommande the etatCommande to set
	 */
	public void setEtatCommande(String etatCommande) {
		this.etatCommande = etatCommande;
	}

	/**
	 * @return the montantTotal
	 */
	public String getMontantTotal() {
		return montantTotal;
	}

	/**
	 * @param montantTotal the montantTotal to set
	 */
	public void setMontantTotal(String montantTotal) {
		this.montantTotal = montantTotal;
	}

	/**
	 * @return the typePaiement
	 */
	public String getTypePaiement() {
		return typePaiement;
	}

	/**
	 * @param typePaiement the typePaiement to set
	 */
	public void setTypePaiement(String typePaiement) {
		this.typePaiement = typePaiement;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @param lignesCommande the lignesCommande to set
	 */
	public void setLignesCommande(ArrayList<LignesCommande> lignesCommande) {
		this.lignesCommande = lignesCommande;
	}

	/**
	 * @return the lignesCommande
	 */
	public ArrayList<LignesCommande> getLignesCommande() {
		return lignesCommande;
	}

	
	
}


