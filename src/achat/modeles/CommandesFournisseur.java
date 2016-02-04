package achat.modeles;

import java.sql.Date;

public class CommandesFournisseur {
	
	private String refCommande, refFournisseur, nomFourniseur, montantTotal, etatCommande, typePaiement;
	Double tauxTva, remise;
	private Date date, dateLivr;

	public CommandesFournisseur(String refCommande, Date date, String refFournisseur, String nomFournisseur, String montantTotal, String etatCommande, double tauxTva, double remise, Date dateLivr, String typePaiement){
		this.refCommande = refCommande;
		this.date = date;
		this.refFournisseur = refFournisseur;
		this.nomFourniseur = nomFournisseur;
		this.montantTotal = montantTotal;
		this.etatCommande = etatCommande;
		this.typePaiement = typePaiement;
		this.tauxTva = tauxTva;
		this.remise = remise;
		this.dateLivr = dateLivr;
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

	public String getTypePaiement() {
		return typePaiement;
	}

	public void setTypePaiement(String typePaiement) {
		this.typePaiement = typePaiement;
	}

	public Double getTauxTva() {
		return tauxTva;
	}

	public void setTauxTva(Double tauxTva) {
		this.tauxTva = tauxTva;
	}

	public Double getRemise() {
		return remise;
	}

	public void setRemise(Double remise) {
		this.remise = remise;
	}

	public Date getDateLivr() {
		return dateLivr;
	}

	public void setDateLivr(Date dateLivr) {
		this.dateLivr = dateLivr;
	}

	public void setEtatCommande(String etatCommande) {
		this.etatCommande = etatCommande;
	}
	
	
}
