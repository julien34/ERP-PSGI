package achat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.ScrollPane;
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

import jdbc.DatabaseConnection;
import principal.FenetrePrincipale;

public class PanelCategorie extends JPanel{
	
	//On instancie les �l�ments de la page
	private JTextField txtRecherche;
	private JLabel lblrecherche;
	private JButton btnAjouter, btnModifier;
	private JPanel panelNord, panelCentre, panelGrille, panelBoutons;
	private String[] tabTitres = {"Code","Cat�gorie"};
	private Object[][] tabCategories;
	private UneditableTableModel tableCategorie;
	private ArrayList<Categorie> listeCategorie = new ArrayList<Categorie>();
	private JScrollPane scrollPane = new JScrollPane();
	private JTable tableau;
	
	
	/**
	 * Constructeur avec une FenetrePrincipale en param�tre
	 * @param f, la fenetrePrincipale
	 */
	public PanelCategorie(FenetrePrincipale f){
		this.initTab();//On initie le tableau des cat�gories
		
		this.initPanel();//On cr�er les diff�rents panels
		this.initElements();//On instancie les diff�rents composants du panel
		this.initEcouteurs();//On cr�er les �couteurs de tous les boutons
		
		this.setVisible(true);//On rend visible le panel
	}
	
	
	/**
	 * M�thode qui initialise le panel de gestion des cat�gories fournisseurs
	 */
	private void initPanel(){
		this.setLayout(new BorderLayout());//Le panel sera en Borderlayout
		this.panelNord = new JPanel();//Panel de recherche dans les cat�gories de fournisseurs
		this.panelCentre = new JPanel(new BorderLayout());//Panel qui recevra la grid + les boutons
		this.panelGrille = new JPanel();//On cr�er un panel qui va recevoir la grille
		this.panelBoutons = new JPanel();//On cr�er le panel qui va recevoir les boutons
	}
	
	
	/**
	 * M�thode qui initialise les �l�ments du panel
	 */
	private void initElements(){
		
		/*PANEL NORD*/
		//On cr�er lee champs et le label recherche
		this.lblrecherche = new JLabel("Cat�gorie : ");
		this.txtRecherche = new JTextField(10);
		
		//On ajoute
		this.panelNord.add(this.lblrecherche);
		this.panelNord.add(this.txtRecherche);
		
		
		/*PANEL CENTRE*/
		//On cr�er les boutons (pas de suppression)
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
	 * M�thode qui r�cup�re les cat�gories et les ajoute dans l'arrayList.
	 */
	private void initTab(){
		
		try {
			Connection cn = DatabaseConnection.getCon();
			Statement st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM CategoriesFournisseur");
			
			while(rs.next()){
				this.listeCategorie.add(new Categorie(rs.getString("refCategorie"), rs.getString("nomCategorie")));
			}
			
			this.remplirTableau();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * M�thode qui remplit le tableau 
	 */
	private void remplirTableau(){
		
		//On calcule la longueur de l'arrayList et on cr�er un nouvceau tableau de cette taille
		int longueurTab = this.listeCategorie.size();
		this.tabCategories = new Object[longueurTab][2];
		
		//On remplit le tableau
		for (Categorie cat : this.listeCategorie){
			this.tabCategories[this.listeCategorie.indexOf(cat)][0] = cat.getId();
			this.tabCategories[this.listeCategorie.indexOf(cat)][1] = cat.getNom();
		}
		
		//On cr�er une table modele et un JTable. On assigne le modele � la JTable
		this.tableCategorie = new UneditableTableModel(0,2);
		this.tableCategorie.setDataVector(tabCategories, tabTitres);
		
		this.tableau = new JTable(tableCategorie);
		this.tableau.setAutoCreateRowSorter(false);
		this.tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.tableau.setPreferredScrollableViewportSize(new Dimension(800, 224));
		
		this.scrollPane = new JScrollPane(this.tableau);
	}
	
	
	/**
	 * M�thode qui initialise les �couteurs du panel
	 */
	private void initEcouteurs(){
		
	}
}
