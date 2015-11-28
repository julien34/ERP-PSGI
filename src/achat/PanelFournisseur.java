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

import achat.popup.PopupAjoutFournisseur;
import achat.popup.PopupModifFournisseur;
import achat.popup.PopupSupressionFournisseur;
import principal.FenetrePrincipale;
import jdbc.DatabaseConnection;

public class PanelFournisseur extends JPanel{
	
	//On créer les panels et le scrollPane (pour la JTable)
	private JPanel panelGrid, panelRechercheNord, panelGridCentre, panelBoutonsSud;
	private JScrollPane scrollPane;
	
	//On créer les boutons, labels, boutons radio et le champs de recherche
	private static JButton btnAjouter, btnModifier, btnSupprimer, btnOkRecherche;
	private JTextField txtRecherche;
	private JLabel lblRecherche;
	private ButtonGroup btnGrpRecherche;
	private static JRadioButton radioSiret, radioNom;
	
	//On créer la JTable et son modèle
	private static JTable tableau = new JTable(new DefaultTableModel());
	private static UneditableTableModel modele;
	
	//On créer une ArrayList de Fournisseur, une matrice de Fournisseur ainsi qu'un tableau de titres
	private static ArrayList<Fournisseur> liste = new ArrayList<Fournisseur>();
	private static String[] titres = {"Entreprise","N° SIRET","Adresse","N° Tél"};
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
		this.txtRecherche = new JTextField(20);
		this.radioNom = new JRadioButton("Nom");
		this.radioSiret = new JRadioButton("Siret");
		btnOkRecherche = new JButton("Ok");
		btnGrpRecherche = new ButtonGroup();
		
		
		//On empêche l'utilisateur de cliquer sur les bouton supprimer et ajouter
		btnModifier.setEnabled(false);
		btnSupprimer.setEnabled(false);
		
		//On coche de base le bouton de recherche par nom pour éviter une erreur
		radioNom.setSelected(true);
		
		//On ajoute les bouton radio au groupe de bouton de la recherche
		this.btnGrpRecherche.add(this.radioNom);
		this.btnGrpRecherche.add(this.radioSiret);
		
		
		//On ajoute les composants aux différents panels
		this.panelRechercheNord.add(this.lblRecherche);
		this.panelRechercheNord.add(this.txtRecherche);
		this.panelRechercheNord.add(this.radioNom);
		this.panelRechercheNord.add(this.radioSiret);
		this.panelRechercheNord.add(btnOkRecherche);
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
		
		//Modifier
		btnModifier.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PopupModifFournisseur popupModifFournisseur = new PopupModifFournisseur(PanelFournisseur.this.getListe().get(tableau.getSelectedRow()),tableau.getSelectedRow());
			}
		});

		//Ajouter
		btnAjouter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PopupAjoutFournisseur popupAjoutFournisseur = new PopupAjoutFournisseur();
			}
		});


		//Suprimer
		btnSupprimer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PopupSupressionFournisseur popupSupressionFournisseur = new PopupSupressionFournisseur(PanelFournisseur.this.getListe().get(tableau.getSelectedRow()));

			}
		});
		
		//Lors d'un double clic sur une ligne du tableau
		tableau.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				//Permettre d'utiliser les bouton modifier et supprimer
				btnModifier.setEnabled(true);
				btnSupprimer.setEnabled(true);

				//Si dble clic ouverture de la popup modif
				if (e.getClickCount()%2 == 0){
					PopupModifFournisseur popupModifFournisseur = new PopupModifFournisseur(PanelFournisseur.this.getListe().get(tableau.getSelectedRow()),tableau.getSelectedRow());
				}
			}
		});
		
		//Bouton "OK" de recherche
		btnOkRecherche.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					Connection cn = DatabaseConnection.getCon();
					PreparedStatement pst;
					
					if(PanelFournisseur.radioNom.isSelected()){
						pst = cn.prepareStatement("SELECT * FROM Fournisseurs WHERE UPPER(nomFournisseur) LIKE UPPER(?)");
					}
					else {
						pst = cn.prepareStatement("SELECT * FROM Fournisseurs WHERE UPPER(siret) LIKE UPPER(?)");
					}
					
					pst.setString(1, "%"+PanelFournisseur.this.txtRecherche.getText()+"%");
					
					ResultSet rs = pst.executeQuery();
					
					PanelFournisseur.liste.removeAll(getListe());
					
					while(rs.next()){
						String ref = rs.getString("refFournisseur");
						String nom = rs.getString("nomFournisseur");
						String siret = rs.getString("siret");
						String tel = rs.getString("telFournisseur");
						String adresse = rs.getString("adresseFournisseur");
						
						PanelFournisseur.liste.add(new Fournisseur(ref,nom,siret,tel,adresse));
					}
					
					tabFn = new Object[liste.size()][4];
					
					for(Fournisseur fn : liste){
						tabFn[liste.indexOf(fn)][0] = fn.nom;
						tabFn[liste.indexOf(fn)][1] = fn.siret;
						tabFn[liste.indexOf(fn)][2] = fn.adresse;
						tabFn[liste.indexOf(fn)][3] = fn.tel;
					}
					
					modele.setDataVector(tabFn,titres);
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
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
			ResultSet rs = st.executeQuery("SELECT * FROM Fournisseurs ORDER BY nomFournisseur");
			
			while(rs.next()){
				String ref = rs.getString("refFournisseur");
				String nomFn = rs.getString("nomFournisseur");
				String siret = rs.getString("siret");
				String tel = rs.getString("telFournisseur");
				String adresse = rs.getString("adresseFournisseur");
	
				liste.add(new Fournisseur(ref, nomFn, siret, tel, adresse));
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
		tabFn = new Object[lgTableau][4];
		
		//On remplit le tableau d'objet avec les fn de l'arraylist
		for(Fournisseur fn : liste){
			tabFn[liste.indexOf(fn)][0] = fn.nom;
			tabFn[liste.indexOf(fn)][1] = fn.siret;
			tabFn[liste.indexOf(fn)][2] = fn.adresse;
			tabFn[liste.indexOf(fn)][3] = fn.tel;
		}
		
		modele = new UneditableTableModel(0,4);
		modele.setDataVector(tabFn,titres);
		
		tableau = new JTable(modele);
		
		tableau.setAutoCreateRowSorter(false);
		tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableau.setPreferredScrollableViewportSize(new Dimension(700, 224));
		
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
		
		tabFn[indice][0] = f.nom;
		tabFn[indice][1] = f.siret;
		tabFn[indice][2] = f.tel;
		tabFn[indice][3] = f.adresse;
		
		modele.setDataVector(tabFn,titres);
	}
	
	
	/**
	 * Méthode qui permet de mettre à jour le tableau des fournisseurs après un ajout.
	 * @param f, un Fournisseur.
	 */
	public static void majTableau(Fournisseur f){
		liste.add(f);
		
		int nouvelleLongueur = tabFn.length+1;
		
		tabFn = new Object[nouvelleLongueur][4];
		
		for(Fournisseur fn : liste){
			tabFn[liste.indexOf(fn)][0] = fn.nom;
			tabFn[liste.indexOf(fn)][1] = fn.siret;
			tabFn[liste.indexOf(fn)][2] = fn.adresse;
			tabFn[liste.indexOf(fn)][3] = fn.tel;
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
		
		tabFn = new Object[nouvelleLongueur][4];
		
		for(Fournisseur fn : liste){
			tabFn[liste.indexOf(fn)][0] = fn.nom;
			tabFn[liste.indexOf(fn)][1] = fn.siret;
			tabFn[liste.indexOf(fn)][2] = fn.adresse;
			tabFn[liste.indexOf(fn)][3] = fn.tel;
		}
		
		modele.setDataVector(tabFn,titres);
		btnModifier.setEnabled(false);
		btnSupprimer.setEnabled(false);
	}

	
	/**
	 * Méthode qui retourne l'ArrayList courante de fournisseurs
	 * @return liste, l'ArrayList de Fournisseur.
	 */
	public static ArrayList<Fournisseur> getTableau(){
		return liste;
	}
	
}
