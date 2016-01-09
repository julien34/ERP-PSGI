package achat;

import java.sql.Date;

public class CommandesFournisseur {
	
	private String refCommande, refFournisseur, nomFourniseur, montantTotal, etatCommande;
	private Date date;

	public CommandesFournisseur(String refCommande, Date date, String refFournisseur, String nomFournisseur, String montantTotal, String etatCommande){
		this.refCommande = refCommande;
		this.date = date;
		this.refFournisseur = refFournisseur;
		this.nomFourniseur = nomFournisseur;
		this.montantTotal = montantTotal;
		this.etatCommande = etatCommande;
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

	public void setRefCommande(String refCommande) {
		this.refCommande = refCommande;
	}

	public String getRefFournisseur() {
		return refFournisseur;
	}

	public void setRefFournisseur(String refFournisseur) {
		this.refFournisseur = refFournisseur;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNomFourniseur() {
		return nomFourniseur;
	}

	public void setNomFourniseur(String nomFourniseur) {
		this.nomFourniseur = nomFourniseur;
	}
	
	public String getEtatCommande(){
		return this.etatCommande;
	}
}
