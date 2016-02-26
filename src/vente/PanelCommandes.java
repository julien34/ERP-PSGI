package vente;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import achat.modeles.CommandesFournisseur;
import achat.modeles.Fournisseur;
import achat.modeles.UneditableTableModel;
import achat.vues.popup.popCommande.PopupCommande;
import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;
import vente.model.Commande;

public class PanelCommandes extends JPanel{

	public static ArrayList<Commande> listeCommandes = new ArrayList<Commande>();
	private static Object[][] tabCo;
	private static Object[] titres = {"N� Commande","Acheteur","Date", "Montant", "Etat"};

	private static JTable tableau = new JTable(new DefaultTableModel());
	private static UneditableTableModel modele = new UneditableTableModel(0,5);
	private static JScrollPane scrollPane  = new JScrollPane(tableau);

	private static JButton btnNouveau, btnModifier, btnAnnuler;
	private static JTextField txtRechercheCommande, txtRechercheClient, txtRechercheMontant;

	//private Commande commande;
	//	private static JDateChooser jdcDate;
	//	private static String dateRecherche = "";


	public PanelCommandes(FenetrePrincipale f){

		//getCommande();
		//remplirTableau();

		this.initElements();

	}

	public static ArrayList<Commande> getListeCommande(){
		return listeCommandes;
	}

	public static void getCommande() {
		listeCommandes.clear();//On efface l'arraylist pour éviter d'ajouter une deuxième fois les éléments
		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("SELECT co.idCommande, co.dateCommande, co.tauxTVA, co.remise, co.typePaiement, cli.idClient, cli.nomClient, SUM(p.prixAchat*lc.quantite) AS montantTotal FROM vente_Commande co LEFT JOIN vente_clients cli ON cli.idClient = co.idClient LEFT JOIN vente_LigneCommande lc ON lc.idCommande = co.idCommande LEFT JOIN Produit p ON p.code = lc.codeProduit GROUP BY co.idCommande, co.dateCommande, co.tauxTVA, co.remise, co.typePaiement, cli.idClient, cli.nomCLient ORDER BY co.dateCommande DESC");
			ResultSet rs = pst.executeQuery();

			while(rs.next()){
				String refCommande, refFournisseur , nomFournisseur, montantTotal, etatCommande, typePaiement;
				float tauxTva, remise;
				Date date;

				refCommande = rs.getString("idCommande");
				date = rs.getDate("dateCommande");
				refFournisseur = rs.getString("idClient");
				nomFournisseur = rs.getString("nomClient");
				//etatCommande = rs.getString("etatCommande");
				etatCommande = "";
				tauxTva = rs.getFloat("tauxTva");
				remise = rs.getFloat("remise");
				typePaiement = rs.getString("typePaiement");


				//Montant total non vide
				if (rs.getString("montantTotal") == null){
					montantTotal = "Pas de produits";
				}
				else {
					montantTotal = rs.getString("montantTotal")+" €";
				}

				listeCommandes.add(new Commande(refCommande, refFournisseur, nomFournisseur, date, etatCommande,montantTotal, tauxTva,remise,typePaiement ));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void remplirTableau(){

		maj();

		tableau = new JTable(modele);
		tableau.setAutoCreateRowSorter(false);
		tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableau.setPreferredScrollableViewportSize(new Dimension(800, 224));

		scrollPane.setViewportView(tableau);
	}

	public static void maj(){
		int nbDeCo = listeCommandes.size();//On calcule la taille de l'arrylist pour cr�er un tableau ad�quat
		tabCo = new Object[nbDeCo][5];//On cr�er le tableau de la taille r�cup�r�e 

		//On remplit ce dernier avec les CommandesFournisseur r�cup�r�es
		for(Commande cf : listeCommandes){
			tabCo[listeCommandes.indexOf(cf)][0] = cf.getRefCommande();
			tabCo[listeCommandes.indexOf(cf)][1] = cf.getIdClient();
			tabCo[listeCommandes.indexOf(cf)][2] = cf.getDate();
			tabCo[listeCommandes.indexOf(cf)][3] = cf.getMontantTotal();
			tabCo[listeCommandes.indexOf(cf)][4] = cf.getEtatCommande();
		}
		modele.setDataVector(tabCo,titres);
	}


	private void initElements(){

		this.setLayout(new BorderLayout());

		JPanel panelRechercheNord = new JPanel();

		JPanel panelGrilleCentre = new JPanel(new GridLayout(2,1));
		JPanel panelGrille = new JPanel();
		JPanel panelBouton = new JPanel();


		JLabel lblRechercheCommande = new JLabel("N� Commande : ");
		txtRechercheCommande = new JTextField(10);
		JLabel lblRechercheFournisseur = new JLabel("Client : ");
		txtRechercheClient = new JTextField(10);


		btnNouveau= new JButton("Nouveau");
		btnModifier = new JButton("Modifier");
		btnAnnuler = new JButton("Annuler");



		panelRechercheNord.add(lblRechercheCommande);
		panelRechercheNord.add(txtRechercheCommande);
		panelRechercheNord.add(lblRechercheFournisseur);
		panelRechercheNord.add(txtRechercheClient);

		panelGrille.add(scrollPane);
		panelBouton.add(btnNouveau);
		panelBouton.add(btnModifier);
		panelBouton.add(btnAnnuler);

		panelGrilleCentre.add(panelGrille);
		panelGrilleCentre.add(panelBouton);

		this.add(panelRechercheNord, BorderLayout.NORTH);
		this.add(panelGrilleCentre, BorderLayout.CENTER);

		btnNouveau.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				//commande = new Commande();
				new FenetreVente();
			}
		});
	}  




}
