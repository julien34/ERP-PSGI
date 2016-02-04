package achat.vues;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.text.SimpleDateFormat;

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
import achat.modeles.UneditableTableModel;
import achat.vues.popup.PopupCommande;
import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class PanelCommande extends JPanel{
	
	public static ArrayList<CommandesFournisseur> listeCommandes = new ArrayList<CommandesFournisseur>();
	private static Object[][] tabCo;
	private static Object[] titres = {"N° Commande","Fournisseur","Date", "Montant", "Etat"};
	
	//On créer la JTable et son modèle
	private static JTable tableau = new JTable(new DefaultTableModel());
	private static UneditableTableModel modele = new UneditableTableModel(0,5);
	private static JScrollPane scrollPane  = new JScrollPane(tableau);
	
	//On créer les composants que 'on va se servir dans plusieurs méthodes :
	private static JButton btnNouveau, btnModifier, btnAnnuler;
	private static JTextField txtRechercheCommande, txtRechercheFournisseur, txtRechercheMontant;
	private static JDateChooser jdcDate;
	private static String dateRecherche = "";

	public PanelCommande(FenetrePrincipale f){
		
		//On récupère l'ensemble des founisseurs présents dans la BDD
		getCommande();
		
		//On remplit la JTable
		remplirTableau();
				
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
			PreparedStatement pst = cn.prepareStatement("SELECT c.refCommande, c.dateCommande, c.etatCommande, c.tauxTVA, c.remise, c.dateLivr, c.typePaiement, f.refFournisseur, f.nomFournisseur, SUM(p.prixAchat*lc.quantite) AS montantTotal FROM CommandesFournisseur c LEFT JOIN Fournisseurs f ON f.refFournisseur = c.refFournisseur LEFT JOIN LignesCommandeFournisseur lc ON lc.refCommande = c.refCommande LEFT JOIN Produit p ON p.code = lc.refProduit GROUP BY c.refCommande, c.dateCommande, c.etatCommande, c.tauxTVA, c.remise, c.dateLivr, c.typePaiement, f.refFournisseur, f.nomFournisseur ORDER BY c.dateCommande DESC");
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
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Méthode qui récupère l'ensemble des commandes de la base de données selon la recherche des champs et les ajoute dans une ArrayList.
	 */
	public static void getCommandeRecherche(){
		listeCommandes.clear();//On efface l'arraylist pour éviter d'ajouter une deuxième fois les éléments
		
		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("SELECT c.refCommande, c.dateCommande, c.etatCommande, c.tauxTVA, c.remise, c.dateLivr, c.typePaiement, f.refFournisseur, f.nomFournisseur, SUM(p.prixAchat*lc.quantite) AS montantTotal FROM CommandesFournisseur c LEFT JOIN Fournisseurs f ON f.refFournisseur = c.refFournisseur LEFT JOIN LignesCommandeFournisseur lc ON lc.refCommande = c.refCommande LEFT JOIN Produit p ON p.code = lc.refProduit WHERE UPPER(c.refCommande) LIKE UPPER(?) AND UPPER(f.nomFournisseur) LIKE UPPER(?) AND c.dateCommande LIKE ? GROUP BY c.refCommande, c.dateCommande, c.etatCommande, c.tauxTVA, c.remise, c.dateLivr, c.typePaiement, f.refFournisseur, f.nomFournisseur HAVING COALESCE(SUM(p.prixAchat*lc.quantite),0) LIKE ? ORDER BY c.dateCommande DESC");
			pst.setString(1, "%"+PanelCommande.txtRechercheCommande.getText()+"%");
			pst.setString(2, "%"+PanelCommande.txtRechercheFournisseur.getText()+"%");
			pst.setString(3, "%"+PanelCommande.dateRecherche+"%");
			pst.setString(4, "%"+PanelCommande.txtRechercheMontant.getText()+"%");
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
		
		maj();
		
		tableau = new JTable(modele);
		tableau.setAutoCreateRowSorter(false);
		tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableau.setPreferredScrollableViewportSize(new Dimension(800, 224));

		scrollPane.setViewportView(tableau);
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
		txtRechercheCommande = new JTextField(10);
		JLabel lblRechercheFournisseur = new JLabel("Fournisseur : ");
		txtRechercheFournisseur = new JTextField(10);
		JLabel lblRechercheDate = new JLabel("Date : ");
		jdcDate = new JDateChooser();
		JLabel lblRechercheMontant = new JLabel("Montant : ");
		txtRechercheMontant = new JTextField(5);
		
		btnNouveau = new JButton("Ajouter");
		btnModifier = new JButton("Modifier");
		btnAnnuler = new JButton("Annuler");
		
		//On grise l'accès aux boutons modifier et annuler tant qu'une ligne n'est pas sélectionnée
		setBtn(false);
		
		
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
		panelBouton.add(btnNouveau);
		panelBouton.add(btnModifier);
		panelBouton.add(btnAnnuler);
		
		panelGrilleCentre.add(panelGrille);
		panelGrilleCentre.add(panelBouton);
		
		//On ajoute les panels au panel principal
		this.add(panelRechercheNord, BorderLayout.NORTH);
		this.add(panelGrilleCentre, BorderLayout.CENTER);
		
	}
	
	
	/**
	 * Méthode qui initialise les écouteurs.
	 */
	public void initEcouteurs(){
		
		//Clic et double clic sur une ligne
		tableau.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				//On active la possibilité de cliquer sur les boutons annuler et modifier
				setBtn(true);
				
				if (e.getClickCount()%2 == 0){
					new PopupCommande(listeCommandes.get(tableau.getSelectedRow()));
				}
			}
		});
		
		
		//Bouton "Nouveau"
		btnNouveau.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new PopupCommande();
			}
		});
		
		
		//Bouton "Modifier"
		btnModifier.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new PopupCommande(listeCommandes.get(tableau.getSelectedRow()));
			}
		});
		
		/*-------------------------*/
		/*ECOUTEURS DE LA RECHERCHE*/
		/*-------------------------*/
		
		//Txt numéro de la commande
		txtRechercheCommande.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				PanelCommande.this.initDate();//On change la date si elle est vide
				PanelCommande.getCommandeRecherche();
				PanelCommande.maj();
			}
		});
		
		//Ecouteur de la Date
		jdcDate.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if("date".equals(evt.getPropertyName())){
					PanelCommande.this.initDate();//On change la date si elle est vide
					PanelCommande.getCommandeRecherche();
					PanelCommande.maj();
				}
			}
		});
		
		//Txt nom du fournisseur
		txtRechercheFournisseur.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				PanelCommande.this.initDate();//On change la date si elle est vide
				PanelCommande.getCommandeRecherche();
				PanelCommande.maj();
			}
		});
		
		//Txt du montant de la commande
		txtRechercheMontant.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				PanelCommande.this.initDate();//On change la date si elle est vide
				PanelCommande.getCommandeRecherche();
				PanelCommande.maj();
			}
		});
	}
	
	
	/**
	 * Change la possibilité d'appuyer sur le bouton modifier et annuler selon son paramètre.
	 * @param b, un booléen.
	 */
	public static void setBtn(boolean b){
		btnModifier.setEnabled(b);
		btnAnnuler.setEnabled(b);
	}
	
	
	/**
	 * Méthode qui change la date si elle est vide ou l'attribut avec son contenue si elle est remplie.
	 */
	private void initDate(){
		
		if(jdcDate.getDate() == null){
			PanelCommande.dateRecherche = "";
		}
		else{
			SimpleDateFormat formater = null;
			Date aujourdhui = new Date(PanelCommande.jdcDate.getDate().getTime());
			formater = new SimpleDateFormat("dd/MM/yy");
			PanelCommande.dateRecherche = String.valueOf(formater.format(aujourdhui));
		}
	}
}
