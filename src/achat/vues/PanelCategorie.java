package achat.vues;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import achat.modeles.Categorie;
import achat.modeles.UneditableTableModel;
import achat.vues.popup.PopupAjoutCategorie;
import achat.vues.popup.PopupModifCategorie;
import achat.vues.popup.PopupSuppressionCategorie;
import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class PanelCategorie extends JPanel{
	
	//On instancie les éléments de la page
	private static JTextField txtRecherche;
	private JLabel lblrecherche;
	private static JButton btnAjouter, btnModifier, btnSupprimer;
	private JPanel panelNord, panelCentre, panelGrille, panelBoutons;
	private static String[] tabTitres = {"Code","Catégorie"};
	private static Object[][] tabCategories;
	private static UneditableTableModel tableCategorie;
	private static ArrayList<Categorie> listeCategorie = new ArrayList<Categorie>();

	private static JScrollPane scrollPane = new JScrollPane();
	private static JTable tableau;
	
	
	/**
	 * Constructeur avec une FenetrePrincipale en paramètre
	 * @param f, la fenetrePrincipale
	 */
	public PanelCategorie(FenetrePrincipale f){
		initTab();//On initie le tableau des catégories
		
		this.initPanel();//On créer les différents panels
		this.initElements();//On instancie les différents composants du panel
		this.initEcouteurs();//On créer les écouteurs de tous les boutons
		
		this.setVisible(true);//On rend visible le panel
	}
	
	
	/**
	 * Méthode qui retourne la liste des catégories.
	 * @return la liste des catégories
	 */
	public static ArrayList<Categorie> getListeCategorie() {
		return listeCategorie;
	}
	
	
	/**
	 * Méthode qui initialise le panel de gestion des catégories fournisseurs
	 */
	private void initPanel(){
		this.setLayout(new BorderLayout());//Le panel sera en Borderlayout
		this.panelNord = new JPanel();//Panel de recherche dans les catégories de fournisseurs
		this.panelCentre = new JPanel(new BorderLayout());//Panel qui recevra la grid + les boutons
		this.panelGrille = new JPanel();//On créer un panel qui va recevoir la grille
		this.panelBoutons = new JPanel();//On créer le panel qui va recevoir les boutons
	}
	
	
	/**
	 * Méthode qui initialise les éléments du panel
	 */
	private void initElements(){
		
		/*PANEL NORD*/
		//On créer lee champs et le label recherche
		this.lblrecherche = new JLabel("Catégorie : ");
		txtRecherche = new JTextField(10);
		
		//On ajoute
		this.panelNord.add(this.lblrecherche);
		this.panelNord.add(txtRecherche);
		
		
		/*PANEL CENTRE*/
		//On créer les boutons (pas de suppression)
		btnAjouter = new JButton("Ajouter");
		btnModifier = new JButton("Modifier");
		btnSupprimer = new JButton("Supprimer");
		
		//On grise le bouton modifier (car aucune ligne n'est sélectionnée au départ)
		btnModifier.setEnabled(false);
		btnSupprimer.setEnabled(false);
		
		//On les ajoute au panelBoutons
		this.panelBoutons.add(btnAjouter);
		this.panelBoutons.add(btnModifier);
		this.panelBoutons.add(btnSupprimer);
		
		//On ajoute le scrollPane au panel central
		this.panelGrille.add(scrollPane);
		
		//On met en place le panel du centre avec la grid et les boutons
		this.panelCentre.add(this.panelGrille, BorderLayout.CENTER);
		this.panelCentre.add(this.panelBoutons, BorderLayout.SOUTH);
		
		//On ajoute le panel de recherche (nord) et le panel centre (grid + boutons)
		this.add(this.panelNord, BorderLayout.NORTH);
		this.add(this.panelCentre, BorderLayout.CENTER);
	}
	
	
	/**
	 * Méthode qui récupère les catégories et les ajoute dans l'arrayList.
	 */
	private static void initTab(){
		
		try {
			Connection cn = DatabaseConnection.getCon();
			Statement st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM CategoriesFournisseur ORDER BY nomCategorie");
			
			while(rs.next()){
				listeCategorie.add(new Categorie(rs.getString("refCategorie"), rs.getString("nomCategorie")));
			}
			
			remplirTableau();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Méthode qui remplit le tableau 
	 */
	private static void remplirTableau(){
		
		//On calcule la longueur de l'arrayList et on créer un nouvceau tableau de cette taille
		int longueurTab = listeCategorie.size();
		tabCategories = new Object[longueurTab][2];
		
		//On remplit le tableau
		for (Categorie cat : listeCategorie){
			tabCategories[listeCategorie.indexOf(cat)][0] = cat.getId();
			tabCategories[listeCategorie.indexOf(cat)][1] = cat.getNom();
		}
		
		//On créer une table modele et un JTable. On assigne le modele à la JTable
		tableCategorie = new UneditableTableModel(0,2);
		tableCategorie.setDataVector(tabCategories, tabTitres);
		
		tableau = new JTable(tableCategorie);
		tableau.setAutoCreateRowSorter(false);
		tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableau.setPreferredScrollableViewportSize(new Dimension(800, 224));
		
		scrollPane = new JScrollPane(tableau);
	}
	
	
	/**
	 * Méthode qui initialise les écouteurs du panel
	 */
	private void initEcouteurs(){
		
		//Bouton ajouter
		btnAjouter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new PopupAjoutCategorie();
			}
		});
		
		//Bouton modifier
		btnModifier.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new PopupModifCategorie(PanelCategorie.getListeCategorie().get(tableau.getSelectedRow()),tableau.getSelectedRow());
			}
		});
		
		
		//Bouton supprimer
		btnSupprimer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new PopupSuppressionCategorie(PanelCategorie.getListeCategorie().get(tableau.getSelectedRow()),tableau.getSelectedRow());
			}
		});
		
		//Champs de texte "Rechercher"
		txtRecherche.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				//On désactive le clic sur le bouton modifier
				PanelCategorie.btnModifier.setEnabled(false);
				PanelCategorie.btnSupprimer.setEnabled(false);
				
				PanelCategorie.majTableauRecherche();
				tableCategorie.setDataVector(tabCategories, tabTitres);
			}
		});
		
		//Double clic sur une ligne
		tableau.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				//On autorique le clic sur le bouton modifier
				PanelCategorie.btnModifier.setEnabled(true);
				PanelCategorie.btnSupprimer.setEnabled(true);
				
				if (e.getClickCount()%2 == 0){
					new PopupModifCategorie(PanelCategorie.getListeCategorie().get(tableau.getSelectedRow()),tableau.getSelectedRow());
				}
			}
		});
	}

	/**
	 * Méthode qui met à jour le tableau en fonction de l'arraylist courante (à réutiliser dans les autres méthodes).
	 */
	private static void maj(){
		int nouvelleLongueur = listeCategorie.size();
		tabCategories = new Object[nouvelleLongueur][2];
		
		for (Categorie cat : listeCategorie){
			tabCategories[listeCategorie.indexOf(cat)][0] = cat.getId();
			tabCategories[listeCategorie.indexOf(cat)][1] = cat.getNom();
		}
		
		tableCategorie.setDataVector(tabCategories, tabTitres);
	}
	
	
	/**
	 * Méthode qui ajoute la catégorie créée au tableau
	 */
	public static void majTableau(Categorie c){
		listeCategorie.add(c);
		maj();
	}
	
	
	/**
	 * Méthode qui modifie une catégorie et met à jour le tableau.
	 * @param c, la catégorie modifiée.
	 * @param indice, l'indice dans l'arraylist de la catégorie modifiée.
	 */
	public static void majTableauModif(Categorie c, int indice){
		listeCategorie.remove(indice);
		listeCategorie.add(indice,c);
		maj();
	}
	
	
	/**
	 * Méthode qui supprimer une catégorie et met à jour le tableau.
	 * @param c, la catégorie supprimée.
	 * @param indice, l'indice dans l'arraylist de la catégorie supprimée.
	 */
	public static void majTableauSuppr(Categorie c, int indice){
		listeCategorie.remove(indice);
		maj();
	}
	
	
	/**
	 * Méthode qui charge uniquement les catégories qui sont tapées dans le champs de recherche.
	 */
	private static void majTableauRecherche(){
		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("SELECT * FROM CategoriesFournisseur WHERE UPPER(nomCategorie) LIKE UPPER(?) ORDER BY nomCategorie");
			pst.setString(1, "%"+txtRecherche.getText()+"%");
			ResultSet rs = pst.executeQuery();
			
			listeCategorie.clear();
			
			while(rs.next()){
				listeCategorie.add(new Categorie(rs.getString("refCategorie"), rs.getString("nomCategorie")));
			}
			
			maj();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Méthode getter qui retourne le bouton modifier.
	 * @return le bouton modifier.
	 */
	public static JButton getBtonModifier(){
		return btnModifier;
	}
	
	
	/**
	 * Méthode getter qui retourne le bouton supprimer.
	 * @return le bouton supprimer.
	 */
	public static JButton getBtonSupprimer(){
		return btnSupprimer;
	}
}
