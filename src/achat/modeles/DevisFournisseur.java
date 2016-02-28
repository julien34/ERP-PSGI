package achat.modeles;

import java.sql.Date;

public class DevisFournisseur {
	private Integer refDevis;
	private String refFournisseur, nomFourniseur, montantTotal, etatDevis, typePaiement, refCommande;
	Double tauxTva, remise;
	private Date date;
	public DevisFournisseur(Integer refDevis, Date date, String refFournisseur, String nomFournisseur, String montantTotal, String etatCommande, double tauxTva, double remise, String typePaiement, String refCommande){
		this.refDevis = refDevis;
		this.date = date;
		this.refFournisseur = refFournisseur;
		this.nomFourniseur = nomFournisseur;
		this.montantTotal = montantTotal;
		this.etatDevis = etatCommande;
		this.typePaiement = typePaiement;
		this.tauxTva = tauxTva;
		this.remise = remise;
		this.refCommande= refCommande;
	}
	public String getRefCommande() {
		return refCommande;
	}
	public void setRefCommande(String refCommande) {
		this.refCommande = refCommande;
	}
	public Integer getRefDevis() {
		return refDevis;
	}
	public void setRefDevis(Integer refDevis) {
		this.refDevis = refDevis;
	}
	public String getRefFournisseur() {
		return refFournisseur;
	}
	public void setRefFournisseur(String refFournisseur) {
		this.refFournisseur = refFournisseur;
	}
	public String getNomFourniseur() {
		return nomFourniseur;
	}
	public void setNomFourniseur(String nomFourniseur) {
		this.nomFourniseur = nomFourniseur;
	}
	public String getMontantTotal() {
		return montantTotal;
	}
	public void setMontantTotal(String montantTotal) {
		this.montantTotal = montantTotal;
	}
	public String getEtatDevis() {
		return etatDevis;
	}
	public void setEtatDevis(String etatDevis) {
		this.etatDevis = etatDevis;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
