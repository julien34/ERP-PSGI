package achat.popup;

import java.awt.BorderLayout;
import java.awt.Choice;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import achat.CommandesFournisseur;

public class PopupCommande extends JDialog {
	
	CommandesFournisseur commande;
	
	/**
	 * Constructeur vide. Créer une nouvelle commande.
	 */
	public PopupCommande(){
		this.initFenetre();
		this.initElements();
	}
	
	
	/**
	 * Constructeur avec en paramètre une commande de type CommandesFournisseur. Modifie une commande existante.
	 * @param cmd, une commande de type CommandesFournisseur. Modifie la commande passée en paramètre.
	 */
	public PopupCommande(CommandesFournisseur cmd){
		this.commande = cmd;
		this.initFenetre();
		this.initElements();
	}
	
	
	/**
	 * Méthode qui initie la fenetre popup.
	 */
	private void initFenetre(){
		
		//On donne un titre selon la provenance du clic (si cmd en paramètre alors modification)
		if(this.commande == null){
			this.setTitle("Nouvelle commande d'achat");
		}
		else {
			this.setTitle("Modification de la commande d'achat n°"+this.commande.getRefCommande()+" du "+this.commande.getDate()+" ("+this.commande.getNomFourniseur()+")");
		}
		
		this.setSize(850, 650);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	/**
	 * Méthode qui initialise l'ensemble de tous les panels et composants de la fenetre.
	 */
	private void initElements(){
		
		//On créer les différents panels
		JPanel panelPrincipal = new JPanel(new BorderLayout());
		JPanel panelRechercheNord = new JPanel();
		
		//On créer les composants
		JLabel lblAjoutproduit = new JLabel("Ajouter un produit : ");
		JTextField txtNumArticle = new JTextField(10);
		Choice chCategorie = new Choice();
		chCategorie.addItem("");
		chCategorie.addItem("Aucune");
		chCategorie.addItem("Test");
		//this.initCategorie();
		JButton btnRechercher = new JButton("Rechercher");
		
		//On ajoute les composants à leur panel respectifs
		panelRechercheNord.add(lblAjoutproduit);
		panelRechercheNord.add(txtNumArticle);
		panelRechercheNord.add(chCategorie);
		panelRechercheNord.add(btnRechercher);
		
		//On ajoute les panel au panel principal
		panelPrincipal.add(panelRechercheNord, BorderLayout.NORTH);
		
		//On ajoute le panel principal à la fenetre
		this.add(panelPrincipal);
	}
}
