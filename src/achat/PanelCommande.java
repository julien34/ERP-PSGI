package achat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import achat.popup.PopupCommande;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JCalendarBeanInfo;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JDayChooser;
import com.toedter.plaf.JCalendarTheme;

import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class PanelCommande extends JPanel{
	
	public static ArrayList<CommandesFournisseur> listeCommandes = new ArrayList<CommandesFournisseur>();
	private static Object[][] tabCo;
	private static Object[] titres = {"N° Commande","Fournisseur","Date", "Montant", "Etat"};
	
	//On créer la JTable et son modèle
	private static JTable tableau = new JTable(new DefaultTableModel());
	private static UneditableTableModel modele = new UneditableTableModel(0,5);
	private static JScrollPane scrollPane;
	
	//On créer les composants que 'on va se servir dans plusieurs méthodes :
	private JButton btnNouveau, btnModifier, btnAnnuler;

	public PanelCommande(FenetrePrincipale f){
		System.out.println(listeCommandes.size());
		listeCommandes.removeAll(listeCommandes);
		
		//On récupère l'ensemble des founisseurs présents dans la BDD
		getCommande();
				
				
		//On initialise l'ensemble des composants sur le JPanel
		this.initElements();
				
		//On initialise l'ensemble des écouteurs
		this.initEcouteurs();
	}
	
	
	/**
	 * Getter de la liste des commandes.
	 * @return, la liste des commandes.
	 */
	public static ArrayList<CommandesFournisseur> getListeCommande(){
		return listeCommandes;
	}
	
	
	/**
	 * Méthode qui récupère l'ensemble des commandes de la base de données et les ajoute dans une ArrayList.
	 */
	public static void getCommande(){
		listeCommandes.clear();//On efface l'arraylist pour éviter d'ajouter une deuxième fois les éléments
		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("SELECT c.refCommande, c.dateCommande, c.etatCommande, c.tauxTVA, c.remise, c.dateLivr, c.typePaiement, f.refFournisseur, f.nomFournisseur, SUM(p.prixAchat*lc.quantite) AS montantTotal FROM CommandesFournisseur c LEFT JOIN Fournisseurs f ON f.refFournisseur = c.refFournisseur LEFT JOIN LignesCommandeFournisseur lc ON lc.refCommande = c.refCommande LEFT JOIN Produits p ON p.codeProduit = lc.refProduit GROUP BY c.refCommande, c.dateCommande, c.etatCommande, c.tauxTVA, c.remise, c.dateLivr, c.typePaiement, f.refFournisseur, f.nomFournisseur ORDER BY c.dateCommande DESC");
			ResultSet rs = pst.executeQuery();
			
			
			while(rs.next()){
				String refCommande, refFournisseur, nomFournisseur, montantTotal, etatCommande, typePaiement;
				double tauxTva, remise;
				Date date, dateLivr;
				
				refCommande = rs.getString("refCommande");
				date = rs.getDate("dateCommande");
				refFournisseur = rs.getString("refFournisseur");
				nomFournisseur = rs.getString("nomFournisseur");
				etatCommande = rs.getString("etatCommande");
				tauxTva = rs.getDouble("tauxTva");
				remise = rs.getDouble("remise");
				dateLivr = rs.getDate("dateLivr");
				typePaiement = rs.getString("typePaiement");
				
				
				//Montant total non vide
				if (rs.getString("montantTotal") == null){
					montantTotal = "Pas de produits";
				}
				else {
					montantTotal = rs.getString("montantTotal")+" €";
				}

				listeCommandes.add(new CommandesFournisseur(refCommande, date, refFournisseur, nomFournisseur, montantTotal, etatCommande, tauxTva, remise, dateLivr, typePaiement));
				
				//On remplit la JTable
				remplirTableau();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Méthode qui met à jour le tableau de commandes.
	 */
	public static void maj(){
		int nbDeCo = listeCommandes.size();//On calcule la taille de l'arrylist pour créer un tableau adéquat
		tabCo = new Object[nbDeCo][5];//On créer le tableau de la taille récupérée 
		
		//On remplit ce dernier avec les CommandesFournisseur récupérées
		for(CommandesFournisseur cf : listeCommandes){
			tabCo[listeCommandes.indexOf(cf)][0] = cf.getRefCommande();
			tabCo[listeCommandes.indexOf(cf)][1] = cf.getNomFourniseur();
			tabCo[listeCommandes.indexOf(cf)][2] = cf.getDate();
			tabCo[listeCommandes.indexOf(cf)][3] = cf.getMontantTotal();
			tabCo[listeCommandes.indexOf(cf)][4] = cf.getEtatCommande();
		}
		
		modele.setDataVector(tabCo,titres);
	}
	
	
	/**
	 * Méthode qui remplit le tableau avec les valeurs de l'arraylist, mais aussi la JTable.
	 */
	public static void remplirTableau(){
		
		tableau = new JTable(modele);
		tableau.setAutoCreateRowSorter(false);
		tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableau.setPreferredScrollableViewportSize(new Dimension(800, 224));
		
		maj();
		
		scrollPane = new JScrollPane(tableau);
	}
	
	
	/**
	 * Méthode qui initialise les éléments de toute la page.
	 */
	private void initElements(){
		
		//on défini le layout du JPanel principal
		this.setLayout(new BorderLayout());
		
		//On créer les panels
		JPanel panelRechercheNord = new JPanel();
		
		JPanel panelGrilleCentre = new JPanel(new GridLayout(2,1));
		JPanel panelGrille = new JPanel();
		JPanel panelBouton = new JPanel();
		
		
		//On créer les composants
		JLabel lblRechercheCommande = new JLabel("N° Commande : ");
		JTextField txtRechercheCommande = new JTextField(10);
		JLabel lblRechercheFournisseur = new JLabel("Fournisseur : ");
		JTextField txtRechercheFournisseur = new JTextField(10);
		JLabel lblRechercheDate = new JLabel("Date : ");
		JDateChooser jdcDate = new JDateChooser();
		JLabel lblRechercheMontant = new JLabel("Montant : ");
		JTextField txtRechercheMontant = new JTextField(5);
		
		this.btnNouveau = new JButton("Ajouter");
		this.btnModifier = new JButton("Modifier");
		this.btnAnnuler = new JButton("Annuler");
		
		//On grise l'accès aux boutons modifier et annuler tant qu'une ligne n'est pas sélectionnée
		this.btnModifier.setEnabled(false);
		this.btnAnnuler.setEnabled(false);
		
		
		//On ajoute les composants aux panels
		panelRechercheNord.add(lblRechercheCommande);
		panelRechercheNord.add(txtRechercheCommande);
		panelRechercheNord.add(lblRechercheFournisseur);
		panelRechercheNord.add(txtRechercheFournisseur);
		panelRechercheNord.add(lblRechercheDate);
		panelRechercheNord.add(jdcDate);
		panelRechercheNord.add(lblRechercheMontant);
		panelRechercheNord.add(txtRechercheMontant);
		
		panelGrille.add(scrollPane);
		panelBouton.add(this.btnNouveau);
		panelBouton.add(this.btnModifier);
		panelBouton.add(this.btnAnnuler);
		
		panelGrilleCentre.add(panelGrille);
		panelGrilleCentre.add(panelBouton);
		
		//On ajoute les panels au panel principal
		this.add(panelRechercheNord, BorderLayout.NORTH);
		this.add(panelGrilleCentre, BorderLayout.CENTER);
		
	}
	
	
	/**
	 * Méthode qui initialise les écouteurs.
	 */
	private void initEcouteurs(){
		
		//Clic et double clic sur une ligne
		tableau.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				btnModifier.setEnabled(true);
				btnAnnuler.setEnabled(true);
				
				if (e.getClickCount()%2 == 0){
					//new PopupCommande(listeCommandes.get(tableau.getSelectedRow()));
					System.out.println(listeCommandes.get(tableau.getSelectedRow()).getRefCommande());
				}
			}
		});
		
		
		//Bouton "Nouveau"
		this.btnNouveau.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new PopupCommande();
			}
		});
		
		
		//Bouton "Modifier"
		this.btnModifier.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new PopupCommande(listeCommandes.get(tableau.getSelectedRow()));
			}
		});
	}
}
