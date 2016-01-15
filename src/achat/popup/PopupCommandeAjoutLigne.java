package achat.popup;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jdbc.DatabaseConnection;
import achat.Categorie;
import achat.LignesCommande;
import achat.Produit;

public class PopupCommandeAjoutLigne extends JDialog{
	private ArrayList<Produit> listeProduits = new ArrayList<Produit>();
	private ArrayList<Categorie> listeCategorie = new ArrayList<Categorie>();
	private JButton btnAjouter, btnAnnuler;
	private JTextField txtNomProduitRecherche, txtQte;
	private Choice chCategorieRecherche = new Choice();
	private DefaultListModel<String> dLMProduits = new DefaultListModel<String>();
	private JList<String> jListProduit = new JList<String>(this.dLMProduits);
	private ScrollPane scrollPane;
	private String numCommande;
	private LignesCommande ligneCommande;
	
	
	/**
	 * Constructeur d'une Popup d'ajout de ligne commande fournisseur.
	 */
	public PopupCommandeAjoutLigne(String numCommande){
		this.numCommande = numCommande;
		this.getProduits();//On récupère l'ensemble des produits et on les ajoute dans l'arryList
		this.getCategories();//On récupère les catégories pour permettre la recherche
		this.initFenetre();//On initialise la fenetre
		this.initElements();//On initialise l'ensemble des composants
		this.initEcouteurs();//On initialise les écouteurs de la fenetre
	}
	
	
	/**
	 * Méthode qui initie la fenetre popup.
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
	 * Méthode qui initialise l'ensemble de tous les panels et composants de la fenetre.
	 */
	private void initElements(){
		this.setLayout(new BorderLayout());//On défini la page en Borderlayout
		
		//On défini les différents panels
		JPanel panelRecherche = new JPanel();
		JPanel panelListe = new JPanel();
		JPanel panelBoutonsQte = new JPanel();
		
		//On défini les différents composants
		JLabel lblCategorie = new JLabel("Catégorie : ");
		JLabel lblProduit = new JLabel("Produit : ");
		JLabel lblQte = new JLabel("Qté : ");
		this.txtNomProduitRecherche = new JTextField(10);
		this.txtQte = new JTextField(4);
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
		
		//On ajoute les boutons et la quantité au panel Sud
		panelBoutonsQte.add(lblQte);
		panelBoutonsQte.add(this.txtQte);
		panelBoutonsQte.add(this.btnAjouter);
		panelBoutonsQte.add(this.btnAnnuler);
		
		
		//On ajoute les panels à la frame
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
				PopupCommandeAjoutLigne.this.setLigne();
				PopupCommandeAjoutLigne.this.ajoutLigneCommande(PopupCommandeAjoutLigne.this.ligneCommande);
			}
		});
		
		this.jListProduit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				PopupCommandeAjoutLigne.this.btnAjouter.setEnabled(true);
			}
		});
	}
	
	
	/**
	 * Méthode qui se connecte à la base de donnée pour récupérer l'ensemble des produits (disponible à l'achat).
	 */
	private void getProduits(){
		
		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("SELECT * FROM Produits p JOIN categorie c ON c.codeCategorie = p.categorie WHERE p.achatVente = 'AchatVente' or p.achatVente = 'Achat' ORDER BY p.description");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				String codePdt = rs.getString("codeProduit");
				String descriptionPdt = rs.getString("description");
				Double prixAchat = rs.getDouble("prixAchat");
				String codeCategorie = rs.getString("codeCategorie");
				String nomCategorie = rs.getString("nomCategorie");
				
				this.listeProduits.add(new Produit(codePdt, descriptionPdt, prixAchat, codeCategorie, nomCategorie));
			}
			
			//On ajoute tous les produits à la JList
			for(Produit p : this.listeProduits){
				this.dLMProduits.addElement(p.getDescription());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Méthode qui se connecte à la base de donnée et récupère l'ensemble des catégories de produits.
	 */
	private void getCategories(){
		
		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("SELECT * FROM Categorie c ORDER BY c.nomCategorie");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()){
				String codeCat = rs.getString("codeCategorie");
				String nomCat = rs.getString("nomCategorie");
				
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
	 * Méthode qui ajoute une ligneCommande dans la BDD et dans la l'ArrayList.
	 * @param lc, Une ligne de commande.
	 */
	private void ajoutLigneCommande(LignesCommande lc){
		
		try {
			Connection cn = DatabaseConnection.getCon();
			PreparedStatement pst = cn.prepareStatement("INSERT INTO LignesCommandeFournisseur(refCommande, refProduit, quantite) VALUES(?,?,?)");
			pst.setString(1, this.numCommande);
			pst.setString(2, lc.getRefProduit());
			pst.setInt(3, lc.getQte());
			pst.executeQuery();
			
			PopupCommande.addArrayListLigneCommande(lc);
			PopupCommande.maj();
			dispose();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Méthode qui remplit la LigneCommande courrante avec les informations renseignées.
	 */
	private void setLigne(){
		String refProduit = this.listeProduits.get(this.jListProduit.getSelectedIndex()).getCode();
		String nomProduit = this.listeProduits.get(this.jListProduit.getSelectedIndex()).getDescription();
		String categorieProduit = this.listeProduits.get(this.jListProduit.getSelectedIndex()).getNomCat();
		Double pHT = this.listeProduits.get(this.jListProduit.getSelectedIndex()).getPrix();
		
		//On vérifie que la qté saisie soit un nombre > 0.
		try{
			int qte = Integer.valueOf(this.txtQte.getText());
			Double total = qte*pHT;
			
			//On créer la ligne de commande
			this.ligneCommande = new LignesCommande(refProduit, nomProduit, categorieProduit, pHT, qte, total);
			
		} catch(NumberFormatException nfe){
			JOptionPane.showMessageDialog(null,"'"+this.txtQte.getText()+"'"+" n'est pas une quantité possible.","Erreur",JOptionPane.ERROR_MESSAGE);

		}
	}
}
