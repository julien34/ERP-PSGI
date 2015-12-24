package achat;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import principal.FenetrePrincipale;

public class PanelCategorie extends JPanel{
	JButton btnAjouter, btnModifier;
	JPanel panelNord, panelCentre, panelGrille, panelBoutons;
	String[] tabTitres = {"Code","Catégorie"};
	Object[][] tabCategories;
	UneditableTableModel tableCategorie;
	
	
	/**
	 * Constructeur avec une FenetrePrincipale en paramètre
	 * @param f, la fenetrePrincipale
	 */
	public PanelCategorie(FenetrePrincipale f){
		this.setLayout(new BorderLayout());//Le panel sera en Borderlayout
		
		this.initPanel();//On créer les différents panels
		this.initElements();//On instancie les différents composants du panel
		this.initEcouteurs();//On créer les écouteurs de tous les boutons
		
		this.setVisible(true);//On rend visible le panel
	}
	
	
	/**
	 * Méthode qui initialise le panel de gestion des catégories fournisseurs
	 */
	private void initPanel(){
		this.panelNord = new JPanel();//Panel de recherche dans les catégories de fournisseurs
		this.panelCentre = new JPanel(new BorderLayout());//Panel qui recevra la grid + les boutons
		this.panelGrille = new JPanel();//On créer un panel qui va recevoir la grille
		this.panelBoutons = new JPanel();//On créer le panel qui va recevoir les boutons
	}
	
	
	/**
	 * Méthode qui initialise les éléments du panel
	 */
	private void initElements(){
		
		//On créer les boutons (pas de suppression)
		this.btnAjouter = new JButton("Ajouter");
		this.btnModifier = new JButton("Modifier");
		
		//On les ajoute au panelBoutons
		this.panelBoutons.add(this.btnAjouter);
		this.panelBoutons.add(this.btnModifier);
		
		//On met en place le panel du centre avec la grid et les boutons
		this.panelCentre.add(this.panelGrille, BorderLayout.CENTER);
		this.panelCentre.add(this.panelBoutons, BorderLayout.SOUTH);
		
		//On ajoute le panel de recherche (nord) et le panel centre (grid + boutons)
		this.add(this.panelNord, BorderLayout.NORTH);
		this.add(this.panelCentre, BorderLayout.CENTER);
	}
	
	
	/**
	 * Méthode qui initialise les écouteurs du panel
	 */
	private void initEcouteurs(){
		
	}
}
