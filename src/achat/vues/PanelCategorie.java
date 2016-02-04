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
	
	//On instancie les �l�ments de la page
	private static JTextField txtRecherche;
	private JLabel lblrecherche;
	private static JButton btnAjouter, btnModifier, btnSupprimer;
	private JPanel panelNord, panelCentre, panelGrille, panelBoutons;
	private static String[] tabTitres = {"Code","Cat�gorie"};
	private static Object[][] tabCategories;
	private static UneditableTableModel tableCategorie;
	private static ArrayList<Categorie> listeCategorie = new ArrayList<Categorie>();

	private static JScrollPane scrollPane = new JScrollPane();
	private static JTable tableau;
	
	
	/**
	 * Constructeur avec une FenetrePrincipale en param�tre
	 * @param f, la fenetrePrincipale
	 */
	public PanelCategorie(FenetrePrincipale f){
		initTab();//On initie le tableau des cat�gories
		
		this.initPanel();//On cr�er les diff�rents panels
		this.initElements();//On instancie les diff�rents composants du panel
		this.initEcouteurs();//On cr�er les �couteurs de tous les boutons
		
		this.setVisible(true);//On rend visible le panel
	}
	
	
	/**
	 * M�thode qui retourne la liste des cat�gories.
	 * @return la liste des cat�gories
	 */
	public static ArrayList<Categorie> getListeCategorie() {
		return listeCategorie;
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
		txtRecherche = new JTextField(10);
		
		//On ajoute
		this.panelNord.add(this.lblrecherche);
		this.panelNord.add(txtRecherche);
		
		
		/*PANEL CENTRE*/
		//On cr�er les boutons (pas de suppression)
		btnAjouter = new JButton("Ajouter");
		btnModifier = new JButton("Modifier");
		btnSupprimer = new JButton("Supprimer");
		
		//On grise le bouton modifier (car aucune ligne n'est s�lectionn�e au d�part)
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
	 * M�thode qui r�cup�re les cat�gories et les ajoute dans l'arrayList.
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
	 * M�thode qui remplit le tableau 
	 */
	private static void remplirTableau(){
		
		//On calcule la longueur de l'arrayList et on cr�er un nouvceau tableau de cette taille
		int longueurTab = listeCategorie.size();
		tabCategories = new Object[longueurTab][2];
		
		//On remplit le tableau
		for (Categorie cat : listeCategorie){
			tabCategories[listeCategorie.indexOf(cat)][0] = cat.getId();
			tabCategories[listeCategorie.indexOf(cat)][1] = cat.getNom();
		}
		
		//On cr�er une table modele et un JTable. On assigne le modele � la JTable
		tableCategorie = new UneditableTableModel(0,2);
		tableCategorie.setDataVector(tabCategories, tabTitres);
		
		tableau = new JTable(tableCategorie);
		tableau.setAutoCreateRowSorter(false);
		tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableau.setPreferredScrollableViewportSize(new Dimension(800, 224));
		
		scrollPane = new JScrollPane(tableau);
	}
	
	
	/**
	 * M�thode qui initialise les �couteurs du panel
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
				
				//On d�sactive le clic sur le bouton modifier
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
	 * M�thode qui met � jour le tableau en fonction de l'arraylist courante (� r�utiliser dans les autres m�thodes).
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
	 * M�thode qui ajoute la cat�gorie cr��e au tableau
	 */
	public static void majTableau(Categorie c){
		listeCategorie.add(c);
		maj();
	}
	
	
	/**
	 * M�thode qui modifie une cat�gorie et met � jour le tableau.
	 * @param c, la cat�gorie modifi�e.
	 * @param indice, l'indice dans l'arraylist de la cat�gorie modifi�e.
	 */
	public static void majTableauModif(Categorie c, int indice){
		listeCategorie.remove(indice);
		listeCategorie.add(indice,c);
		maj();
	}
	
	
	/**
	 * M�thode qui supprimer une cat�gorie et met � jour le tableau.
	 * @param c, la cat�gorie supprim�e.
	 * @param indice, l'indice dans l'arraylist de la cat�gorie supprim�e.
	 */
	public static void majTableauSuppr(Categorie c, int indice){
		listeCategorie.remove(indice);
		maj();
	}
	
	
	/**
	 * M�thode qui charge uniquement les cat�gories qui sont tap�es dans le champs de recherche.
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
	 * M�thode getter qui retourne le bouton modifier.
	 * @return le bouton modifier.
	 */
	public static JButton getBtonModifier(){
		return btnModifier;
	}
	
	
	/**
	 * M�thode getter qui retourne le bouton supprimer.
	 * @return le bouton supprimer.
	 */
	public static JButton getBtonSupprimer(){
		return btnSupprimer;
	}
}
