package achat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
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

import achat.popup.PopupAjoutCategorie;
import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class PanelCategorie extends JPanel{
	
	//On instancie les éléments de la page
	private JTextField txtRecherche;
	private JLabel lblrecherche;
	private JButton btnAjouter, btnModifier;
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
		this.initTab();//On initie le tableau des catégories
		
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
		this.txtRecherche = new JTextField(10);
		
		//On ajoute
		this.panelNord.add(this.lblrecherche);
		this.panelNord.add(this.txtRecherche);
		
		
		/*PANEL CENTRE*/
		//On créer les boutons (pas de suppression)
		this.btnAjouter = new JButton("Ajouter");
		this.btnModifier = new JButton("Modifier");
		
		//On les ajoute au panelBoutons
		this.panelBoutons.add(this.btnAjouter);
		this.panelBoutons.add(this.btnModifier);
		
		//On ajoute le scrollPane au panel central
		this.panelGrille.add(this.scrollPane);
		
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
			ResultSet rs = st.executeQuery("SELECT * FROM CategoriesFournisseur");
			
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
		this.btnAjouter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new PopupAjoutCategorie();
			}
		});
	}

	
	/**
	 * Méthode qui ajoute la catégorie créée au tableau
	 */
	public static void majTableau(Categorie c){
		listeCategorie.add(c);
		int nouvelleLongueur = listeCategorie.size();
		tabCategories = new Object[nouvelleLongueur][2];
		
		for (Categorie cat : listeCategorie){
			tabCategories[listeCategorie.indexOf(cat)][0] = cat.getId();
			tabCategories[listeCategorie.indexOf(cat)][1] = cat.getNom();
		}
		
		tableCategorie.setDataVector(tabCategories, tabTitres);
	}
	
	
	
	/**
	 * Méthode qui charge uniquement les catégories qui sont tapées dans le champs de recherche.
	 */
	private void majTableaurecherche(){
		
	}
}
