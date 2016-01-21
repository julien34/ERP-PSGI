package vente;

import java.sql.Date;

public class Commande {

	String refCommande, refClient, nomClient, montantTotal, etatCommande, typePaiement;
	private Date date;
	
	public Commande(String refCommande,String refClient, String nomClient, String montantTotal, String etatCommande,String typePaiement){
		this.refCommande = refCommande;
		this.refClient = refClient;
		this.nomClient = nomClient;
		this.montantTotal = montantTotal;
		this.etatCommande = etatCommande;
		this.typePaiement = typePaiement;
	}

	public String getMontantTotal() {
		return montantTotal;
	}

	public void setMontantTotal(String montantTotal) {
		this.montantTotal = montantTotal;
	}
	
	public String getRefCommande() {
		return refCommande;
	}
	
	public String getNomClient() {
		return nomClient;
	}

	public void setNomFourniseur(String nomClient) {
		this.nomClient = nomClient;
	}

	public void setRefCommande(String refCommande) {
		this.refCommande = refCommande;
	}

	public String getRefClient() {
		return refClient;
	}

	public void setRefClient(String refClient) {
		this.refClient = refClient;
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
	
	
}


