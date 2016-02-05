package vente;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import jdbc.DatabaseConnection;
import achat.modeles.Categorie;
import achat.modeles.LignesCommande;
import achat.modeles.Produit; 

public class AjouterProduitCommande extends JDialog{
	private ArrayList<Produit> listeProduits = new ArrayList<Produit>();
	private ArrayList<Categorie> listeCategorie = new ArrayList<Categorie>();
	private JButton btnAjouter, btnAnnuler;
	private JTextField txtNomProduitRecherche;
	private JSpinner txtQte;
	private Choice chCategorieRecherche = new Choice();
	private DefaultListModel<String> dLMProduits = new DefaultListModel<String>();
	private JList<String> jListProduit = new JList<String>(this.dLMProduits);
	private ScrollPane scrollPane;
	private String numCommande;
	private LignesCommande ligneCommande;
	private Client clientCommande;
	
	/**
	 * Constructeur d'une Popup d'ajout de ligne commande fournisseur.
	 */
	public AjouterProduitCommande(String numCommande){
		this.numCommande = numCommande;
		this.getProduits();//On r�cup�re l'ensemble des produits et on les ajoute dans l'arryList
		this.getCategories();//On r�cup�re les cat�gories pour permettre la recherche
		this.initFenetre();//On initialise la fenetre
		this.initElements();//On initialise l'ensemble des composants
		this.initEcouteurs();//On initialise les �couteurs de la fenetre
	}
	
	
	/**
	 * M�thode qui initie la fenetre popup.
	 */
	private void initFenetre(){
		this.setSize(550, 350);
		this.setTitle("Ajouter une ligne commande");
		Dimension dim = new Dimension(550,350);
		this.setMinimumSize(dim);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	/**
	 * M�thode qui initialise l'ensemble de tous les panels et composants de la fenetre.
	 */
	private void initElements(){
		this.setLayout(new BorderLayout());//On d�fini la page en Borderlayout
		
		//On d�fini les diff�rents panels
		JPanel panelRecherche = new JPanel();
		JPanel panelListe = new JPanel();
		JPanel panelBoutonsQte = new JPanel();
		
		//On d�fini les diff�rents composants
		JLabel lblCategorie = new JLabel("Cat�gorie : ");
		JLabel lblProduit = new JLabel("Produit : ");
		JLabel lblQte = new JLabel("Qt� : ");
		this.txtNomProduitRecherche = new JTextField(10);
		this.txtQte = new JSpinner(new SpinnerNumberModel(1, 1, 999,1)); //Min - min - max - saut
		//this.txtQte.set
		this.btnAjouter = new JButton("Ajouter");
		this.btnAjouter.setEnabled(false);
		this.btnAnnuler = new JButton("Annuler");
		this.scrollPane = new ScrollPane();
		this.scrollPane.setSize(550, 250);
		
		
		//On ajoute les composants au panel recherche
		panelRecherche.add(lblCategorie);
		panelRecherche.add(this.chCategorieRecherche);
		panelRecherche.add(lblProduit);
		panelRecherche.add(this.txtNomProduitRecherche);
		
		
		//On ajoute la JList au panelListe
		this.scrollPane.add(this.jListProduit);
		panelListe.add(this.scrollPane);
		
		//On ajoute les boutons et la quantit� au panel Sud
		panelBoutonsQte.add(lblQte);
		panelBoutonsQte.add(this.txtQte);
		panelBoutonsQte.add(this.btnAjouter);
		panelBoutonsQte.add(this.btnAnnuler);
		
		
		//On ajoute les panels � la frame
		this.add(panelRecherche, BorderLayout.NORTH);
		this.add(panelListe, BorderLayout.CENTER);
		this.add(panelBoutonsQte, BorderLayout.SOUTH);
	}
	
	
	private void initEcouteurs(){
		
		//Bouton annuler
		this.btnAnnuler.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		//Bouton valider
		this.btnAjouter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AjouterProduitCommande.this.setLigne();
		//		AjouterProduitCommande.this.ajoutLigneCommande(AjouterProduitCommande.this.ligneCommande);
			}
		});
		
		//Tableau
		this.jListProduit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				AjouterProduitCommande.this.btnAjouter.setEnabled(true);
			}
		});
		
		//Changement de cat�gorie
		this.chCategorieRecherche.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				AjouterProduitCommande.this.rechercheProduit();
			}
		});
		
		//Lors d'une saisie de texte dans le nom du produit
		txtNomProduitRecherche.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				AjouterProduitCommande.this.rechercheProduit();
			}
		});
	}
	
	
	/**
	 * M�thode qui se connecte � la base de donn�e pour r�cup�rer l'ensemble des produits (disponible � l'achat).
	 */
	private void getProduits(){
		
		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("SELECT p.code, p.nom, p.prixAchat, p.categorie, c.nom AS nomCategorie FROM Produit p JOIN categorie c ON c.code = p.categorie WHERE p.disponibilite = 'Vente' ORDER BY p.nom");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				String codePdt = rs.getString("code");
				String descriptionPdt = rs.getString("nom");
				Double prixAchat = rs.getDouble("prixAchat");
				String codeCategorie = rs.getString("categorie");
				String nomCategorie = rs.getString("nomCategorie");
				
				this.listeProduits.add(new Produit(codePdt, descriptionPdt, prixAchat, codeCategorie, nomCategorie));
			}
			
			//On ajoute tous les produits � la JList
			for(Produit p : this.listeProduits){
				this.dLMProduits.addElement(p.getDescription());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * M�thode qui se connecte � la base de donn�e et r�cup�re l'ensemble des cat�gories de produits.
	 */
	private void getCategories(){
		
		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("SELECT * FROM Categorie c ORDER BY c.nom");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				String codeCat = rs.getString("code");
				String nomCat = rs.getString("nom");
				
				this.listeCategorie.add(new Categorie(codeCat, nomCat));
			}
			
			this.chCategorieRecherche.add("Toutes");
			this.chCategorieRecherche.select("Toutes");
			
			//On boucle dans la liste pour afficher dans le Choice
			for (Categorie l : listeCategorie){
				this.chCategorieRecherche.add(l.getNom());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * M�thode qui ajoute une ligneCommande dans la BDD et dans la l'ArrayList.
	 * @param lc, Une ligne de commande.
	 */
	/*private void ajoutLigneCommande(LignesCommande lc){
		
		try {
		
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("INSERT INTO LignesCommandeFournisseur(refCommande, refProduit, quantite) VALUES(?,?,?)");
			pst.setString(1, this.numCommande);
			pst.setString(2, lc.getRefProduit());
			pst.setInt(3, lc.getQte());
			pst.executeQuery();

			dispose();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
*/	
	
	/**
	 * M�thode qui remplit la LigneCommande courrante avec les informations renseign�es.
	 */
	private void setLigne(){
		String refProduit = this.listeProduits.get(this.jListProduit.getSelectedIndex()).getCode();
		String nomProduit = this.listeProduits.get(this.jListProduit.getSelectedIndex()).getDescription();
		String categorieProduit = this.listeProduits.get(this.jListProduit.getSelectedIndex()).getNomCat();
		Double pHT = this.listeProduits.get(this.jListProduit.getSelectedIndex()).getPrix();
		int qte = (Integer) this.txtQte.getValue();
		Double total = qte*pHT;
			
		//On cr�er la ligne de commande
		this.ligneCommande = new LignesCommande(refProduit, nomProduit, categorieProduit, pHT, qte, total);
	}
	
	
	/**
	 * M�thode qui recherche dans la liste des produits en fonction des champs de la recherche.
	 */
	private void rechercheProduit(){
		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("SELECT p.code, p.nom, p.prixAchat, p.categorie, c.nom AS nomCategorie FROM Produit p JOIN categorie c ON c.code = p.categorie WHERE UPPER(c.code) LIKE UPPER(?) AND UPPER(p.nom) LIKE UPPER(?) AND p.disponibilite = 'Achat' ORDER BY p.nom");
			
			//On remet la liste � 0
			this.listeProduits.clear();
			this.dLMProduits.clear();
			
			//Si la cat�gorie est sur "Toutes", on initialise la cat�gorie � vide ("")
			if(chCategorieRecherche.getSelectedItem().equals("Toutes")){
				pst.setString(1, "%%");
			}
			
			//Si non on r�cup�re l'id de la cat�gorie depuis la liste (-1, car "Toutes" est au d�but)
			else{
				pst.setString(1, "%"+this.listeCategorie.get(chCategorieRecherche.getSelectedIndex()-1).getId()+"%");
			}
			pst.setString(2, "%"+this.txtNomProduitRecherche.getText()+"%");
			ResultSet rs = pst.executeQuery();
			
			//On boucle sur les r�sultat pour remplir l'arraylist
			while(rs.next()){
				String codePdt = rs.getString("code");
				String descriptionPdt = rs.getString("nom");
				Double prixAchat = rs.getDouble("prixAchat");
				String codeCategorie = rs.getString("categorie");
				String nomCategorie = rs.getString("nomCategorie");
				
				this.listeProduits.add(new Produit(codePdt, descriptionPdt, prixAchat, codeCategorie, nomCategorie));
			}
			
			//On ajoute tous les produits � la JList
			for(Produit p : this.listeProduits){
				this.dLMProduits.addElement(p.getDescription());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
