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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import achat.modeles.Fournisseur;
import achat.modeles.UneditableTableModel;
import achat.vues.popup.popFournisseur.PopupAjoutFournisseur;
import achat.vues.popup.popFournisseur.PopupModifFournisseur;
import achat.vues.popup.popFournisseur.PopupSupressionFournisseur;
import principal.FenetrePrincipale;
import jdbc.DatabaseConnection;

public class PanelFournisseur extends JPanel{
	
	//On créer les panels et le scrollPane (pour la JTable)
	private JPanel panelGrid, panelRechercheNord, panelGridCentre, panelBoutonsSud;
	private JScrollPane scrollPane;
	
	//On créer les boutons, labels, boutons radio et le champs de recherche
	private static JButton btnAjouter, btnModifier, btnSupprimer;
	private static JTextField txtRecherche;
	private JLabel lblRecherche;
	private ButtonGroup btnGrpRecherche;
	private static JRadioButton radioSiret, radioNom;
	
	//On créer la JTable et son modèle
	private static JTable tableau = new JTable(new DefaultTableModel());
	private static UneditableTableModel modele;
	
	//On créer une ArrayList de Fournisseur, une matrice de Fournisseur ainsi qu'un tableau de titres
	private static ArrayList<Fournisseur> liste = new ArrayList<Fournisseur>();
	private static String[] titres = {"Entreprise","N° SIRET","Adresse","N° Tél", "Catégorie"};
	private static Object[][] tabFn;
	
	
	/**
	 * Constructeur avec la fenetre principale en parmètre la classe PanelAjoutFournisseur
	 * @throws SQLException 
	 */
	public PanelFournisseur(FenetrePrincipale f) {
		
		//On récupère l'ensemble des founisseurs présents dans la BDD
		this.getFournisseur();
		
		//On remplit la JTable
		this.remplirTableau();
		
		//On initialise l'ensemble des composants sur le JPanel
		this.initElements();
		
		//On initialise l'ensemble des écouteurs
		this.initEcouteurs();
	}
	
	
	/**
	 * Méthode qui caractérise l'ensemble des composants et les ajoutent aux différents panels.
	 */
	private void initElements(){
		
		//On caractérise les panels
		this.setLayout(new BorderLayout());
		this.panelRechercheNord = new JPanel();
		this.panelBoutonsSud = new JPanel();//On créer un JPanel qui va recevoir les boutons
		this.panelGridCentre = new JPanel();//On créer le panel qui va recevoir la grid
		this.panelGrid = new JPanel(new GridLayout(2,1));
		
		//On caractérise nos composants
		btnAjouter = new JButton("Ajouter");
		btnModifier = new JButton("Modifier");
		btnSupprimer = new JButton("Supprimer");
		
		this.lblRecherche = new JLabel("Rechercher : ");
		txtRecherche = new JTextField(20);
		radioNom = new JRadioButton("Nom");
		radioSiret = new JRadioButton("Siret");
		btnGrpRecherche = new ButtonGroup();
		
		
		//On empêche l'utilisateur de cliquer sur les bouton supprimer et ajouter
		btnModifier.setEnabled(false);
		btnSupprimer.setEnabled(false);
		
		//On coche de base le bouton de recherche par nom pour éviter une erreur
		radioNom.setSelected(true);
		
		//On ajoute les bouton radio au groupe de bouton de la recherche
		this.btnGrpRecherche.add(radioNom);
		this.btnGrpRecherche.add(radioSiret);
		
		
		//On ajoute les composants aux différents panels
		this.panelRechercheNord.add(this.lblRecherche);
		this.panelRechercheNord.add(txtRecherche);
		this.panelRechercheNord.add(radioNom);
		this.panelRechercheNord.add(radioSiret);
		this.panelGridCentre.add(this.scrollPane);
		this.panelBoutonsSud.add(btnAjouter);
		this.panelBoutonsSud.add(btnModifier);
		this.panelBoutonsSud.add(btnSupprimer);
		
		//On ajoute les panels au panels
		this.panelGrid.add(panelGridCentre);
		this.panelGrid.add(panelBoutonsSud);
		
		this.add(this.panelRechercheNord, BorderLayout.NORTH);
		this.add(this.panelGrid, BorderLayout.CENTER);
	}
	
	
	/**
	 * Méthode qui initialise les écouteurs (boutons et double clic sur la JTable).
	 */
	private void initEcouteurs(){
		
		//Ecouteur du bouton "Modifier"
		btnModifier.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new PopupModifFournisseur(PanelFournisseur.this.getListe().get(tableau.getSelectedRow()),tableau.getSelectedRow());
			}
		});

		//Ecouteur du bouton "Ajouter"
		btnAjouter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new PopupAjoutFournisseur();
			}
		});


		//Ecouteur du bouton "Supprimer"
		btnSupprimer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new PopupSupressionFournisseur(PanelFournisseur.this.getListe().get(tableau.getSelectedRow()));
			}
		});
		
		//Lors d'un double clic sur une ligne du tableau
		tableau.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				//Permettre d'utiliser les boutons modifier et supprimer
				btnModifier.setEnabled(true);
				btnSupprimer.setEnabled(true);

				//Si dble clic ouverture de la popup modif
				if (e.getClickCount()%2 == 0){
					new PopupModifFournisseur(getListe().get(tableau.getSelectedRow()),tableau.getSelectedRow());
				}
			}
		});
		
		
		//Ecouteur du JRadioButton Nom
		radioNom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnModifier.setEnabled(false);
				btnSupprimer.setEnabled(false);
				PanelFournisseur.majTableauRecherche();
			}
		});
		
		
		//Ecouteur du JRadioButton siret
		radioSiret.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnModifier.setEnabled(false);
				btnSupprimer.setEnabled(false);
				PanelFournisseur.majTableauRecherche();
			}
		});
		
		
		//Ecouteur du champs de recherche
		txtRecherche.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnModifier.setEnabled(false);
				btnSupprimer.setEnabled(false);
				PanelFournisseur.majTableauRecherche();
			}
		});
	}

	
	/**
	 * Méthode qui retourne une ArrayList de tous les Fournisseurs présents dans la base de donnée.
	 * @return ArrayList<Fournisseur>
	 * @throws SQLException
	 */
	private ArrayList<Fournisseur> getFournisseur() {
		try{
			Connection cn = DatabaseConnection.getCon();
			Statement st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM Fournisseurs f LEFT JOIN CategoriesFournisseur c ON f.categorieFournisseur = c.refCategorie ORDER BY nomFournisseur");
			
			while(rs.next()){
				String ref = rs.getString("refFournisseur");
				String nomFn = rs.getString("nomFournisseur");
				String siret = rs.getString("siret");
				String tel = rs.getString("telFournisseur");
				String adresse = rs.getString("adresseFournisseur");
				String categorie = rs.getString("nomCategorie");

				liste.add(new Fournisseur(ref, nomFn, siret, tel, adresse, categorie));
				
				this.remplirTableau();
				
			}} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste;
	}
	
	
	/**
	 * Méthode qui retourne l'ArrayList courante.
	 * @return liste, l'ArrayList courante de Fournisseurs.
	 */
	private ArrayList<Fournisseur> getListe(){
		return liste;
	}
	
	
	/**
	 * Méthode qui remplit la JTable à partir de l'arraylist récupérée
	 */
	private void remplirTableau(){
		int lgTableau = liste.size();
		tabFn = new Object[lgTableau][5];
		
		//On remplit le tableau d'objet avec les fn de l'arraylist
		for(Fournisseur fn : liste){
			tabFn[liste.indexOf(fn)][0] = fn.getNom();
			tabFn[liste.indexOf(fn)][1] = fn.getSiret();
			tabFn[liste.indexOf(fn)][2] = fn.getAdresse();
			tabFn[liste.indexOf(fn)][3] = fn.getTel();
			tabFn[liste.indexOf(fn)][4] = fn.getCategorie();
		}
		
		modele = new UneditableTableModel(0,5);
		modele.setDataVector(tabFn,titres);
		
		tableau = new JTable(modele);
		
		tableau.setAutoCreateRowSorter(false);
		tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableau.setPreferredScrollableViewportSize(new Dimension(800, 224));
		
		scrollPane = new JScrollPane(tableau);
	}

	
	/**
	 * Méthode qui permet de mettre à jour le tableau des fournisseurs après une modification.
	 * @param f, un Fournisseur.
	 * @param indice, un entier pour définir la place dans la liste (pour garder la cohérence).
	 */
	public static void setTableau(Fournisseur f, int indice){
		liste.remove(indice);
		liste.add(indice,f);
		
		tabFn[indice][0] = f.getNom();
		tabFn[indice][1] = f.getSiret();
		tabFn[indice][2] = f.getTel();
		tabFn[indice][3] = f.getAdresse();
		tabFn[indice][4] = f.getCategorie();
		
		modele.setDataVector(tabFn,titres);
	}
	
	
	/**
	 * Méthode qui permet de mettre à jour le tableau des fournisseurs après un ajout.
	 * @param f, un Fournisseur.
	 */
	public static void majTableau(Fournisseur f){
		liste.add(f);
		
		int nouvelleLongueur = tabFn.length+1;
		
		tabFn = new Object[nouvelleLongueur][5];
		
		for(Fournisseur fn : liste){
			tabFn[liste.indexOf(fn)][0] = fn.getNom();
			tabFn[liste.indexOf(fn)][1] = fn.getSiret();
			tabFn[liste.indexOf(fn)][2] = fn.getAdresse();
			tabFn[liste.indexOf(fn)][3] = fn.getTel();
			tabFn[liste.indexOf(fn)][4] = fn.getCategorie();
		}
		
		modele.setDataVector(tabFn,titres);
	}
	
	
	/**
	 * Méthode qui permet de mettre a jour le tableau des Fournisseurs après une supression
	 * @param f, un Fournisseur.
	 */
	public static void majTableauSuppr(Fournisseur f){
		liste.remove(f);
		
		int nouvelleLongueur = tabFn.length-1;
		
		tabFn = new Object[nouvelleLongueur][5];
		
		for(Fournisseur fn : liste){
			tabFn[liste.indexOf(fn)][0] = fn.getNom();
			tabFn[liste.indexOf(fn)][1] = fn.getSiret();
			tabFn[liste.indexOf(fn)][2] = fn.getAdresse();
			tabFn[liste.indexOf(fn)][3] = fn.getTel();
			tabFn[liste.indexOf(fn)][4] = fn.getCategorie();
		}
		
		modele.setDataVector(tabFn,titres);
		btnModifier.setEnabled(false);
		btnSupprimer.setEnabled(false);
	}
	
	
	
	/**
	 * Méthode qui met à jour la JTable à chaque touche tapée avec les donnée du champs de recherche.
	 */
	public static void majTableauRecherche(){

		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst;

			//On vérifie d'ou vient la source de la recherche
			if(PanelFournisseur.radioNom.isSelected()){
				pst = cn.prepareStatement("SELECT * FROM Fournisseurs f LEFT JOIN CategoriesFournisseur c ON f.categorieFournisseur = c.refCategorie WHERE UPPER(nomFournisseur) LIKE UPPER(?) ORDER BY nomFournisseur");
			}
			else {
				pst = cn.prepareStatement("SELECT * FROM Fournisseurs f LEFT JOIN CategoriesFournisseur c ON f.categorieFournisseur = c.refCategorie WHERE UPPER(siret) LIKE UPPER(?) ORDER BY nomFournisseur");
			}

			pst.setString(1, "%"+txtRecherche.getText()+"%");//% comme caractère blanc 

			ResultSet rs = pst.executeQuery();

			PanelFournisseur.liste.removeAll(liste);//On supprimer la liste

			//On re-rempli la liste avec les données récoltées de la requête
			while(rs.next()){
				String ref = rs.getString("refFournisseur");
				String nom = rs.getString("nomFournisseur");
				String siret = rs.getString("siret");
				String tel = rs.getString("telFournisseur");
				String adresse = rs.getString("adresseFournisseur");
				String categorie = rs.getString("nomCategorie");

				PanelFournisseur.liste.add(new Fournisseur(ref,nom,siret,tel,adresse, categorie));
			}

			tabFn = new Object[liste.size()][5];

			for(Fournisseur fn : liste){
				tabFn[liste.indexOf(fn)][0] = fn.getNom();
				tabFn[liste.indexOf(fn)][1] = fn.getSiret();
				tabFn[liste.indexOf(fn)][2] = fn.getAdresse();
				tabFn[liste.indexOf(fn)][3] = fn.getTel();
				tabFn[liste.indexOf(fn)][4] = fn.getCategorie();
			}

			modele.setDataVector(tabFn,titres);

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}


	/**
	 * Méthode qui retourne l'ArrayList courante de fournisseurs
	 * @return liste, l'ArrayList de Fournisseur.
	 */
	public static ArrayList<Fournisseur> getTableau(){
		return liste;
	}
	
}
