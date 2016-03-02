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
import achat.modeles.DevisFournisseur;
import achat.modeles.UneditableTableModel;
import achat.vues.popup.popCommande.PopupCommande;
import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class PanelDevis extends JPanel{
	
	public static ArrayList<DevisFournisseur> listeDevis = new ArrayList<DevisFournisseur>();
	public static ArrayList<DevisFournisseur> getListeDevis() {
		return listeDevis;
	}


	public static void setListeDevis(ArrayList<DevisFournisseur> listeDevis) {
		PanelDevis.listeDevis = listeDevis;
	}


	private static Object[][] tabCo;
	private static Object[] titres = {"N° Devis","Fournisseur","Date", "Montant", "Etat"};
	
	//On créer la JTable et son modèle
	private static JTable tableau = new JTable(new DefaultTableModel());
	private static UneditableTableModel modele = new UneditableTableModel(0,5);
	private static JScrollPane scrollPane  = new JScrollPane(tableau);
	
	//On créer les composants que 'on va se servir dans plusieurs méthodes :
	private static JButton btnNouveau, btnModifier, btnAnnuler,btnValiderCommande;
	private static JTextField txtRechercheCommande, txtRechercheFournisseur, txtRechercheMontant;
	private static JDateChooser jdcDate;
	private static String dateRecherche = "";
	private FenetrePrincipale fe;
	public PanelDevis(FenetrePrincipale f){
		
		//On récupère l'ensemble des founisseurs présents dans la BDD
		getCommande();
		
		//On remplit la JTable
		remplirTableau();
				
		//On initialise l'ensemble des composants sur le JPanel
		this.initElements();
				
		//On initialise l'ensemble des écouteurs
		this.initEcouteurs();
		fe=f;
	}
	
	
	/**
	 * Getter de la liste des devis.
	 * @return, la liste des devis.
	 */
	public static ArrayList<DevisFournisseur> getListeCommande(){
		return listeDevis;
	}
	
	
	/**
	 * Méthode qui récupère l'ensemble des devis de la base de données et les ajoute dans une ArrayList.
	 */
	public static void getCommande(){
		listeDevis.clear();//On efface l'arraylist pour éviter d'ajouter une deuxième fois les éléments
		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("SELECT d.refDevis, d.dateDevis, d.etatDevis, d.tauxTVA, d.remise, d.typePaiement, d.refCommande, f.refFournisseur, f.nomFournisseur, SUM(p.prixAchat*lc.quantite) AS montantTotal FROM DevisFournisseurs d LEFT JOIN Fournisseurs f ON f.refFournisseur = d.refFournisseur LEFT join CommandesFournisseur c on d.refCommande = c.refCommande LEFT JOIN LignesCommandeFournisseur lc ON lc.refCommande = c.refCommande LEFT JOIN Produit p ON p.code = lc.refProduit GROUP BY d.refDevis, d.dateDevis, d.etatdevis, d.tauxTVA, d.remise, d.typePaiement,d.refCommande, f.refFournisseur, f.nomFournisseur ORDER BY d.dateDevis DESC");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				Integer refDevis;
				String  refCommande = null, refFournisseur, nomFournisseur, montantTotal, etatCommande, typePaiement;
				double tauxTva, remise;
				Date date;
				
				refDevis= rs.getInt("refDevis");
				date = rs.getDate("datedevis");
				refFournisseur = rs.getString("refFournisseur");
				nomFournisseur = rs.getString("nomFournisseur");
				etatCommande = rs.getString("etatDevis");
				tauxTva = rs.getDouble("tauxTva");
				remise = rs.getDouble("remise");
				typePaiement = rs.getString("typePaiement");
				refCommande= rs.getString("refCommande"); 
				
				//Montant total non vide
				if (rs.getString("montantTotal") == null){
					montantTotal = "Pas de produits";
				}
				else {
					montantTotal = rs.getString("montantTotal")+" €";
				}

				listeDevis.add(new DevisFournisseur(refDevis, date, refFournisseur, nomFournisseur, montantTotal, etatCommande, tauxTva, remise, typePaiement,refCommande));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Méthode qui récupère l'ensemble des commandes de la base de données selon la recherche des champs et les ajoute dans une ArrayList.
	 */
	public static void getCommandeRecherche(){
		listeDevis.clear();//On efface l'arraylist pour éviter d'ajouter une deuxième fois les éléments
		
		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("SELECT d.refDevis, d.dateDevis, d.etatDevis, d.tauxTVA, d.remise, d.typePaiement, d.refCommande, f.refFournisseur, f.nomFournisseur FROM DevisFournisseurs d LEFT JOIN Fournisseurs f ON f.refFournisseur = d.refFournisseur WHERE UPPER(d.refDevis) LIKE UPPER(?) AND UPPER(f.nomFournisseur) LIKE UPPER(?) AND d.datedevis LIKE ? GROUP BY d.refDevis, d.dateDevis, d.etatdevis, d.tauxTVA, d.remise, d.typePaiement, d.refCommande, f.refFournisseur, f.nomFournisseur ORDER BY d.dateDevis DESC");
			pst.setString(1, "%"+PanelDevis.txtRechercheCommande.getText()+"%");
			pst.setString(2, "%"+PanelDevis.txtRechercheFournisseur.getText()+"%");
			pst.setString(3, "%"+PanelDevis.dateRecherche+"%");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				Integer refDevis;
				String refCommande = null, refFournisseur, nomFournisseur, montantTotal, etatCommande, typePaiement;
				double tauxTva, remise;
				Date date;
				
				refDevis = rs.getInt("refDevis");
				date = rs.getDate("dateDevis");
				refFournisseur = rs.getString("refFournisseur");
				nomFournisseur = rs.getString("nomFournisseur");
				etatCommande = rs.getString("etatDevis");
				tauxTva = rs.getDouble("tauxTva");
				remise = rs.getDouble("remise");
				typePaiement = rs.getString("typePaiement");
				refCommande= rs.getString("refCommande");
				
				
				//Montant total non vide
				if (rs.getString("montantTotal") == null){
					montantTotal = "Pas de produits";
				}
				else {
					montantTotal = rs.getString("montantTotal")+" €";
				}

				listeDevis.add(new DevisFournisseur(refDevis, date, refFournisseur, nomFournisseur, montantTotal, etatCommande, tauxTva, remise, typePaiement,refCommande));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Méthode qui met à jour le tableau de commandes.
	 */
	public static void maj(){
		int nbDeCo = listeDevis.size();//On calcule la taille de l'arrylist pour créer un tableau adéquat
		tabCo = new Object[nbDeCo][5];//On créer le tableau de la taille récupérée 
		
		//On remplit ce dernier avec les CommandesFournisseur récupérées
		for(DevisFournisseur cf : listeDevis){
			tabCo[listeDevis.indexOf(cf)][0] = cf.getRefDevis();
			tabCo[listeDevis.indexOf(cf)][1] = cf.getNomFourniseur();
			tabCo[listeDevis.indexOf(cf)][2] = cf.getDate();
			tabCo[listeDevis.indexOf(cf)][3] = cf.getMontantTotal();
			tabCo[listeDevis.indexOf(cf)][4] = cf.getEtatDevis();
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
		JLabel lblRechercheCommande = new JLabel("N° Devis : ");
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
		btnValiderCommande = new JButton("Valider Devis");
		
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
		//panelBouton.add(btnNouveau);
		//panelBouton.add(btnModifier);
		panelBouton.add(btnValiderCommande);
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
				
//				if (e.getClickCount()%2 == 0){
//					new PopupCommande(listeDevis.get(tableau.getSelectedRow()));
//				}
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
				//new PopupCommande(listeDevis.get(tableau.getSelectedRow()).getRefCommande());
			}
		});
		this.btnValiderCommande.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
               validationCommande(listeDevis.get(tableau.getSelectedRow()));
                maj();
            }
		});
		this.btnAnnuler.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				annulationCommande(listeDevis.get(tableau.getSelectedRow()));
				maj();
				
			}

			
		});
		
		/*-------------------------*/
		/*ECOUTEURS DE LA RECHERCHE*/
		/*-------------------------*/
		
		//Txt numéro de la commande
		txtRechercheCommande.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				PanelDevis.this.initDate();//On change la date si elle est vide
				PanelDevis.getCommandeRecherche();
				PanelDevis.maj();
			}
		});
		
		//Ecouteur de la Date
		jdcDate.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if("date".equals(evt.getPropertyName())){
					PanelDevis.this.initDate();//On change la date si elle est vide
					PanelDevis.getCommandeRecherche();
					PanelDevis.maj();
				}
			}
		});
		
		//Txt nom du fournisseur
		txtRechercheFournisseur.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				PanelDevis.this.initDate();//On change la date si elle est vide
				PanelDevis.getCommandeRecherche();
				PanelDevis.maj();
			}
		});
		
		//Txt du montant de la commande
		txtRechercheMontant.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				PanelDevis.this.initDate();//On change la date si elle est vide
				PanelDevis.getCommandeRecherche();
				PanelDevis.maj();
			}
		});
	}
	
	
	/**
	 * Change la possibilité d'appuyer sur le bouton modifier et annuler selon son paramètre.
	 * @param b, un booléen.
	 */
	public static void setBtn(boolean b){
		btnValiderCommande.setEnabled(b);
		btnAnnuler.setEnabled(b);
	}
	
	
	/**
	 * Méthode qui change la date si elle est vide ou l'attribut avec son contenue si elle est remplie.
	 */
	private void initDate(){
		
		if(jdcDate.getDate() == null){
			PanelDevis.dateRecherche = "";
		}
		else{
			SimpleDateFormat formater = null;
			Date aujourdhui = new Date(PanelDevis.jdcDate.getDate().getTime());
			formater = new SimpleDateFormat("dd/MM/yy");
			PanelDevis.dateRecherche = String.valueOf(formater.format(aujourdhui));
		}
	}
	private void validationCommande(DevisFournisseur devisFournisseur) {
        try {
				Connection cn = DatabaseConnection.getCon();
				PreparedStatement pst = cn.prepareStatement("UPDATE DevisFournisseurs SET EtatDevis = ? WHERE refDevis = ?");
				pst.setString(1, "Validé");
				pst.setInt(2, devisFournisseur.getRefDevis());
				pst.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			devisFournisseur.setEtatDevis("Validée");
    }
	private void annulationCommande(DevisFournisseur devisFournisseur) {
		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("UPDATE DevisFournisseurs SET EtatDevis = ? WHERE refDevis = ?");
			pst.setString(1, "Annulé");
			pst.setInt(2, devisFournisseur.getRefDevis());
			pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("UPDATE commandesFournisseur SET EtatCommande = ? WHERE refCommande = ?");
			pst.setString(1, "Annulée");
			pst.setString(2, devisFournisseur.getRefCommande());
			pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		devisFournisseur.setEtatDevis("Annulé");
		
		
	}
}
