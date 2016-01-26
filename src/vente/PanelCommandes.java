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

import achat.CommandesFournisseur;
import achat.UneditableTableModel;
import achat.popup.PopupCommande;
import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class PanelCommandes extends JPanel{
	
	public static ArrayList<Commande> listeCommandes = new ArrayList<Commande>();
	private static Object[][] tabCo;
	private static Object[] titres = {"N° Commande","Acheteur","Date", "Montant", "Etat"};
	
	private static JTable tableau = new JTable(new DefaultTableModel());
	private static UneditableTableModel modele = new UneditableTableModel(0,5);
	private static JScrollPane scrollPane  = new JScrollPane(tableau);
	
	private static JButton btnNouveauParticulier , btnNouveauEntreprise, btnModifier, btnAnnuler;
	private static JTextField txtRechercheCommande, txtRechercheClient, txtRechercheMontant;
//	private static JDateChooser jdcDate;
//	private static String dateRecherche = "";
	

	public PanelCommandes(FenetrePrincipale f){
		
		getCommande();
		
		remplirTableau();
				
		this.initElements();
				
	}

	public static ArrayList<Commande> getListeCommande(){
		return listeCommandes;
	}
	
	
	
	public static void getCommande() {

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
		int nbDeCo = listeCommandes.size();//On calcule la taille de l'arrylist pour créer un tableau adéquat
		tabCo = new Object[nbDeCo][5];//On créer le tableau de la taille récupérée 
		
		//On remplit ce dernier avec les CommandesFournisseur récupérées
		for(Commande cf : listeCommandes){
			tabCo[listeCommandes.indexOf(cf)][0] = cf.getRefCommande();
			tabCo[listeCommandes.indexOf(cf)][1] = cf.getNomClient();
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
		
		
		JLabel lblRechercheCommande = new JLabel("N° Commande : ");
		txtRechercheCommande = new JTextField(10);
		JLabel lblRechercheFournisseur = new JLabel("Client : ");
		txtRechercheClient = new JTextField(10);

		
		btnNouveauParticulier = new JButton("nouveau PA");
		btnNouveauEntreprise = new JButton("nouveau EN");
		btnModifier = new JButton("Modifier");
		btnAnnuler = new JButton("Annuler");
		
		
		
		panelRechercheNord.add(lblRechercheCommande);
		panelRechercheNord.add(txtRechercheCommande);
		panelRechercheNord.add(lblRechercheFournisseur);
		panelRechercheNord.add(txtRechercheClient);

		panelGrille.add(scrollPane);
		panelBouton.add(btnNouveauEntreprise);
		panelBouton.add(btnNouveauParticulier);
		panelBouton.add(btnModifier);
		panelBouton.add(btnAnnuler);
		
		panelGrilleCentre.add(panelGrille);
		panelGrilleCentre.add(panelBouton);
		
		this.add(panelRechercheNord, BorderLayout.NORTH);
		this.add(panelGrilleCentre, BorderLayout.CENTER);
		
		
		
		
		
		btnNouveauEntreprise.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				 new FenetreVente(null);

			}
		});
		
		btnNouveauParticulier.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}  


	
	
}
